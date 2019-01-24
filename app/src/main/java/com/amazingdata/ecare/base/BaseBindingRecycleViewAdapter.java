package com.amazingdata.ecare.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Xiong
 * @date 2019/1/23 - 16:54
 */
// 自定义的BaseRecycleViewAdapter(Databinding版——适用于带/不带头布局,点击/选中效果)
// M: 数据类型,B: itemView的ViewDataBinding类型,H: header的ViewDataBinding类型
public abstract class BaseBindingRecycleViewAdapter<M, B extends ViewDataBinding, H extends ViewDataBinding> extends RecyclerView.Adapter {

    // 上下文对象
    protected Context mContext;
    // 观察数据集合
    protected ObservableArrayList<M> mItems;
    // 当前选中的index
    protected int selectorIndex = -1;
    // 状态标识: 1 为点击,2 为check,3 为带头布局的点击,4 为带头布局的check
    protected int mode;
    // 一些是否不可点击/选中
    protected boolean someUnClickable;


    public static final int MODE_NORMAL_CLICK = 1; // 不带头布局的点击mode
    public static final int MODE_NORMAL_CHECK = 2; // 不带头布局的选中mode
    public static final int MODE_WITH_HEADER_CLICK = 3; // 带头布局的点击mode
    public static final int MODE_WITH_HEADER_CHECK = 4; // 带头布局的选中mode
    private static final int VIEW_HEADER_TYPE = 1; // 头布局的ViewType
    private static final int VIEW_CONTENT_TYPE = 2; // item布局的ViewType4
    private onHeaderClickListener mOnHeaderClickListener; // 头布局的点击监听接口
    private onHeaderCheckListener mOnHeaderCheckListener; // 头布局的选中监听接口
    private onItemClickListener mOnItemClickListener; // item的点击监听接口
    private onItemCheckListener mOnItemCheckListener; // item的选中监听接口
    private onItemDisableClickListener mOnItemDisableClickListener; // item(Disable)的点击监听接口
    private onItemDisableCheckListener mOnItemDisableCheckListener; // item(Disable)的选中监听接口
    protected ListChangedCallback itemsChangeCallback; // 偷来的监听数据变化的监听类(好像没用)

    public void setOnHeaderClickListener(onHeaderClickListener onHeaderClickListener) {
        this.mOnHeaderClickListener = onHeaderClickListener;
    }

    public void setOnHeaderCheckListener(onHeaderCheckListener onHeaderCheckListener) {
        this.mOnHeaderCheckListener = onHeaderCheckListener;
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemCheckListener(onItemCheckListener onItemCheckListener) {
        this.mOnItemCheckListener = onItemCheckListener;
    }

    public void setOnItemDisableClickListener(onItemDisableClickListener onItemDisableClickListener) {
        this.mOnItemDisableClickListener = onItemDisableClickListener;
    }

    public void setOnItemDisableCheckListener(onItemDisableCheckListener onItemDisableCheckListener) {
        this.mOnItemDisableCheckListener = onItemDisableCheckListener;
    }

    // 头布局的点击监听接口
    public interface onHeaderClickListener {
        void click();
    }

    // 头布局的选中监听接口
    public interface onHeaderCheckListener {
        void singleSelect();
    }

    // item的点击监听接口
    public interface onItemClickListener<M> {
        void click(M m);
    }

    // item的选中监听接口
    public interface onItemCheckListener<M> {
        void singleSelect(M m);
    }

    // item(Disable)的点击监听接口
    public interface onItemDisableClickListener<M> {
        void click(M m);
    }

    // item(Disable)的选中监听接口
    public interface onItemDisableCheckListener<M> {
        void singleSelect(M m);
    }

    private class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {
        @Override
        public void onChanged(ObservableArrayList<M> newItems) {
            BaseBindingRecycleViewAdapter.this.onChanged(newItems);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingRecycleViewAdapter.this.onItemRangeChanged(newItems, i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingRecycleViewAdapter.this.onItemRangeInserted(newItems, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> newItems, int i, int i1, int i2) {
            BaseBindingRecycleViewAdapter.this.onItemRangeMoved(newItems);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            BaseBindingRecycleViewAdapter.this.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }

    protected void onChanged(ObservableArrayList<M> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeMoved(ObservableArrayList<M> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeRemoved(ObservableArrayList<M> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    // 构造器
    public BaseBindingRecycleViewAdapter(Context mContext, ObservableArrayList<M> mItems, int mode) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.mode = mode;
        this.itemsChangeCallback = new ListChangedCallback();
        this.someUnClickable = false;
    }

    // 设置是否一些不响应点击的item(clickable = false)点击/选择
    public void setSomeUnClickable(boolean someUnClickable) {
        this.someUnClickable = someUnClickable;
    }

    // 根据当前position返回ViewType
    @Override
    public int getItemViewType(int position) {
        if (mode == MODE_WITH_HEADER_CHECK || mode == MODE_WITH_HEADER_CHECK) {
            if (position == 0) return VIEW_HEADER_TYPE;
        }
        return VIEW_CONTENT_TYPE;
    }

    // 设置当前的选中对象
    public void setSelectorIndex(int position) {
        // 当当前是点击mode,退出当前方法
        if (mode == MODE_NORMAL_CLICK || mode == MODE_WITH_HEADER_CLICK) return;
        else {
            // 保存旧的选中Index
            int old = selectorIndex;
            selectorIndex = position;
            // 更新新的选中的条目
            notifyItemChanged(selectorIndex);
            // 更新旧的选中的条目
            if (old >= 0)
                notifyItemChanged(old);
        }
    }

    // 因为是Databinding方法,所以ViewHolder不用重写
    // 可以在onBinding时通过传入的binding对象来获取layout中所有的已命名的控件
    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }

    // 根据mode返回当前的item条目(算上header)
    @Override
    public int getItemCount() {
        if (mode == MODE_WITH_HEADER_CLICK || mode == MODE_WITH_HEADER_CHECK)
            return mItems.size() + 1;
        return mItems.size();
    }

    // 创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据当前的viewType获取当前的resId
        int resId = (viewType == VIEW_HEADER_TYPE) ? getHeaderLayoutResId() : getNormalLayoutResId();
        if (viewType == VIEW_HEADER_TYPE) { // 如果当前是头布局,就返回头布局的binding对象
            H binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new BaseBindingRecycleViewAdapter.BaseBindingViewHolder(binding.getRoot());
        } else { // 如果当前是item布局,就返回头布局的binding对象
            B binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new BaseBindingRecycleViewAdapter.BaseBindingViewHolder(binding.getRoot());
        }
    }

    // 绑定viewHolder
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 如果当前的viewType为头布局类型
        if (getItemViewType(position) == VIEW_HEADER_TYPE) {
            // 获取当前的binding对象
            H binding = DataBindingUtil.getBinding(holder.itemView);
            // 子类通过重写抽象方法完成BR和layout中的数据的绑定
            onBindHeaderItem(binding);
            // 如果当前的是点击类型
            if (mode == MODE_WITH_HEADER_CLICK)
                // 为itemView整体设置点击时间
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 如果当前的监听器不为空,设置监听
                        if (null != mOnHeaderClickListener)
                            mOnHeaderClickListener.click();
                    }
                });
            else {
                // 如果当前的是选中状态
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 设置监听并更新旧的、新的index的数据
                        if (null != mOnHeaderCheckListener) {
                            mOnHeaderCheckListener.singleSelect();
                            setSelectorIndex(position);
                        }
                    }
                });
            }
        } else { // 如果当前的viewType为item局部类型
            B binding = DataBindingUtil.getBinding(holder.itemView);
            onBindNormalItem(binding, mItems.get((mode == MODE_NORMAL_CLICK || mode == MODE_NORMAL_CHECK) ? position : position - 1), position);
            // 有两种情况: 带头布局和不带头布局
            if (mode == MODE_NORMAL_CLICK || mode == MODE_WITH_HEADER_CLICK) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 如果当前设置了一些item项目不可被点击/选中
                        // 并且当前itemView不能被点击
                        // 设置(Disable)点击监听
                        // 如果是带有头布局的情况,则当前实际position = position - 1
                        if (!holder.itemView.isClickable() && someUnClickable) {
                            if (null != mOnItemDisableClickListener)
                                mOnItemDisableClickListener.click(mItems.get(mode == MODE_NORMAL_CLICK ? position : position - 1));
                        } else {
                            if (null != mOnItemClickListener)
                                mOnItemClickListener.click(mItems.get(mode == MODE_NORMAL_CLICK ? position : position - 1));
                        }
                    }
                });
            } else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!holder.itemView.isClickable() && someUnClickable) {
                            if (null != mOnItemDisableCheckListener) {
                                mOnItemDisableCheckListener.singleSelect(mItems.get(mode == MODE_NORMAL_CLICK ? position : position - 1));
                                setSelectorIndex(position);
                            }
                        } else {
                            if (null != mOnItemCheckListener) {
                                mOnItemCheckListener.singleSelect(mItems.get(mode == MODE_NORMAL_CLICK ? position : position - 1));
                                setSelectorIndex(position);
                            }
                        }
                    }
                });
            }
        }
    }

    // 重置被观察数据集合
    protected void resetItems(ObservableArrayList<M> newItems) {
        mItems = newItems;
    }

    // 在子类中必须重写,返回正常item布局的layoutId
    @LayoutRes
    protected abstract int getNormalLayoutResId();

    // 在子类中必须重写,返回头布局的layoutId
    @LayoutRes
    protected abstract int getHeaderLayoutResId();

    // 在子类中必须实现,绑定item的binding和数据及自定义业务逻辑
    protected abstract void onBindNormalItem(B binding, M item, int position);

    // 在子类中必须实现,绑定头布局的binding及自定义业务逻辑
    protected abstract void onBindHeaderItem(H binding);
}

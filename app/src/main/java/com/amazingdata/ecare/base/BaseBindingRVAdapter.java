package com.amazingdata.ecare.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingdata.ecare.utils.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xiong
 * @date 2019/1/23 - 16:54
 */
// 自定义的BaseRecycleViewAdapter
// (Databinding版——适用于带/不带头布局,带/不带尾布局,点击/选中效果,item布局(unclickable)的点击监听)
// M: 数据类型,I: item的ViewDataBinding类型,H: header的ViewDataBinding类型,F: footer的ViewDataBinding类型
public abstract class BaseBindingRVAdapter<M, I extends ViewDataBinding, H extends ViewDataBinding, F extends ViewDataBinding> extends RecyclerView.Adapter {

    // 上下文对象
    protected Context mContext;
    // 观察数据集合
    protected ObservableArrayList<M> mItems;
    // 当前选中的index
    protected int selectedIndex = -1;
    // 当前选中的index集合
    protected List<Integer> selectedIndexList;
    // 响应模式mode
    protected int checkMode;
    // 是否为带头布局mode
    protected boolean withHeader = false;
    // 是否为带尾布局mode
    protected boolean withFooter = false;
    // 是否响应item的不可点击
    protected boolean responseItemDisableClick = false;

    // 一些标记参数
    public static final int MODE_CLICK = 0;
    public static final int MODE_SINGLE_SELECT = 1;
    public static final int MODE_MULTIPLE_SELECT = 2;
    private static final int VIEW_TYPE_HEADER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private static final int VIEW_TYPE_FOOTER = 2;

    // 一系列监听接口的声明
    private BaseBindingRVListener.onHeaderClickListener mOnHeaderClickListener;
    private BaseBindingRVListener.onHeaderSelectListener mOnHeaderSelectListener;
    private BaseBindingRVListener.onItemClickListener<M> mOnItemClickListener;
    private BaseBindingRVListener.onItemSelectListener<M> mOnItemSelectListener;
    private BaseBindingRVListener.onItemDisableClickListener<M> mOnItemDisableClickListener;
    private BaseBindingRVListener.onItemDisableSelectListener<M> mOnItemDisableSelectListener;
    private BaseBindingRVListener.onFooterClickListener mOnFooterClickListener;
    private BaseBindingRVListener.onFooterSelectListener mOnFooterSelectListener;
    protected ListChangedCallback itemsChangeCallback; // 偷来的监听数据变化的监听类(好像没用)

    public BaseBindingRVAdapter(Context mContext, ObservableArrayList<M> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;
        this.checkMode = MODE_CLICK;
        this.itemsChangeCallback = new ListChangedCallback();
    }

    // 链式调用,设置参数
    public BaseBindingRVAdapter setCheckMode(int checkMode) {
        this.checkMode = checkMode;
        if (checkMode == MODE_MULTIPLE_SELECT)
            selectedIndexList = new ArrayList<>();
        return this;
    }

    public BaseBindingRVAdapter setWithHeader(boolean withHeader) {
        this.withHeader = withHeader;
        return this;
    }

    public BaseBindingRVAdapter setWithFooter(boolean withFooter) {
        this.withFooter = withFooter;
        return this;
    }

    public BaseBindingRVAdapter setResponseItemDisableClick(boolean responseItemDisableClick) {
        this.responseItemDisableClick = responseItemDisableClick;
        return this;
    }

    // 返回当前返回的所有选中的数据集合
    public List<M> getMultipleSelectedDatas() {
        if (selectedIndexList == null || selectedIndexList.isEmpty())
            return null;
        else {
            List<M> datas = new ArrayList<>();
            for (int index : selectedIndexList) {
                datas.add(mItems.get(withHeader ? index - 1 : index));
            }
            return datas;
        }
    }

    // 返回当前选中的下标集合
    public List<Integer> getSelectedIndexList() {
        return selectedIndexList;
    }

    // 返回当前选中的单个数据
    public M getSingleSelectedData() {
        if (selectedIndex == -1)
            return null;
        else {
            return mItems.get(withHeader ? selectedIndex - 1 : selectedIndex);
        }
    }

    // 根据当前position返回ViewType
    @Override
    public int getItemViewType(int position) {
        if (position == 0 && withHeader)
            return VIEW_TYPE_HEADER;
        else if (position == getItemCount() - 1 && withFooter)
            return VIEW_TYPE_FOOTER;
        else
            return VIEW_TYPE_ITEM;
    }

    // 返回当前的item数目
    @Override
    public int getItemCount() {
        if (withHeader && withFooter)
            return mItems.size() + 2;
        else if (!withHeader && !withFooter)
            return mItems.size();
        else
            return mItems.size() + 1;
    }

    // 设置当前的选中item函数
    public void setSelectorIndex(int position) {
        // 如果是点击模式
        if (checkMode == MODE_CLICK) return;
        else {
            if (checkMode == MODE_SINGLE_SELECT) { // 如果是单选模式
                // 保存旧的选中index
                int old = selectedIndex;
                selectedIndex = position;
                // 更新新的选中条目
                notifyItemChanged(selectedIndex);
                // 更新旧的选中条目
                if (old >= 0 && old < getItemCount())
                    notifyItemChanged(old);
            } else { // 如果是多选模式
                // 如果存在,就从列表中删除
                if (selectedIndexList.contains(position))
                    selectedIndexList.remove(position);
                else // 如果不存在,就像列表中添加
                    selectedIndexList.add(position);
                notifyItemChanged(position);
            }
        }
    }

    // 因为是Databinding方法,所以ViewHolder不用重写
    // 可以在onBinding时通过传入的binding对象来获取layout中所有的已命名的控件
    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }

    // 创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 根据当前的viewType获取当前的resId
        int resId = (viewType == VIEW_TYPE_HEADER) ? getHeaderLayoutResId()
                : ((viewType == VIEW_TYPE_ITEM) ? getItemLayoutResId() : getFooterLayoutResId());
        // 根据当前viewType返回对应的viewHolder
        if (viewType == VIEW_TYPE_HEADER) {
            H binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new BaseBindingRVAdapter.BaseBindingViewHolder(binding.getRoot());
        } else if (viewType == VIEW_TYPE_ITEM) {
            I binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new BaseBindingRVAdapter.BaseBindingViewHolder(binding.getRoot());
        } else {
            F binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new BaseBindingRVAdapter.BaseBindingViewHolder(binding.getRoot());
        }
    }

    // 绑定viewHolder
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        // 如果当前的viewType为头布局类型
        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
            // 获取当前的binding对象
            H binding = DataBindingUtil.getBinding(holder.itemView);
            // 子类通过重写抽象方法完成BR和layout中的数据的绑定
            onBindHeader(binding);
            // 如果当前view不可点击,并设置了对此响应,则不设置点击事件
            if (!holder.itemView.isClickable() && responseItemDisableClick) return;
            // 如果当前的是点击类型
            if (checkMode == MODE_CLICK) {
                // 为itemView整体设置点击事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnHeaderClickListener)
                            mOnHeaderClickListener.click();
                    }
                });
            } else { // 如果当前是选中类型
                // 为itemView整体设置选中事件
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnHeaderSelectListener) {
                            mOnHeaderSelectListener.select();
                            setSelectorIndex(position);
                        }
                    }
                });
            }
        } else if (getItemViewType(position) == VIEW_TYPE_FOOTER) {
            // 如果当前的viewType为尾布局类型
            F binding = DataBindingUtil.getBinding(holder.itemView);
            onBindFooter(binding);
            if (!holder.itemView.isClickable() && responseItemDisableClick) return;
            if (checkMode == MODE_CLICK) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnFooterClickListener)
                            mOnFooterClickListener.click();
                    }
                });
            } else {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (null != mOnFooterSelectListener) {
                            mOnFooterSelectListener.select();
                            setSelectorIndex(position);
                        }
                    }
                });
            }
        } else { // 如果当前的viewType为item类型
            I binding = DataBindingUtil.getBinding(holder.itemView);
            // 只有取数据时index会略微不同,获取当前数据指定的index
            final int currentPos = withHeader ? position - 1 : position;
            onBindItem(binding, mItems.get(currentPos), currentPos);
            if (checkMode == MODE_CLICK) {
                // 因为对view进行setOnlickListener后自动回设置为clickable为true
                // 故去除当前值,方便下面判断
                final boolean tag = holder.itemView.isClickable();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 如果当前view不可点击,并设置了对此响应,则设置不可点击的监听
                        if (!tag && responseItemDisableClick) {
                            // 重新设置不可点击,以防notify
                            holder.itemView.setClickable(false);
                            if (null != mOnItemDisableClickListener)
                                mOnItemDisableClickListener.disableClick(mItems.get(currentPos));
                        } else {
                            if (null != mOnItemClickListener)
                                mOnItemClickListener.click(mItems.get(currentPos));
                        }
                    }
                });
            } else {
                final boolean tag = holder.itemView.isClickable();
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!tag && responseItemDisableClick) {
                            holder.itemView.setClickable(false);
                            if (null != mOnItemDisableSelectListener)
                                mOnItemDisableSelectListener.disableSelect(mItems.get(currentPos));
                        } else {
                            if (null != mOnItemSelectListener) {
                                mOnItemSelectListener.select(mItems.get(currentPos));
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

    // 在子类中必须重写,返回头布局的layoutId
    @LayoutRes
    protected abstract int getHeaderLayoutResId();

    // 在子类中必须重写,返回item布局的layoutId
    @LayoutRes
    protected abstract int getItemLayoutResId();

    // 在子类中必须重写,返回尾布局的layoutId
    @LayoutRes
    protected abstract int getFooterLayoutResId();

    // 在子类中必须实现,绑定头布局的binding及自定义业务逻辑
    protected abstract void onBindHeader(H binding);

    // 在子类中必须实现,绑定item的binding和数据及自定义业务逻辑
    protected abstract void onBindItem(I binding, M item, int position);

    // 在子类中必须实现,绑定尾布局的binding及自定义业务逻辑
    protected abstract void onBindFooter(F binding);

    public void setOnItemClickListener(BaseBindingRVListener.onItemClickListener<M> onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectListener(BaseBindingRVListener.onItemSelectListener<M> onItemSelectListener) {
        this.mOnItemSelectListener = onItemSelectListener;
    }

    public void setOnItemDisableClickListener(BaseBindingRVListener.onItemDisableClickListener<M> onItemDisableClickListener) {
        this.mOnItemDisableClickListener = onItemDisableClickListener;
    }

    public void setOnItemDisableSelectListener(BaseBindingRVListener.onItemDisableSelectListener<M> onItemDisableSelectListener) {
        this.mOnItemDisableSelectListener = onItemDisableSelectListener;
    }

    public void setOnHeaderClickListener(BaseBindingRVListener.onHeaderClickListener onHeaderClickListener) {
        this.mOnHeaderClickListener = onHeaderClickListener;
    }

    public void setOnHeaderSelectListener(BaseBindingRVListener.onHeaderSelectListener onHeaderSelectListener) {
        this.mOnHeaderSelectListener = onHeaderSelectListener;
    }

    public void setOnFooterClickListener(BaseBindingRVListener.onFooterClickListener onFooterClickListener) {
        this.mOnFooterClickListener = onFooterClickListener;
    }

    public void setOnFooterSelectListener(BaseBindingRVListener.onFooterSelectListener onFooterSelectListener) {
        this.mOnFooterSelectListener = onFooterSelectListener;
    }

    public static class BaseBindingRVListener {
        public interface onHeaderClickListener {
            void click();
        }

        public interface onHeaderSelectListener {
            void select();
        }

        public interface onItemClickListener<M> {
            void click(M data);
        }

        public interface onItemSelectListener<M> {
            void select(M data);
        }

        public interface onItemDisableClickListener<M> {
            void disableClick(M data);
        }

        public interface onItemDisableSelectListener<M> {
            void disableSelect(M data);
        }

        public interface onFooterClickListener {
            void click();
        }

        public interface onFooterSelectListener {
            void select();
        }
    }

    private class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {
        @Override
        public void onChanged(ObservableArrayList<M> newItems) {
            BaseBindingRVAdapter.this.onChanged(newItems);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingRVAdapter.this.onItemRangeChanged(newItems, i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingRVAdapter.this.onItemRangeInserted(newItems, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> newItems, int i, int i1, int i2) {
            BaseBindingRVAdapter.this.onItemRangeMoved(newItems);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            BaseBindingRVAdapter.this.onItemRangeRemoved(sender, positionStart, itemCount);
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
}

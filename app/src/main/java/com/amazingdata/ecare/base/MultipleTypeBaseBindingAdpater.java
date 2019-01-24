package com.amazingdata.ecare.base;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Xiong
 * @date 2019/1/24 - 13:21
 */
// 内容多样的BaseRecycleViewAdapter(穿插)
public abstract class MultipleTypeBaseBindingAdpater<A, D extends ViewDataBinding, E extends ViewDataBinding> extends RecyclerView.Adapter {

    // 上下文对象
    protected Context mContext;
    // 观察的数据对象集合
    protected ObservableList<A> singleDatas;
    private static final int VIEW_TYPE_ONE = 1; // 第一种Item样式的ViewType
    private static final int VIEW_TYPE_TWO = 2; // 第二种Item样式的ViewType
    private onTypeOneItemClickListener mOnTypeOneItemClickListener; // 第一种Item的点击监听
    private onTypeTwoItemClickListener mOnTypeTwoItemClickListener; // 第二种Item的点击监听
    protected ListChangedCallback itemsChangeCallback; // 偷来的数据对象变化监听

    public void setOnTypeOneItemClickListener(onTypeOneItemClickListener onTypeOneItemClickListener) {
        this.mOnTypeOneItemClickListener = onTypeOneItemClickListener;
    }

    public void setOnTypeTwoItemClickListener(onTypeTwoItemClickListener onTypeTwoItemClickListener) {
        this.mOnTypeTwoItemClickListener = onTypeTwoItemClickListener;
    }

    // 第一种Item的点击监听接口
    public interface onTypeOneItemClickListener<A> {
        void onItemClick(A a);
    }

    // 第二种Item的点击监听接口
    public interface onTypeTwoItemClickListener<A> {
        void onItemClick(A a);
    }

    // 构造器
    public MultipleTypeBaseBindingAdpater(Context mContext, ObservableList<A> singleDatas) {
        this.mContext = mContext;
        this.singleDatas = singleDatas;
        this.itemsChangeCallback = new ListChangedCallback();
    }

    // 根据当前position返回ViewType
    @Override
    public int getItemViewType(int position) {
        // 根据当前是否为第一种View返回相应的ViewType
        if (isTypeOne(singleDatas.get(position)))
            return VIEW_TYPE_ONE;
        else
            return VIEW_TYPE_TWO;
    }

    // 子类必须重写,区分两种Item的函数
    protected abstract boolean isTypeOne(A a);

    // 因为是Databinding方法,所以ViewHolder不用重写
    // 可以在onBinding时通过传入的binding对象来获取layout中所有的已命名的控件
    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return singleDatas.size();
    }

    // 创建ViewHolder
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 获得当前viewType的item的layoutId
        int resId = (viewType == VIEW_TYPE_ONE) ? getTypeOneLayoutResId() : getTypeTwoLayoutResId();
        if (viewType == VIEW_TYPE_ONE) { // 如果当前第一种Item,就返第一种Item的binding对象
            D binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new MultipleTypeBaseBindingAdpater.BaseBindingViewHolder(binding.getRoot());
        } else { // 如果当前第二种Item,就返第二种Item的binding对象
            E binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), resId, parent, false);
            return new MultipleTypeBaseBindingAdpater.BaseBindingViewHolder(binding.getRoot());
        }
    }

    // 绑定viewHolder
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (getItemViewType(position) == VIEW_TYPE_ONE) {
            // 如果当前是第一种Item
            // 获得binding
            D binding = DataBindingUtil.getBinding(holder.itemView);
            // 绑定binding对象和数据
            onBindTypeOneItem(binding, singleDatas.get(position), position);
            // 如果itemView不可点击,返回
            if (!holder.itemView.isClickable()) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 为itemView设置点击监听,返回当前绑定的数据
                    if (null != mOnTypeOneItemClickListener)
                        mOnTypeOneItemClickListener.onItemClick(singleDatas.get(position));
                }
            });
        } else {
            E binding = DataBindingUtil.getBinding(holder.itemView);
            onBindTypeTwoItem(binding, singleDatas.get(position), position);
            if (!holder.itemView.isClickable()) return;
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != mOnTypeTwoItemClickListener)
                        mOnTypeTwoItemClickListener.onItemClick(singleDatas.get(position));
                }
            });
        }
    }

    private class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<A>> {
        @Override
        public void onChanged(ObservableArrayList<A> newItems) {
            MultipleTypeBaseBindingAdpater.this.onChanged(newItems);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<A> newItems, int i, int i1) {
            MultipleTypeBaseBindingAdpater.this.onItemRangeChanged(newItems, i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<A> newItems, int i, int i1) {
            MultipleTypeBaseBindingAdpater.this.onItemRangeInserted(newItems, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<A> newItems, int i, int i1, int i2) {
            MultipleTypeBaseBindingAdpater.this.onItemRangeMoved(newItems);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<A> sender, int positionStart, int itemCount) {
            MultipleTypeBaseBindingAdpater.this.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }

    //region 处理数据集变化
    protected void onChanged(ObservableArrayList<A> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeChanged(ObservableArrayList<A> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeChanged(positionStart, itemCount);
    }

    protected void onItemRangeInserted(ObservableArrayList<A> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeInserted(positionStart, itemCount);
    }

    protected void onItemRangeMoved(ObservableArrayList<A> newItems) {
        resetItems(newItems);
        notifyDataSetChanged();
    }

    protected void onItemRangeRemoved(ObservableArrayList<A> newItems, int positionStart, int itemCount) {
        resetItems(newItems);
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    // 重置被观察数据集合
    protected void resetItems(ObservableArrayList<A> newItems) {
        singleDatas = newItems;
    }

    // 在子类中必须实现,绑定第一种item的binding和数据及自定义业务逻辑
    protected abstract void onBindTypeTwoItem(E binding, A a, int position);

    // 在子类中必须实现,绑定第二种item的binding和数据及自定义业务逻辑
    protected abstract void onBindTypeOneItem(D binding, A a, int position);

    // 在子类中必须重写,返回第一种item布局的layoutId
    @LayoutRes
    protected abstract int getTypeTwoLayoutResId();

    // 在子类中必须重写,返回第二种item布局的layoutId
    @LayoutRes
    protected abstract int getTypeOneLayoutResId();
}

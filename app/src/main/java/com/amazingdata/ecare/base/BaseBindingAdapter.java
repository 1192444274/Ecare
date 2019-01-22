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
 * @date 2019/1/21 - 11:35
 */
// 偷来的RecycleViewAdapter基类(DataBinding版)
public abstract class BaseBindingAdapter<M, B extends ViewDataBinding> extends RecyclerView.Adapter {

    // 上下文对象
    protected Context mContext;
    // 观察对象
    protected ObservableArrayList<M> mItems;
    // 监听数据变化的接口
    protected ListChangedCallback itemsChangeCallback;
    // 监听点击事件的接口
    public recycleViewItemClickListener<M> mItemClickListener;

    public void setItemClickListener(recycleViewItemClickListener<M> ItemClickListener) {
        this.mItemClickListener = ItemClickListener;
    }

    // 监听item点击事件的自定义接口
    public interface recycleViewItemClickListener<M> {
        void onItemClick(M data);
    }

    public BaseBindingAdapter(Context context, ObservableArrayList<M> items) {
        this.mContext = context;
        this.mItems = items;
        this.itemsChangeCallback = new ListChangedCallback();
    }

    public ObservableArrayList<M> getItems() {
        return mItems;
    }

    public class BaseBindingViewHolder extends RecyclerView.ViewHolder {
        public BaseBindingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(this.mContext), this.getLayoutResId(viewType), parent, false);
        return new BaseBindingViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        B binding = DataBindingUtil.getBinding(holder.itemView);
        this.onBindItem(binding, mItems.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(mItems.get(position));
            }
        });
    }

    //region 处理数据集变化
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

    protected void resetItems(ObservableArrayList<M> newItems) {
        mItems = newItems;
    }

    @LayoutRes
    protected abstract int getLayoutResId(int resId);

    protected abstract void onBindItem(B binding, M item);

    private class ListChangedCallback extends ObservableArrayList.OnListChangedCallback<ObservableArrayList<M>> {
        @Override
        public void onChanged(ObservableArrayList<M> newItems) {
            BaseBindingAdapter.this.onChanged(newItems);
        }

        @Override
        public void onItemRangeChanged(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingAdapter.this.onItemRangeChanged(newItems, i, i1);
        }

        @Override
        public void onItemRangeInserted(ObservableArrayList<M> newItems, int i, int i1) {
            BaseBindingAdapter.this.onItemRangeInserted(newItems, i, i1);
        }

        @Override
        public void onItemRangeMoved(ObservableArrayList<M> newItems, int i, int i1, int i2) {
            BaseBindingAdapter.this.onItemRangeMoved(newItems);
        }

        @Override
        public void onItemRangeRemoved(ObservableArrayList<M> sender, int positionStart, int itemCount) {
            BaseBindingAdapter.this.onItemRangeRemoved(sender, positionStart, itemCount);
        }
    }
}

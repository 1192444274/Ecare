package com.amazingdata.ecare.ui.notice;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.databinding.NoticeItemBinding;
import com.amazingdata.ecare.entity.Notice;

/**
 * @author Xiong
 * @date 2019/1/21 - 11:39
 */
public class NoticeListAdapter extends BaseBindingRVAdapter<Notice, NoticeItemBinding, ViewDataBinding, ViewDataBinding> {

    public NoticeListAdapter(Context mContext, ObservableArrayList<Notice> mItems) {
        super(mContext, mItems);
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.notice_item;
    }

    @Override
    protected int getFooterLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindHeader(ViewDataBinding binding) {

    }

    @Override
    protected void onBindItem(NoticeItemBinding binding, Notice item, int position) {
        binding.setVariable(BR.noticeItem, item);
        binding.executePendingBindings();
    }

    @Override
    protected void onBindFooter(ViewDataBinding binding) {

    }
}

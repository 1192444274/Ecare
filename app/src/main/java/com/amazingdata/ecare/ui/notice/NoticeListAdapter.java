package com.amazingdata.ecare.ui.notice;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRecycleViewAdapter;
import com.amazingdata.ecare.databinding.NoticeItemBinding;
import com.amazingdata.ecare.entity.Notice;

/**
 * @author Xiong
 * @date 2019/1/21 - 11:39
 */
public class NoticeListAdapter extends BaseBindingRecycleViewAdapter<Notice, NoticeItemBinding, ViewDataBinding> {

    public NoticeListAdapter(Context mContext, ObservableArrayList<Notice> mItems, int mode) {
        super(mContext, mItems, mode);
    }

    @Override
    protected int getNormalLayoutResId() {
        return R.layout.notice_item;
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindNormalItem(NoticeItemBinding binding, Notice item, int position) {
        binding.setVariable(BR.noticeItem, item);
        binding.executePendingBindings();
    }

    @Override
    protected void onBindHeaderItem(ViewDataBinding binding) {

    }
}

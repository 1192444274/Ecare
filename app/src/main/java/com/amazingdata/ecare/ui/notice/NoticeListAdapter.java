package com.amazingdata.ecare.ui.notice;

import android.content.Context;
import android.databinding.ObservableArrayList;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingAdapter;
import com.amazingdata.ecare.databinding.NoticeItemBinding;
import com.amazingdata.ecare.entity.Notice;

/**
 * @author Xiong
 * @date 2019/1/21 - 11:39
 */
// Notice的RecycleView适配器(DataBinding版)
public class NoticeListAdapter extends BaseBindingAdapter<Notice, NoticeItemBinding> {

    public NoticeListAdapter(Context context, ObservableArrayList<Notice> items) {
        super(context, items);
    }

    @Override
    protected int getLayoutResId(int resId) {
        return R.layout.notice_item;
    }

    @Override
    protected void onBindItem(NoticeItemBinding binding, Notice item) {
        binding.setVariable(BR.noticeItem, item);
        binding.executePendingBindings();
    }
}

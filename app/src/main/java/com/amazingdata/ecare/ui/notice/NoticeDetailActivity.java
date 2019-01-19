package com.amazingdata.ecare.ui.notice;

import android.os.Bundle;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.databinding.ActivityNoticedetailBinding;

public class NoticeDetailActivity extends BaseActivity<ActivityNoticedetailBinding, NoticeDetailViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_noticedetail;
    }

    @Override
    public int initVariableId() {
        return BR.noticeDetailViewModel;
    }
}

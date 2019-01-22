package com.amazingdata.ecare.ui.notice;

import android.os.Bundle;
import android.view.View;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.databinding.ActivityNoticedetailBinding;

// 公告详情页面
public class NoticeDetailActivity extends BaseActivity<ActivityNoticedetailBinding, NoticeDetailViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_noticedetail;
    }

    @Override
    public int initVariableId() {
        return BR.noticeDetailViewModel;
    }

    @Override
    public void initData() {
        binding.includetoolbar.toolbarRight.setVisibility(View.INVISIBLE);
        binding.includetoolbar.toolbarText.setText("公告详情");
        binding.includetoolbar.toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

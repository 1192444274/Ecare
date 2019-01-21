package com.amazingdata.ecare.ui.notice;

import android.os.Bundle;
import android.view.View;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.base.ListItemListener;
import com.amazingdata.ecare.databinding.ActivityNoticelistBinding;
import com.amazingdata.ecare.entity.Notice;
import com.amazingdata.ecare.utils.Constant;

public class NoticeListActivity extends BaseActivity<ActivityNoticelistBinding, NoticeListViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_noticelist;
    }

    @Override
    public int initVariableId() {
        return BR.noticeListViewModel;
    }

    @Override
    public void initData() {
        binding.includetoolbar.toolbarText.setText("公告");
        binding.includetoolbar.toolbarRight.setVisibility(View.INVISIBLE);
        binding.includetoolbar.toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        viewModel.initAdapter(this);
        viewModel.setListItemListener(new ListItemListener<Notice>() {
            @Override
            public void onItemClick(Notice notice) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.BUNDLE_KEY, notice);
                startActivity(NoticeDetailActivity.class, bundle);
            }
        });
    }
}

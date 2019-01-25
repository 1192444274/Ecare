package com.amazingdata.ecare.ui.notice;

import android.os.Bundle;
import android.view.View;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.databinding.ActivityNoticelistBinding;
import com.amazingdata.ecare.entity.Notice;
import com.amazingdata.ecare.utils.LinearSpacesItemDecoration;
import com.amazingdata.ecare.utils.ToastUtils;

// 公告列表页面
public class NoticeListActivity extends BaseActivity<ActivityNoticelistBinding, NoticeListViewModel> {

    private NoticeListAdapter mAdapter;

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
        initToolBar();

        viewModel.setProgressDialogListener(new DialogListenerUtils.ProgressDialogListener() {
            @Override
            public void show(String content) {
                showProgressDialog(content);
            }

            @Override
            public void dissmiss() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                        dismissProgressDialog();
                    }
                });
            }
        });

        initAdapter();
    }

    // 初始化Adpater
    private void initAdapter() {
        mAdapter = new NoticeListAdapter(this, viewModel.mDatas);
        binding.noticelistRv.addItemDecoration(new LinearSpacesItemDecoration(10));
        binding.noticelistRv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseBindingRVAdapter.BaseBindingRVListener.onItemClickListener<Notice>() {
            @Override
            public void click(Notice data) {
                ToastUtils.showShort(data.toString());
            }
        });
        viewModel.initAdapter();
    }

    // 初始化toolBar
    private void initToolBar() {
        binding.includetoolbar.toolbarText.setText("公告");
        binding.includetoolbar.toolbarRight.setVisibility(View.INVISIBLE);
        binding.includetoolbar.toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

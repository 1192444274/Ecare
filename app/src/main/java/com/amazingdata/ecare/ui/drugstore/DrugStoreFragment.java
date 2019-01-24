package com.amazingdata.ecare.ui.drugstore;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRecycleViewAdapter;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.databinding.FragmentDrugstoreBinding;
import com.amazingdata.ecare.entity.DrugInfo;
import com.amazingdata.ecare.utils.GridSpacingItemDecoration;
import com.amazingdata.ecare.utils.LinearSpacesItemDecoration;
import com.amazingdata.ecare.utils.ToastUtils;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:31
 */
// 药房页面
public class DrugStoreFragment extends BaseFragment<FragmentDrugstoreBinding, DrugStoreViewModel> {

    // 药品类型列表适配器
    private DrugTypeAdapter drugTypeAdapter;
    // 取药列表适配器
    private TakeDrugListAdapter takeDrugListAdapter;
    // 药品列表适配器
    private DrugListAdapter drugListAdapter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_drugstore;
    }

    @Override
    public int initVariableId() {
        return BR.drugViewModel;
    }

    @Override
    public void initData() {
        initAdapter();
        initListener();
    }

    // 初始化监听
    private void initListener() {
        // 为drugList设置点击监听
        drugListAdapter.setOnItemClickListener(new BaseBindingRecycleViewAdapter.onItemClickListener<DrugInfo>() {
            @Override
            public void click(DrugInfo drugInfo) {
                ToastUtils.showShort(drugInfo.getDrugName());
            }
        });

        // 为viewModel设置进度对话框的监听
        viewModel.setProgressDialogWithModeListener(new DialogListenerUtils.ProgressDialogWithModeListener() {
            @Override
            public void show(String content, int mode) {
                showProgressDialog(content);
            }

            @Override
            public void dissmiss(final int mode) {
                // 适配器Adapter的notifyDataSetChanged必须在主线程中执行
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mode == DrugStoreViewModel.FRESH_LEFT_LIST)
                            drugTypeAdapter.notifyDataSetChanged();
                        else if (mode == DrugStoreViewModel.FRESH_TAKEDRUG_LIST)
                            takeDrugListAdapter.notifyDataSetChanged();
                        else
                            drugListAdapter.notifyDataSetChanged();
                        dismissProgressDialog();
                    }
                });
            }
        });

        // 初始化DrugTypeList的数据
        viewModel.initDrugTypeList();

        // 为药品列表适配器设置选中头布局的监听
        drugTypeAdapter.setOnHeaderCheckListener(new BaseBindingRecycleViewAdapter.onHeaderCheckListener() {
            @Override
            public void singleSelect() {
                // 将取药RecycleView置为可见
                binding.drugstoreRvTakedrug.setVisibility(View.VISIBLE);
                // 将药品RecycleView置为不可见
                binding.drugstoreRvDruglist.setVisibility(View.INVISIBLE);
                // 刷新取药list数据
                viewModel.initDrugTakeingList();
            }
        });

        drugTypeAdapter.setOnItemCheckListener(new BaseBindingRecycleViewAdapter.onItemCheckListener() {
            @Override
            public void singleSelect(Object o) {
                // 将药品RecycleView置为可见
                binding.drugstoreRvDruglist.setVisibility(View.VISIBLE);
                // 将取药RecycleView置为不可见
                binding.drugstoreRvTakedrug.setVisibility(View.INVISIBLE);
                // 刷新药品list数据
                viewModel.initDrugList();
            }
        });
    }

    // 初始化adapter
    private void initAdapter() {
        // 设置将数据设置为ViewModel中的数据
        drugTypeAdapter = new DrugTypeAdapter(getActivity(), viewModel.drugTypes, DrugTypeAdapter.MODE_WITH_HEADER_CHECK);
        // recycleView 设置适配器
        binding.drugstoreRv.setAdapter(drugTypeAdapter);
        // 设置item间距
        binding.drugstoreRv.addItemDecoration(new LinearSpacesItemDecoration(20));

        takeDrugListAdapter = new TakeDrugListAdapter(getActivity(), viewModel.drugTakings);
        binding.drugstoreRvTakedrug.setAdapter(takeDrugListAdapter);
        binding.drugstoreRvTakedrug.addItemDecoration(new LinearSpacesItemDecoration(20));

        drugListAdapter = new DrugListAdapter(getActivity(), viewModel.drugInfos, DrugListAdapter.MODE_NORMAL_CLICK);
        binding.drugstoreRvDruglist.setAdapter(drugListAdapter);
        binding.drugstoreRvDruglist.addItemDecoration(new GridSpacingItemDecoration(2, 20, true));
    }
}

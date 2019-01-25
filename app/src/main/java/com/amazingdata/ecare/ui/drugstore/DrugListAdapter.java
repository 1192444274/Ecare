package com.amazingdata.ecare.ui.drugstore;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.databinding.DruglistItemBinding;
import com.amazingdata.ecare.entity.DrugInfo;

/**
 * @author Xiong
 * @date 2019/1/24 - 16:20
 */
public class DrugListAdapter extends BaseBindingRVAdapter<DrugInfo, DruglistItemBinding, ViewDataBinding, ViewDataBinding> {

    public DrugListAdapter(Context mContext, ObservableArrayList<DrugInfo> mItems) {
        super(mContext, mItems);
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.druglist_item;
    }

    @Override
    protected int getFooterLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindHeader(ViewDataBinding binding) {

    }

    @Override
    protected void onBindItem(DruglistItemBinding binding, DrugInfo item, int position) {
        binding.setVariable(BR.drugInfo, item);
        binding.executePendingBindings();
    }

    @Override
    protected void onBindFooter(ViewDataBinding binding) {

    }
}

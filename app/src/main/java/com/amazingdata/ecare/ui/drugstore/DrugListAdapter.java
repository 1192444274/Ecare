package com.amazingdata.ecare.ui.drugstore;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRecycleViewAdapter;
import com.amazingdata.ecare.databinding.DruglistItemBinding;
import com.amazingdata.ecare.entity.DrugInfo;

/**
 * @author Xiong
 * @date 2019/1/24 - 16:20
 */
public class DrugListAdapter extends BaseBindingRecycleViewAdapter<DrugInfo, DruglistItemBinding, ViewDataBinding> {

    public DrugListAdapter(Context mContext, ObservableArrayList<DrugInfo> mItems, int mode) {
        super(mContext, mItems, mode);
    }

    @Override
    protected int getNormalLayoutResId() {
        return R.layout.druglist_item;
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindNormalItem(DruglistItemBinding binding, DrugInfo item, int position) {
        binding.setVariable(BR.drugInfo, item);
        binding.executePendingBindings();
    }

    @Override
    protected void onBindHeaderItem(ViewDataBinding binding) {

    }
}

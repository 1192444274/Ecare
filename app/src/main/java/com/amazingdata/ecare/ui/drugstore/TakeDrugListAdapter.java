package com.amazingdata.ecare.ui.drugstore;

import android.content.Context;
import android.databinding.ObservableList;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.MultipleTypeBaseBindingAdpater;
import com.amazingdata.ecare.databinding.DrugtakelistItemBinding;
import com.amazingdata.ecare.databinding.DrugtakelistundoneItemBinding;
import com.amazingdata.ecare.entity.DrugTaking;

/**
 * @author Xiong
 * @date 2019/1/24 - 13:14
 */
public class TakeDrugListAdapter extends MultipleTypeBaseBindingAdpater<DrugTaking, DrugtakelistundoneItemBinding, DrugtakelistItemBinding> {

    public TakeDrugListAdapter(Context mContext, ObservableList<DrugTaking> singleDatas) {
        super(mContext, singleDatas);
    }

    @Override
    protected boolean isTypeOne(DrugTaking drugTaking) {
        return drugTaking.getMode() == 0;
    }

    @Override
    protected void onBindTypeTwoItem(DrugtakelistItemBinding binding, DrugTaking drugTaking, int position) {
        binding.setVariable(BR.drugTaking, drugTaking);
        binding.executePendingBindings();
    }

    @Override
    protected void onBindTypeOneItem(DrugtakelistundoneItemBinding binding, DrugTaking drugTaking, int position) {
        binding.setVariable(BR.drugTaking, drugTaking);
        binding.executePendingBindings();
    }

    @Override
    protected int getTypeTwoLayoutResId() {
        return R.layout.drugtakelist_item;
    }

    @Override
    protected int getTypeOneLayoutResId() {
        return R.layout.drugtakelistundone_item;
    }
}

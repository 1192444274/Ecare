package com.amazingdata.ecare.ui.drugstore;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;

import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.R;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.databinding.DrugtypelistHeaderBinding;
import com.amazingdata.ecare.databinding.DrugtypelistItemBinding;

/**
 * @author Xiong
 * @date 2019/1/23 - 19:34
 */
public class DrugTypeAdapter extends BaseBindingRVAdapter<String, DrugtypelistItemBinding, DrugtypelistHeaderBinding, ViewDataBinding> {

    public DrugTypeAdapter(Context mContext, ObservableArrayList<String> mItems) {
        super(mContext, mItems);
    }

    @Override
    protected int getHeaderLayoutResId() {
        return R.layout.drugtypelist_header;
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.drugtypelist_item;
    }

    @Override
    protected int getFooterLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindHeader(DrugtypelistHeaderBinding binding) {
        if (selectedIndex == 0) {
            binding.drugtypelistHeaderIv.setImageResource(R.mipmap.takedrug_check);
            binding.drugtypelistHeaderTv.setTextColor(mContext.getResources().getColor(R.color.colorAppointBrown));
        } else {
            binding.drugtypelistHeaderIv.setImageResource(R.mipmap.takedrug_uncheck);
            binding.drugtypelistHeaderTv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    @Override
    protected void onBindItem(DrugtypelistItemBinding binding, String item, int position) {
        binding.setVariable(BR.drugType, item);
        binding.executePendingBindings();
        if (selectedIndex == position) {
            binding.drugtypelistItemTv.setBackgroundResource(R.drawable.drugtypelist_item_check);
            binding.drugtypelistItemTv.setTextColor(mContext.getResources().getColor(R.color.colorPureWhite));
        } else {
            binding.drugtypelistItemTv.setBackgroundResource(R.drawable.drugtypelist_item_uncheck);
            binding.drugtypelistItemTv.setTextColor(mContext.getResources().getColor(R.color.colorGrayLight));
        }
    }

    @Override
    protected void onBindFooter(ViewDataBinding binding) {

    }
}

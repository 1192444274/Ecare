package com.amazingdata.ecare.ui.drugstore;

import android.content.Context;
import android.databinding.ObservableArrayList;

import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.R;
import com.amazingdata.ecare.base.BaseBindingRecycleViewAdapter;
import com.amazingdata.ecare.databinding.DrugtypelistHeaderBinding;
import com.amazingdata.ecare.databinding.DrugtypelistItemBinding;

/**
 * @author Xiong
 * @date 2019/1/23 - 19:34
 */
public class DrugTypeAdapter extends BaseBindingRecycleViewAdapter<String, DrugtypelistItemBinding, DrugtypelistHeaderBinding> {

    public DrugTypeAdapter(Context mContext, ObservableArrayList<String> mItems, int mode) {
        super(mContext, mItems, mode);
    }

    @Override
    protected int getNormalLayoutResId() {
        return R.layout.drugtypelist_item;
    }

    @Override
    protected int getHeaderLayoutResId() {
        return R.layout.drugtypelist_header;
    }

    @Override
    protected void onBindNormalItem(DrugtypelistItemBinding binding, String item, int position) {
        binding.setVariable(BR.drugType, item);
        binding.executePendingBindings();
        if (selectorIndex == position) {
            binding.drugtypelistItemTv.setBackgroundResource(R.drawable.drugtypelist_item_check);
            binding.drugtypelistItemTv.setTextColor(mContext.getResources().getColor(R.color.colorPureWhite));
        } else {
            binding.drugtypelistItemTv.setBackgroundResource(R.drawable.drugtypelist_item_uncheck);
            binding.drugtypelistItemTv.setTextColor(mContext.getResources().getColor(R.color.colorGrayLight));
        }
    }

    @Override
    protected void onBindHeaderItem(DrugtypelistHeaderBinding binding) {
        if (selectorIndex == 0) {
            binding.drugtypelistHeaderIv.setImageResource(R.mipmap.takedrug_check);
            binding.drugtypelistHeaderTv.setTextColor(mContext.getResources().getColor(R.color.colorAppointBrown));
        } else {
            binding.drugtypelistHeaderIv.setImageResource(R.mipmap.takedrug_uncheck);
            binding.drugtypelistHeaderTv.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

}

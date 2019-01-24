package com.amazingdata.ecare.ui.timechooser;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRecycleViewAdapter;
import com.amazingdata.ecare.databinding.AppointtimeItemBinding;
import com.amazingdata.ecare.entity.AppointTime;
import com.amazingdata.ecare.utils.DateUtils;

/**
 * @author Xiong
 * @date 2019/1/23 - 10:36
 */
public class TimeListAdapter extends BaseBindingRecycleViewAdapter<AppointTime, AppointtimeItemBinding, ViewDataBinding> {

    public TimeListAdapter(Context mContext, ObservableArrayList<AppointTime> mItems, int mode) {
        super(mContext, mItems, mode);
    }

    @Override
    protected int getNormalLayoutResId() {
        return R.layout.appointtime_item;
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindNormalItem(AppointtimeItemBinding binding, AppointTime item, int position) {
        binding.setVariable(BR.appointTime, item);
        binding.executePendingBindings();
        String hint = new String();
        boolean canChoose = false;
        if (item.getCapacity() == 0)
            hint = "满预约";
        if (!DateUtils.outTime(item.getTime())) {
            Log.e("onBindItem: ", "已逾时");
            hint = "已逾时";
        }
        if (TextUtils.isEmpty(hint)) {
            Log.e("onBindItem: ", "剩余");
            hint = "剩余" + item.getCapacity() + "个";
            canChoose = true;
        }
        binding.appointtimeitemMode.setText(hint);
        if (position == selectorIndex) {
            binding.appointtimeitemTime.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeSelected));
            binding.appointtimeitemMode.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeSelected));
            binding.appointtimeSelectediv.setVisibility(View.VISIBLE);
            binding.appointtimeitem.setBackgroundResource(R.drawable.appointtimelist_cardview_check);
        } else {
            binding.appointtimeSelectediv.setVisibility(View.INVISIBLE);
            if (canChoose) {
                binding.appointtimeitemTime.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeNormal));
                binding.appointtimeitemMode.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeNormal));
                binding.appointtimeitem.setBackgroundResource(R.drawable.appointtimelist_cardview_uncheck);
                binding.appointtimeitem.setClickable(true);
            } else {
                binding.appointtimeitemTime.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeDisable));
                binding.appointtimeitemMode.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeDisable));
                binding.appointtimeitem.setBackgroundResource(R.drawable.appointtimelist_cardview_disableclick);
                binding.appointtimeitem.setClickable(false);
            }
        }
    }

    @Override
    protected void onBindHeaderItem(ViewDataBinding binding) {

    }
}

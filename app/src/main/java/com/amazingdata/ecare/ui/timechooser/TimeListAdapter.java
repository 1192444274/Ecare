package com.amazingdata.ecare.ui.timechooser;

import android.content.Context;
import android.databinding.ObservableArrayList;
import android.databinding.ViewDataBinding;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.databinding.AppointtimeItemBinding;
import com.amazingdata.ecare.entity.AppointTime;
import com.amazingdata.ecare.utils.DateUtils;

/**
 * @author Xiong
 * @date 2019/1/23 - 10:36
 */
public class TimeListAdapter extends BaseBindingRVAdapter<AppointTime, AppointtimeItemBinding, ViewDataBinding, ViewDataBinding> {

    public TimeListAdapter(Context mContext, ObservableArrayList<AppointTime> mItems) {
        super(mContext, mItems);
    }

    @Override
    protected int getHeaderLayoutResId() {
        return 0;
    }

    @Override
    protected int getItemLayoutResId() {
        return R.layout.appointtime_item;
    }

    @Override
    protected int getFooterLayoutResId() {
        return 0;
    }

    @Override
    protected void onBindHeader(ViewDataBinding binding) {

    }

    @Override
    protected void onBindItem(AppointtimeItemBinding binding, AppointTime item, int position) {
        // 绑定数据与XML——binding对象
        binding.setVariable(BR.appointTime, item);
        binding.executePendingBindings();
        String hint = new String();
        // 临时变量: 标志是否可以被选择
        boolean canChoose = false;
        if (item.getCapacity() == 0) // 没有容量
            hint = "满预约";
        if (!DateUtils.outTime(item.getTime())) { // 当前时间大于item数据的时间
            Log.e("onBindItem: ", "已逾时");
            hint = "已逾时";
        }
        if (TextUtils.isEmpty(hint)) { // 可以被选择的情况
            Log.e("onBindItem: ", "剩余");
            hint = "剩余" + item.getCapacity() + "个";
            canChoose = true;
        }
        binding.appointtimeitemMode.setText(hint);
        if (position == selectedIndex) { // 设置选中的效果
            binding.appointtimeitemTime.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeSelected));
            binding.appointtimeitemMode.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeSelected));
            binding.appointtimeSelectediv.setVisibility(View.VISIBLE);
            binding.appointtimeitem.setBackgroundResource(R.drawable.appointtimelist_cardview_check);
        } else { // 设置未选中的效果
            binding.appointtimeSelectediv.setVisibility(View.INVISIBLE);
            if (canChoose) { // 可被选中
                binding.appointtimeitemTime.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeNormal));
                binding.appointtimeitemMode.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeNormal));
                binding.appointtimeitem.setBackgroundResource(R.drawable.appointtimelist_cardview_uncheck);
                binding.getRoot().setClickable(true);
            } else { // 不可选中
                binding.appointtimeitemTime.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeDisable));
                binding.appointtimeitemMode.setTextColor(mContext.getResources().getColor(R.color.colorAppointTimeDisable));
                binding.appointtimeitem.setBackgroundResource(R.drawable.appointtimelist_cardview_disableclick);
                binding.getRoot().setClickable(false);
            }
        }
    }

    @Override
    protected void onBindFooter(ViewDataBinding binding) {

    }
}

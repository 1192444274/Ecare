package com.amazingdata.ecare.ui.timechooser;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.base.BaseBindingRVAdapter;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.base.VMConnectedListenerUtils;
import com.amazingdata.ecare.databinding.ActivityTimechooserBinding;
import com.amazingdata.ecare.entity.AppointTime;
import com.amazingdata.ecare.utils.Constant;
import com.amazingdata.ecare.utils.GridSpacingItemDecoration;
import com.amazingdata.ecare.utils.ToastUtils;

import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

// 选择预约时间页面
public class TimeChooserActivity extends BaseActivity<ActivityTimechooserBinding, TimeChooserViewModel> {

    // 当前是否是挂号选择的
    private boolean isRegister;
    // 选择的日期
    private Date choosedTime;
    // 挂号时间列表的适配器
    private TimeListAdapter mAdapter;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_timechooser;
    }

    @Override
    public int initVariableId() {
        return BR.timeChooserViewModel;
    }

    @Override
    public void initParam() {
        // 从bundle中获取当前页面的预约状态(true: 挂号,false: 体检)
        Bundle bundle = getIntent().getExtras();
        isRegister = bundle.getBoolean(Constant.BUNDLE_KEY, false);
    }

    @Override
    public void initData() {
        initToolBar();

        initCalendar();

        initAdapter();
    }

    // 初始化适配器
    private void initAdapter() {
        viewModel.setRegister(isRegister);
        viewModel.setOnViewChangeUI(new VMConnectedListenerUtils.onViewChangeUI() {
            @Override
            public void changeUI(Object o) {
                ToastUtils.showShort(o.toString());
                finish();
            }
        });
        if (isRegister) {
            mAdapter = new TimeListAdapter(this, viewModel.mDatas);
            mAdapter.setCheckMode(BaseBindingRVAdapter.MODE_SINGLE_SELECT)
                    .setResponseItemDisableClick(true);
            binding.registerRv.setVisibility(View.VISIBLE);
            binding.registerRv.addItemDecoration(new GridSpacingItemDecoration(4, 15, true));

            mAdapter.setOnItemSelectListener(new BaseBindingRVAdapter.BaseBindingRVListener.onItemSelectListener<AppointTime>() {
                @Override
                public void select(AppointTime data) {
                    viewModel.setSelectedData(data);
                }
            });
            binding.registerRv.setAdapter(mAdapter);
            viewModel.setProgressDialogListener(new DialogListenerUtils.ProgressDialogListener() {
                @Override
                public void show(String content) {
                    showProgressDialog(content);
                }

                @Override
                public void dissmiss() {
                    dismissProgressDialog();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                }
            });
            viewModel.initAdapterData();
        } else {
            viewModel.setRadioMode(0);
            binding.bodyexamLl.setVisibility(View.VISIBLE);
            binding.picktimeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    if (checkedId == R.id.picktime_rb_morning)
                        viewModel.setRadioMode(1);
                    else
                        viewModel.setRadioMode(2);
                }
            });
        }
    }

    // 初始化水平日历控件
    private void initCalendar() {
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.DAY_OF_WEEK, 0);

        Calendar endDate = Calendar.getInstance();
        if (isRegister)
            endDate.add(Calendar.DAY_OF_WEEK, 5);
        else
            endDate.add(Calendar.DAY_OF_WEEK, 14);

        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Calendar date, int position) {
                choosedTime = date.getTime();
                ToastUtils.showShort("date: " + choosedTime + " , pos: " + position);
            }
        });
    }

    // 初始化toolBar
    private void initToolBar() {
        binding.includetoolbar.toolbarText.setText("选择预约时间");
        binding.includetoolbar.toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        binding.includetoolbar.toolbarRight.setVisibility(View.INVISIBLE);
    }
}

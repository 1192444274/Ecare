package com.amazingdata.ecare.ui.picktime;

import android.os.Bundle;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.databinding.ActivityPickTimeBinding;

public class PickTimeActivity extends BaseActivity<ActivityPickTimeBinding, PickTimeViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_pick_time;
    }

    @Override
    public int initVariableId() {
        return BR.pickTimeViewModel;
    }
}

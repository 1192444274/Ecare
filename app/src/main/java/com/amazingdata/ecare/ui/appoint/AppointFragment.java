package com.amazingdata.ecare.ui.appoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.databinding.FragmentAppointBinding;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:28
 */
public class AppointFragment extends BaseFragment<FragmentAppointBinding, AppointViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_appoint;
    }

    @Override
    public int initVariableId() {
        return BR.appointViewModel;
    }

}

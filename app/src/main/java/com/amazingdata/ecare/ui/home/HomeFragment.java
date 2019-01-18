package com.amazingdata.ecare.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.databinding.FragmentHomeBinding;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:24
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public void initParam() {
        //通过binding拿到toolbar控件, 设置给Activity
        ((AppCompatActivity) getActivity()).setSupportActionBar(binding.includeToolbar.toolbar);
    }
}

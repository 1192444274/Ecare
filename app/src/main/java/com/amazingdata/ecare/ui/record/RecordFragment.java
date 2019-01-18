package com.amazingdata.ecare.ui.record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.databinding.FragmentRecordBinding;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:44
 */
public class RecordFragment extends BaseFragment<FragmentRecordBinding, RecordViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_record;
    }

}

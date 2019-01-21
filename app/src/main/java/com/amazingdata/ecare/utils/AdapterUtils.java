package com.amazingdata.ecare.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.amazingdata.ecare.base.BaseBindingAdapter;

/**
 * @author Xiong
 * @date 2019/1/21 - 14:31
 */
public class AdapterUtils {

    @BindingAdapter(value = "bind:adapter")
    public static void setAdapter(RecyclerView recyclerView, BaseBindingAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setAdapter(adapter);
    }
}

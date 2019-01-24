package com.amazingdata.ecare.ui.timechooser;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.base.VMConnectedListenerUtils;
import com.amazingdata.ecare.entity.AppointTime;
import com.amazingdata.ecare.utils.ToastUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/23 - 22:48
 */
public class TimeChooserViewModel extends BaseViewModel {

    // 挂号选择时间列表的数据集合的观察稽核
    public ObservableArrayList<AppointTime> mDatas = new ObservableArrayList<>();
    // 进度条对话框监听接口
    private DialogListenerUtils.ProgressDialogListener mProgressDialogListener;
    // 选中的时间数据
    private AppointTime selectedData;
    // 设置的上下午体检选项的mode,0为未选,1为上午,2为下午
    private int radioMode;
    // 标记当前是否为挂号预约
    private boolean isRegister;
    // 页面变化的监听接口(用于连接View 和ViewModel)
    private VMConnectedListenerUtils.onViewChangeUI mOnViewChangeUI;

    public void setOnViewChangeUI(VMConnectedListenerUtils.onViewChangeUI onViewChangeUI) {
        this.mOnViewChangeUI = onViewChangeUI;
    }

    public void setProgressDialogListener(DialogListenerUtils.ProgressDialogListener progressDialogListener) {
        this.mProgressDialogListener = progressDialogListener;
    }

    public void setSelectedData(AppointTime selectedData) {
        this.selectedData = selectedData;
    }

    public void setRadioMode(int radioMode) {
        this.radioMode = radioMode;
    }

    public void setRegister(boolean register) {
        isRegister = register;
    }

    public TimeChooserViewModel(@NonNull Application application) {
        super(application);
    }

    // submitButton的点击监听
    public void click() {
        if (isRegister) {
            if (null == selectedData)
                ToastUtils.showShort("请选择一个时间段!");
            else
                mOnViewChangeUI.changeUI(selectedData);
        } else {
            if (radioMode == 0)
                ToastUtils.showShort("请选择一个时间段!");
            else
                mOnViewChangeUI.changeUI(radioMode);
        }
    }

    public void initAdapterData() {
        // 模拟耗时操作
        Observable.just("")
                .delay(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mProgressDialogListener.show("正在加载数据...");

                        // 模拟网络请求
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 8, 0, 0), 3));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 8, 30, 0), 2));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 9, 0, 0), 1));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 9, 30, 0), 1));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 10, 0, 0), 4));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 10, 30, 0), 5));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 14, 0, 0), 0));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 14, 30, 0), 3));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 15, 0, 0), 4));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 15, 30, 0), 0));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 16, 0, 0), 7));
                        mDatas.add(new AppointTime(new Date(2019 - 1900, 0, 24, 16, 30, 0), 2));
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mProgressDialogListener.dissmiss();
                    }
                });
    }
}

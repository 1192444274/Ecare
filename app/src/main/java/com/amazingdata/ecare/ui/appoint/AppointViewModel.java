package com.amazingdata.ecare.ui.appoint;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.support.annotation.NonNull;
import android.view.View;

import com.amazingdata.ecare.base.BaseViewModel;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:29
 */
public class AppointViewModel extends BaseViewModel {

    public ObservableBoolean hasRegister = new ObservableBoolean(true);
    public ObservableBoolean hasBodyExam = new ObservableBoolean(true);
    public ObservableBoolean isRegister = new ObservableBoolean(true);
    public ObservableBoolean isNormal = new ObservableBoolean(true);
    public ObservableBoolean isSpecial = new ObservableBoolean(true);

    public AppointViewModel(@NonNull Application application) {
        super(application);
    }

    public void cancelAppoint() {
        // 修改UI
        boolean mode = isRegister.get();
        if (mode && hasRegister.get()) {
            hasRegister.set(false);
            // 发送网络请求
            // ...
        } else if (mode && hasBodyExam.get()) {
            hasBodyExam.set(false);
            // 发送网络请求
            // ...
        }
    }
}

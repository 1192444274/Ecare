package com.amazingdata.ecare.ui.appoint;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.entity.Appoint;
import com.amazingdata.ecare.utils.ToastUtils;

import java.util.Date;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:29
 */
// AppointFragment 的ViewModel
public class AppointViewModel extends BaseViewModel {

    // 是否有挂号预约的被观察者
    public ObservableBoolean hasRegister = new ObservableBoolean(true);
    // 是否有体检预约的被观察者
    public ObservableBoolean hasBodyExam = new ObservableBoolean(true);
    // 当前是否是挂号模式的被观察者
    public ObservableBoolean isRegister = new ObservableBoolean(true);
    // 当前是否显示除科室外属性的被观察者
    public ObservableBoolean isNormal = new ObservableBoolean(true);
    // 当前是否显示科属性的被观察者
    public ObservableBoolean isSpecial = new ObservableBoolean(true);
    // 获取的挂号预约数据的被观察者
    public ObservableField<Appoint> registerData = new ObservableField<>();
    // 获取的体检预约数据的被观察者
    public ObservableField<Appoint> bodyExamData = new ObservableField<>();
    // 当前显示数据的被观察者
    public ObservableField<Appoint> currentData = new ObservableField<>();

    public AppointViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化当前预约UI
    public void init() {
        // 网络请求返回数据
        // ...

        // 以下是模拟
        boolean hasRegister = true;
        boolean hasBodyExam = false;
        registerData.set(new Appoint(1, 39, "口腔科", new Date(2019 - 1900, 1, 28, 13, 30), "后勤大楼302"));
        Appoint appoint = new Appoint();
        appoint.setMode(2);
        bodyExamData.set(appoint);
        currentData.set(registerData.get());

        // 根据数据改变UI
        if (!hasRegister) {
            this.hasRegister.set(false);
            this.isNormal.set(false);
            this.isSpecial.set(false);
        }
        if (!hasBodyExam) {
            this.hasBodyExam.set(false);
        }
    }

    // 取消预约点击的业务逻辑
    public void cancelAppoint() {
        boolean mode = isRegister.get();
        if (mode && hasRegister.get()) {
            // 修改UI
            hasRegister.set(false);
            isNormal.set(false);
            isSpecial.set(false);
            currentData.set(registerData.get());

            // 发送网络请求
            // ...
        } else if (mode && hasBodyExam.get()) {
            // 修改UI
            hasBodyExam.set(false);
            isNormal.set(false);
            currentData.set(bodyExamData.get());

            // 发送网络请求
            // ...
        }
    }

    // radioButton的点击监听后UI变化
    public void onModeChange(boolean isRegister) {
        this.isRegister.set(isRegister);
        if (isRegister) {
            this.isNormal.set(this.hasRegister.get());
            this.isSpecial.set(this.hasRegister.get());
        } else {
            this.isSpecial.set(false);
            this.isNormal.set(this.hasBodyExam.get());
        }
    }

    // 体检预约的点击事件
    public void bodyExamAppoint() {
        ToastUtils.showShort("体检预约...");
    }

    // 确认预约的点击事件
    public void checkAppoint() {
        ToastUtils.showShort("确认预约...");
    }
}

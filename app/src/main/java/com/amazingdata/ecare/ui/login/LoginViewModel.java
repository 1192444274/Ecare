package com.amazingdata.ecare.ui.login;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.DialogListener;
import com.amazingdata.ecare.utils.Constant;
import com.amazingdata.ecare.utils.SPUtils;
import com.amazingdata.ecare.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/18 - 21:15
 */
// LoginActivity 的ViewModel
public class LoginViewModel extends BaseViewModel {

    // 学号的被观察者
    public final ObservableField<String> stuId = new ObservableField<>();
    // 密码的被观察者
    public final ObservableField<String> password = new ObservableField<>();
    // 自动登录的被观察者
    public final ObservableBoolean autoLogin = new ObservableBoolean(false);
    // Dialog的监听
    private DialogListener mDialogListener;

    public void setmDialogListener(DialogListener mDialogListener) {
        this.mDialogListener = mDialogListener;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void onStart() {
        super.onStart();
        // 初始化信息
        init();
    }

    // 初始化账号密码,从SharedPreferences获取数据
    public void init() {
        SPUtils instance = SPUtils.getInstance(Constant.SHARED_PREFERENCE_KEY);
        boolean autoLogin = instance.getBoolean("autoLogin", false);
        String stuId = instance.getString("stuId", "");
        String password = instance.getString("password", "");
        // 本地校验
        if (TextUtils.isEmpty(stuId) || TextUtils.isEmpty(password)) return;
        if (autoLogin) {
            this.stuId.set(stuId);
            this.password.set(password);
            this.autoLogin.set(true);
            login();
        } else {
            this.stuId.set(stuId);
            this.password.set(password);
            this.autoLogin.set(false);
        }
    }

    // 监听Button点击的回调方法
    public void login() {
        // 获取数据
        final String stu_Id = stuId.get();
        final String passWord = password.get();
        final boolean auto_Login = autoLogin.get();

        // 本地校验
        if (TextUtils.isEmpty(stu_Id)) {
            ToastUtils.showShort("学号不得为空!");
            return;
        }
        if (TextUtils.isEmpty(passWord)) {
            ToastUtils.showShort("密码不得为空!");
            return;
        }

        // RxAndroid 处理登录的远程验证,不能占用UI线程(主线程)
        Observable.just("")
                .delay(3, TimeUnit.SECONDS) // 模拟登录延时3S
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        // 显示Dialog
                        mDialogListener.show_Dialog("登录中,请稍等...");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // 移除Dialog
                        mDialogListener.dismiss_Dialog(checkLogin(stu_Id, passWord, auto_Login), stu_Id);
                    }
                });
    }

    // 远程验证方法
    private boolean checkLogin(String stuId, String password, boolean autoLogin) {
        // 验证成功将当前账号导入SharedPreferences
        SPUtils instance = SPUtils.getInstance(Constant.SHARED_PREFERENCE_KEY);
        instance.put("stuId", stuId);
        instance.put("password", password);
        instance.put("autoLogin", autoLogin);
        return true;
    }

}

package com.amazingdata.ecare.ui.login;

import android.app.Application;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.DialogListener;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/18 - 21:15
 */
public class LoginViewModel extends BaseViewModel {

    public final ObservableField<String> stuId = new ObservableField<>();
    public final ObservableField<String> password = new ObservableField<>();
    public final ObservableBoolean autoLogin = new ObservableBoolean();
    private DialogListener mDialogListener;

    public void setmDialogListener(DialogListener mDialogListener) {
        this.mDialogListener = mDialogListener;
    }

    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public void login() {
        final String stu_Id = stuId.get();
        final String passWord = password.get();
        final boolean auto_Login = autoLogin.get();
        Observable.just("")
                .delay(3, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDialogListener.show_Dialog("登录中,请稍等...");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mDialogListener.dismiss_Dialog(checkLogin(stu_Id, passWord, auto_Login), stu_Id);
                    }
                });
    }

    private boolean checkLogin(String stuId, String password, boolean autoLogin) {
        return true;
    }

}

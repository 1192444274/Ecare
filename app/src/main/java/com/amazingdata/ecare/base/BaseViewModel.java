package com.amazingdata.ecare.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.support.annotation.NonNull;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:00
 */
// 偷得BaseViewModel基类
public class BaseViewModel extends AndroidViewModel implements ViewLifecycleObserver {

    public BaseViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 以下的生命周期方法全部交由继承类来实现
     */
    @Override
    public void onAny(LifecycleOwner owner, Lifecycle.Event event) {

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }
}

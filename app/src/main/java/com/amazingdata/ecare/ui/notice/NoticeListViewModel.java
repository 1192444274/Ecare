package com.amazingdata.ecare.ui.notice;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.entity.Notice;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/21 - 9:57
 */
// NoticeListActivity 的ViewModel
public class NoticeListViewModel extends BaseViewModel {

    // 公告列表的被观察者
    public ObservableArrayList<Notice> mDatas = new ObservableArrayList<>();

    private DialogListenerUtils.ProgressDialogListener mProgressDialogListener;

    public void setProgressDialogListener(DialogListenerUtils.ProgressDialogListener progressDialogListener) {
        this.mProgressDialogListener = progressDialogListener;
    }

    public NoticeListViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化Recycle Adapter适配器(从View视图传入context上下文)
    public void initAdapter() {
        Observable.just("")
                .delay(2, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mProgressDialogListener.show("正在加载中...");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        // 获取网络数据
                        // ...

                        // 以下为模拟数据
                        mDatas.add(new Notice("今天星期一", new Date(2019 - 1900, 1, 20), "星期一真好...", null));
                        mDatas.add(new Notice("今天星期二", new Date(2019 - 1900, 1, 20), "星期二真好...", null));
                        mDatas.add(new Notice("今天星期三", new Date(2019 - 1900, 1, 20), "星期三真好...", null));
                        mDatas.add(new Notice("今天星期四", new Date(2019 - 1900, 1, 20), "星期四真好...", null));
                        mDatas.add(new Notice("今天星期五", new Date(2019 - 1900, 1, 20), "星期五真好...", null));
                        mDatas.add(new Notice("今天星期六", new Date(2019 - 1900, 1, 20), "星期六真好...", null));
                        mProgressDialogListener.dissmiss();
                    }
                });
    }
}

package com.amazingdata.ecare.ui.notice;

import android.app.Application;
import android.content.Context;
import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;

import com.amazingdata.ecare.base.BaseBindingAdapter;
import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.ListItemListener;
import com.amazingdata.ecare.entity.Notice;

import java.util.Date;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/21 - 9:57
 */
// NoticeListActivity 的ViewModel
public class NoticeListViewModel extends BaseViewModel {

    // Adapter 列表适配器
    public NoticeListAdapter mAdapter;
    // 公告列表的被观察者
    public ObservableArrayList<Notice> mDatas = new ObservableArrayList<>();
    // 监听器接口,View NoticeListActivity中回调
    private ListItemListener mListItemListener;

    public void setListItemListener(ListItemListener ListItemListener) {
        this.mListItemListener = ListItemListener;
    }

    public NoticeListViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化Recycle Adapter适配器(从View视图传入context上下文)
    public void initAdapter(Context context) {
        mAdapter = new NoticeListAdapter(context, mDatas);
        Observable.just(initData())
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        // 数据获取成功设置点击监听
                        if (integer == 0) {
                            mAdapter.setItemClickListener(new BaseBindingAdapter.recycleViewItemClickListener<Notice>() {
                                @Override
                                public void onItemClick(Notice data) {
                                    mListItemListener.onItemClick(data);
                                }
                            });
                        }
                    }
                });
    }

    private int initData() {
        // 获取网络数据
        // ...

        // 以下为模拟数据
        mDatas.add(new Notice("今天星期一", new Date(2019 - 1900, 1, 20), "星期一真好...", null));
        mDatas.add(new Notice("今天星期二", new Date(2019 - 1900, 1, 20), "星期二真好...", null));
        mDatas.add(new Notice("今天星期三", new Date(2019 - 1900, 1, 20), "星期三真好...", null));
        mDatas.add(new Notice("今天星期四", new Date(2019 - 1900, 1, 20), "星期四真好...", null));
        mDatas.add(new Notice("今天星期五", new Date(2019 - 1900, 1, 20), "星期五真好...", null));
        mDatas.add(new Notice("今天星期六", new Date(2019 - 1900, 1, 20), "星期六真好...", null));

        // 正确返回0,错误返回1
        return 0;
    }

}

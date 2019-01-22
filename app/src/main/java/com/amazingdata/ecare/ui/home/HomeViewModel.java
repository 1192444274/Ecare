package com.amazingdata.ecare.ui.home;

import android.app.Application;
import android.support.annotation.NonNull;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.entity.Notice;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:25
 */
// HomeFragment 的ViewModel
public class HomeViewModel extends BaseViewModel {

    // 存储数据的List
    public final List<Notice> items = new ArrayList<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    // 初始化数据,从远程获取
    public List<Notice> initData() {
        // 远程网络请求数据
        // ...

        // 模拟数据
        items.add(new Notice("周一", new Date(), "周一...", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547889190260&di=21c5e0e27322f0ff480a3c9c28d9d37b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201306%2F07%2F20130607160603_fSMZ2.jpeg"));
        items.add(new Notice("周一", new Date(), "周一...", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547889190260&di=21c5e0e27322f0ff480a3c9c28d9d37b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201306%2F07%2F20130607160603_fSMZ2.jpeg"));
        items.add(new Notice("周一", new Date(), "周一...", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547889190260&di=21c5e0e27322f0ff480a3c9c28d9d37b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201306%2F07%2F20130607160603_fSMZ2.jpeg"));
        items.add(new Notice("周一", new Date(), "周一...", "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547889190260&di=21c5e0e27322f0ff480a3c9c28d9d37b&imgtype=0&src=http%3A%2F%2Fb-ssl.duitang.com%2Fuploads%2Fitem%2F201306%2F07%2F20130607160603_fSMZ2.jpeg"));
        return items;
    }

}

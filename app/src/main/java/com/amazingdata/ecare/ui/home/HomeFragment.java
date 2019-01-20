package com.amazingdata.ecare.ui.home;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.base.DialogListener;
import com.amazingdata.ecare.databinding.FragmentHomeBinding;
import com.amazingdata.ecare.entity.Notice;
import com.amazingdata.ecare.ui.MainActivity;
import com.amazingdata.ecare.ui.notice.NoticeDetailActivity;
import com.amazingdata.ecare.utils.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:24
 */
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    private List<Notice> datas;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_home;
    }

    @Override
    public int initVariableId() {
        return BR.homeViewModel;
    }

    @Override
    public HomeViewModel initViewModel() {
        return ViewModelProviders.of(this).get(HomeViewModel.class);
    }

    @Override
    public void initData() {
        // RxAndroid 观察者模式
        // 在子线程中观察,在主线程(UI线程)更新UI
        Observable.just(initViewModel().initData())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Notice>>() {
                    @Override
                    public void accept(List<Notice> notices) throws Exception {
                        datas = notices;
                        List<String> imgUrls = new ArrayList<>();
                        for (Notice notice : notices) {
                            imgUrls.add(notice.getIconUrl());
                        }
                        // 设置图片资源Url
                        binding.homeXbanner.setData(imgUrls, null);
                    }
                });
        // banner 设置点击监听
        binding.homeXbanner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constant.BUNDLE_KEY, datas.get(position));
                HomeFragment.this.startActivity(NoticeDetailActivity.class, bundle);
            }
        });
        // banner 设置图片加载方式
        binding.homeXbanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(getActivity())
                        .load((String) model)
                        .apply(new RequestOptions().placeholder(R.mipmap.ic_launcher))
                        .apply(new RequestOptions().error(R.mipmap.ic_launcher_round))
                        .into((ImageView) view);
            }
        });

    }

}

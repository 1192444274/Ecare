package com.amazingdata.ecare.ui.drugstore;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.entity.DrugInfo;
import com.amazingdata.ecare.entity.DrugTaking;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:39
 */
public class DrugStoreViewModel extends BaseViewModel {

    // 药品类型集合的观察对象
    public ObservableArrayList<String> drugTypes = new ObservableArrayList<>();
    // 取药信息集合的观察对象
    public ObservableArrayList<DrugTaking> drugTakings = new ObservableArrayList<>();
    // 药品信息集合的观察对象
    public ObservableArrayList<DrugInfo> drugInfos = new ObservableArrayList<>();
    // 显示取药RecycleView的观察对象
    public ObservableInt showTakeDrug = new ObservableInt(View.INVISIBLE);
    // 显示药品RecycleView的观察对象
    public ObservableInt showDrugList = new ObservableInt(View.INVISIBLE);
    // 进度条对话框的监听接口
    private DialogListenerUtils.ProgressDialogWithModeListener mProgressDialogWithModeListener;
    public static final int FRESH_LEFT_LIST = 1; // 刷新药品类型的mode
    public static final int FRESH_TAKEDRUG_LIST = 2; // 刷新取药信息的mode
    public static final int FRESH_DRUG_LIST = 3; // 刷新药品信息的mode

    public void setProgressDialogWithModeListener(DialogListenerUtils.ProgressDialogWithModeListener progressDialogWithModeListener) {
        this.mProgressDialogWithModeListener = progressDialogWithModeListener;
    }

    public DrugStoreViewModel(@NonNull Application application) {
        super(application);
    }

    public void initDrugTypeList() {
        // 加载数据
        Observable.just("")
                .delay(2, TimeUnit.SECONDS) // 模拟2s的延时
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mProgressDialogWithModeListener.show("正在加载数据...", FRESH_LEFT_LIST);
                        // 模拟请求网络
                        // ...
                        // 清空数据以防止多批次add
                        drugTypes.clear();
                        drugTypes.add("呼吸系统药品");
                        drugTypes.add("消化系统药品");
                        drugTypes.add("清热解毒药品");
                        drugTypes.add("皮肤病药品");
                        drugTypes.add("五官科药品");
                        drugTypes.add("滋补类药品");
                        drugTypes.add("其他");
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mProgressDialogWithModeListener.dissmiss(FRESH_LEFT_LIST);
                    }
                });
    }

    public void initDrugTakeingList() {
        // 加载数据
        Observable.just("")
                .delay(2, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mProgressDialogWithModeListener.show("正在加载数据...", FRESH_TAKEDRUG_LIST);
                        // 模拟请求网络
                        // ...
                        drugTakings.clear();
                        drugTakings.add(new DrugTaking(1234, 0, null, null));
                        drugTakings.add(new DrugTaking(1235, 1, new Date(2019 - 1900, 0, 25, 17, 0), "https://gss0.bdstatic.com/94o3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike80%2C5%2C5%2C80%2C26/sign=fa9140accd95d143ce7bec711299e967/2934349b033b5bb571dc8c5133d3d539b600bc12.jpg"));
                        drugTakings.add(new DrugTaking(1236, 2, new Date(2019 - 1900, 0, 24, 13, 5), null));
                        drugTakings.add(new DrugTaking(1237, 2, new Date(2019 - 1900, 0, 23, 10, 25), null));
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mProgressDialogWithModeListener.dissmiss(FRESH_TAKEDRUG_LIST);
                    }
                });
    }

    public void initDrugList() {
        // 加载数据
        Observable.just("")
                .delay(2, TimeUnit.SECONDS)
                .observeOn(Schedulers.newThread())
                .subscribeOn(Schedulers.newThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mProgressDialogWithModeListener.show("正在加载数据...", FRESH_TAKEDRUG_LIST);
                        // 模拟请求网络
                        // ...
                        drugInfos.clear();
                        drugInfos.add(new DrugInfo("复方甘草片", "呼吸系统用药", "镇咳祛痰", null, 0, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548330432400&di=fe7213badfb3ae9cba4d827ee5ba6d70&imgtype=0&src=http%3A%2F%2Fpic.qbaobei.com%2FUploads%2FPicture%2F2016-07-16%2F5789a2d7682ac.png"));
                        drugInfos.add(new DrugInfo("阿斯美", "呼吸系统用药", "支气管哮喘", null, 0, "http://i13.wkimg.com/itemyaobox/2015/06/18/558261656daf1.jpg"));
                        drugInfos.add(new DrugInfo("强力枇杷露", "呼吸系统用药", "止咳祛痰", null, 0, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548330683057&di=235497505d5e3d466fa61c27592f6f16&imgtype=0&src=http%3A%2F%2Fc1.yaofangwang.net%2FCommon%2FUpload%2FMedicine%2F649%2F649442%2F5cc5d615-6bed-454a-9c95-4bc37810c72b5551.jpg_syp.jpg"));
                        drugInfos.add(new DrugInfo("布洛芬混悬液", "呼吸系统用药", "头痛、发热", null, 0, "https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2065831739,1817068326&fm=26&gp=0.jpg"));
                        drugInfos.add(new DrugInfo("复方氨酚烷胺胶囊", "呼吸系统用药", "流行性感冒", null, 0, "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3790521577,2109388860&fm=26&gp=0.jpg"));
                        drugInfos.add(new DrugInfo("阿莫西林胶囊", "呼吸系统用药", "消炎", null, 0, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548330759421&di=6e69173de958afa07746447aa1c3b1fc&imgtype=0&src=http%3A%2F%2Fimg.yaopinnet.com%2Fimg500%2F201501%2F1891133906290115.jpg"));
                    }
                })
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        mProgressDialogWithModeListener.dissmiss(FRESH_DRUG_LIST);
                    }
                });
    }
}

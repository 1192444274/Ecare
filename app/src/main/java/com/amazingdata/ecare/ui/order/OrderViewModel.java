package com.amazingdata.ecare.ui.order;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;
import android.view.View;

import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.base.VMConnectedListenerUtils;
import com.amazingdata.ecare.entity.ShoppingCartItem;
import com.amazingdata.ecare.utils.DataFormatUtils;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:42
 */
public class OrderViewModel extends BaseViewModel {

    // adapter的观察者购物车条目数据列表
    public ObservableArrayList<ShoppingCartItem> datas = new ObservableArrayList<>();
    // 需结算数目的观察者
    public ObservableField<String> textPay = new ObservableField<>("结算");
    // 需支付总额的观察者
    public ObservableField<String> textPrice = new ObservableField<>("¥ 0");
    // 底部管理栏是否出现的观察者
    public ObservableInt showManager = new ObservableInt(View.INVISIBLE);
    // 全选checkBox是否全选的观察者
    public ObservableBoolean getAll = new ObservableBoolean(false);
    // 购物车为空是否显示的观察者
    public ObservableInt showEmpty = new ObservableInt(View.INVISIBLE);

    // View视图与ViewModel联系的监听接口
    private VMConnectedListenerUtils.onViewChangeUI mOnViewChangeUI;
    // 对话框监听接口
    private DialogListenerUtils.ProgressDialogListener mProgressDialogListener;

    // 一些状态值
    public static final int MODE_DELETE_CLICK = 1;
    public static final int MODE_PAY_CLICK = 2;
    public static final int MODE_GETALL_CHECK = 3;
    public static final int MODE_GETALL_UNCHECK = 4;

    public void setOnViewChangeUI(VMConnectedListenerUtils.onViewChangeUI onViewChangeUI) {
        this.mOnViewChangeUI = onViewChangeUI;
    }

    public void setProgressDialogListener(DialogListenerUtils.ProgressDialogListener progressDialogListener) {
        this.mProgressDialogListener = progressDialogListener;
    }

    public OrderViewModel(@NonNull Application application) {
        super(application);
    }

    // 点击删除的回调事件
    public void deleteCartItems() {
        mOnViewChangeUI.changeUI(MODE_DELETE_CLICK);
    }

    // 点击支付的回调事件
    public void payCartItems() {
        mOnViewChangeUI.changeUI(MODE_PAY_CLICK);
    }

    // 点击checkBox的回调事件
    // 为什么不适用checkBox的OnCheckChangeListener呢?
    // 因为页面购物车条目的全选会导致全选的选中,而选中又会触发OnCheckChangeListener
    // 导致该方法没有必要的多次调用,可能会出错
    public void checkModeChange(boolean checked) {
        // 因为checked是传入的当前的观察对象getAll
        // 所以点击后要重设checked值
        checked = !checked;
        getAll.set(checked);
        // 点击全选的回调事件
        if (checked)
            mOnViewChangeUI.changeUI(MODE_GETALL_CHECK);
        else
            mOnViewChangeUI.changeUI(MODE_GETALL_UNCHECK);
    }

    // 初始化adapter的数据
    public void initAdapterData() {
        Observable.just("")
                .delay(2, TimeUnit.SECONDS) // 模拟网络延时
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
                        // 模拟数据
                        datas.add(new ShoppingCartItem("强力枇杷露", (float) 8.88, 1, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548409012038&di=001318a3833623689fcff79552fd82c0&imgtype=0&src=http%3A%2F%2Fimg8.3156.cn%2Fupload%2Fimages%2F20170313%2F14893754828.jpg%3Fw%3D300%26h%3D225"));
                        datas.add(new ShoppingCartItem("阿莫西林胶囊", (float) 16.66, 1, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548409059001&di=467e2c402fba699bbd7171049f48755a&imgtype=0&src=http%3A%2F%2Fimg.800pharm.com%2Fimages%2F20160531%2F20160531170325_693.jpg"));
                        datas.add(new ShoppingCartItem("阿莫西林胶囊", (float) 16.66, 1, "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1548409059001&di=467e2c402fba699bbd7171049f48755a&imgtype=0&src=http%3A%2F%2Fimg.800pharm.com%2Fimages%2F20160531%2F20160531170325_693.jpg"));

                        if (datas.isEmpty()) // 如果数据为空,则显示购物车为空
                            showEmpty.set(View.VISIBLE);

                        mProgressDialogListener.dissmiss();
                    }
                });
    }

    // 更新底部管理栏数据
    public void updateBottom(List<Integer> selectedIndexList) {
        // 如果当前选中条目为空
        if (null == selectedIndexList || selectedIndexList.isEmpty()) {
            textPay.set("结算");
            textPrice.set(DataFormatUtils.getCartPrice(0));
            showManager.set(View.INVISIBLE);
        } else { // 如果当前选中条目不为空
            // 如果全部选中
            if (selectedIndexList.size() == datas.size())
                getAll.set(true);
            else // 如果一部分选中
                getAll.set(false);
            textPay.set("结算(" + selectedIndexList.size() + ")");
            float sum = 0;
            for (int index : selectedIndexList) { // 计算总额
                ShoppingCartItem cItem = datas.get(index);
                sum += cItem.getGoodsNum() * cItem.getGoodsPrice();
            }
            textPrice.set(DataFormatUtils.getCartPrice(sum));
        }
    }

    // 删除购物车条目数据
    public void deleteData(List<Integer> selectedIndexList) {
        // 如果选中条目数据不为空
        if (null != selectedIndexList && !selectedIndexList.isEmpty()) {
            // 从小到大排序
            Collections.sort(selectedIndexList);
            // 逆序,因为如果从小到大删,小的删掉了
            // 原来大的index变小,后面的需要删除index就会越界
            Collections.reverse(selectedIndexList);
            for (int index : selectedIndexList) { // 逆序删除
                datas.remove(index);
            }
            if (datas.isEmpty()) // 如果删完后购物车为空
                showEmpty.set(View.VISIBLE);
        }
    }
}

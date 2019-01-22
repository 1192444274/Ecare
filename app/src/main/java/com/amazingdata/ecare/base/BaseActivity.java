package com.amazingdata.ecare.base;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amazingdata.ecare.utils.MaterialDialogUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author Xiong
 * @date 2019/1/18 - 13:51
 */
// 偷的BaseActivity基类
public abstract class BaseActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends AppCompatActivity implements LifecycleOwner {
    // binding 对象
    protected B binding;
    // viewModel 对象
    protected VM viewModel;
    // 绑定的viewModel id (BR中)
    private int viewModelId;
    // progressDialog 对象
    private MaterialDialog progressDialog;
    // basicDialog 对象
    private MaterialDialog basicDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //页面接受的参数方法
        initParam();
        //私有的初始化Databinding和ViewModel方法
        initViewDataBinding(savedInstanceState);
        //页面数据初始化方法
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解除ViewModel生命周期感应
        getLifecycle().removeObserver(viewModel);
        viewModel = null;
        binding.unbind();
    }

    // 交给继承类重写
    public void initParam() {

    }

    // 注入绑定
    private void initViewDataBinding(Bundle savedInstanceState) {
        // DataBindingUtil类需要在project的build中配置 dataBinding { enabled true }
        // 同步后会自动关联android.databinding包
        binding = DataBindingUtil.setContentView(this, initContentView(savedInstanceState));
        viewModelId = initVariableId();
        viewModel = initViewModel();
        if (viewModel == null) {
            Class modelClass;
            Type type = getClass().getGenericSuperclass();
            if (type instanceof ParameterizedType) {
                modelClass = (Class) ((ParameterizedType) type).getActualTypeArguments()[1];
            } else {
                //如果没有指定泛型参数，则默认使用BaseViewModel
                modelClass = ViewModel.class;
            }
            viewModel = (VM) createViewModel(this, modelClass);
        }
        //关联ViewModel
        binding.setVariable(viewModelId, viewModel);
        //让ViewModel拥有View的生命周期感应
        getLifecycle().addObserver(viewModel);
    }

    // 交给继承类重写
    public void initData() {

    }

    // 显示耗时progressDialog
    public void showProgressDialog(String title) {
        if (progressDialog != null) {
            progressDialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showIndeterminateProgressDialog(this, title, true);
            progressDialog = builder.show();
        }
    }

    // 移除progressDialog
    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public MaterialDialog getBasicDialog() {
        return basicDialog;
    }

    // 显示basicDialog
    public void showBasicDialog(String content, String positiveText, String negativeText) {
        if (basicDialog != null) {
            basicDialog.show();
        } else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showBasicDialog(this, content, positiveText, negativeText);
            basicDialog = builder.show();
        }
    }

    // 必须由继承类重写,返回当前的根布局文件的R id
    public abstract int initContentView(Bundle savedInstanceState);

    // 必须由继承类重写,返回当前viewModel的BR id
    public abstract int initVariableId();

    // 交给继承类重写,返回当前的具体的ViewModel对象
    public VM initViewModel() {
        return null;
    }

    /**
     * 创建ViewModel
     *
     * @param cls
     * @param <T>
     * @return
     */
    public <T extends ViewModel> T createViewModel(FragmentActivity activity, Class<T> cls) {
        return ViewModelProviders.of(activity).get(cls);
    }

    /**
     * 跳转页面
     *
     * @param clz 所跳转的目的Activity类
     */
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    /**
     * 跳转页面
     *
     * @param clz    所跳转的目的Activity类
     * @param bundle 跳转所携带的信息
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }
}

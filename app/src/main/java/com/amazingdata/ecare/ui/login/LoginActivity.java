package com.amazingdata.ecare.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.databinding.ActivityLoginBinding;
import com.amazingdata.ecare.ui.MainActivity;
import com.amazingdata.ecare.utils.Constant;
import com.amazingdata.ecare.utils.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;

// 登录页面
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initParam() {
        ImmersionBar.with(this).init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy(); //必须调用该方法，防止内存泄漏
    }

    @Override
    public int initVariableId() {
        return BR.loginViewModel;
    }

    @Override
    public LoginViewModel initViewModel() {
        return ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Override
    public void initData() {
        // 设置监听的回调,响应登录
        viewModel.setmDialogListener(new DialogListenerUtils.ProgressDialogListener() {
            @Override
            public void show(String content) {
                // 显示Dialog
                showProgressDialog(content);
            }

            @Override
            public void dissmiss() {
                // 移除Dialog
                dismissProgressDialog();
                // 当验证正确,携带用户名进入MainActivity
                if (viewModel.checkLogin(viewModel.stuId.get(), viewModel.password.get(), viewModel.autoLogin.get())) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BUNDLE_KEY, viewModel.stuId.get());
                    startActivity(MainActivity.class, bundle);
                    finish();
                } else // 验证失败Toast提示
                    ToastUtils.showShort("对不起,登录失败...");
            }
        });
    }
}

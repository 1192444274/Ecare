package com.amazingdata.ecare.ui.login;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.base.DialogListener;
import com.amazingdata.ecare.databinding.ActivityLoginBinding;
import com.amazingdata.ecare.ui.MainActivity;
import com.amazingdata.ecare.utils.Constant;
import com.amazingdata.ecare.utils.ToastUtils;

public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_login;
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
        viewModel.setmDialogListener(new DialogListener() {
            @Override
            public void show_Dialog(String title) {
                showDialog(title);
            }

            @Override
            public void dismiss_Dialog(boolean isCheck, String stuId) {
                dismissDialog();
                if (isCheck) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constant.BUNDLE_KEY, stuId);
                    startActivity(MainActivity.class, bundle);
                    finish();
                } else
                    ToastUtils.showShort("对不起,登录失败...");
            }

        });
    }
}

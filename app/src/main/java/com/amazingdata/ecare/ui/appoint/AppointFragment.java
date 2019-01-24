package com.amazingdata.ecare.ui.appoint;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseFragment;
import com.amazingdata.ecare.base.DialogListenerUtils;
import com.amazingdata.ecare.databinding.FragmentAppointBinding;
import com.amazingdata.ecare.ui.timechooser.TimeChooserActivity;
import com.amazingdata.ecare.utils.Constant;
import com.amazingdata.ecare.utils.MaterialDialogUtils;

import java.util.List;

/**
 * @author Xiong
 * @date 2019/1/18 - 14:28
 */
// 预约页面
public class AppointFragment extends BaseFragment<FragmentAppointBinding, AppointViewModel> {

    // list选择对话框
    private MaterialDialog listDialog;

    // 显示list选择对话框
    public void showListDialog(String title, List<String> items, String pickHint) {
        if (null != listDialog)
            listDialog.show();
        else {
            MaterialDialog.Builder builder = MaterialDialogUtils.showSingleListDialog(getActivity(), title, items, pickHint);
            listDialog = builder.show();
        }
    }

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_appoint;
    }

    @Override
    public int initVariableId() {
        return BR.appointViewModel;
    }

    @Override
    public void initData() {
        // 初始化数据
        viewModel.init();

        // 设置挂号和体检切换的radioButton的选择监听
        binding.appointRgMode.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                viewModel.onModeChange(checkedId == R.id.appoint_rb_register);
            }
        });

        // 设置取消预约的点击监听
        viewModel.setBasicDialogListener(new DialogListenerUtils.BasicDialogListener() {
            @Override
            public void show(String content, String positiveText, String negativeText) {
                AppointFragment.this.showBasicDialog(content, positiveText, negativeText);
            }
        });

        // 设置对话框按钮的点击监听,处理回调
        MaterialDialogUtils.setBasicDialogClickListener(new DialogListenerUtils.BasicDialogClickListener() {
            @Override
            public void onPositive() {
                viewModel.cancelLogical();
            }

            @Override
            public void onNegative() {

            }
        });

        // 设置点击选择科室的点击监听
        viewModel.setSingleListDialogListener(new DialogListenerUtils.SingleListDialogListener() {
            @Override
            public void show(String content, List<String> items, String pickHint) {
                showListDialog(content, items, pickHint);
            }
        });

        // 设置List选择对话框的确认监听
        MaterialDialogUtils.setSingleListDialogPickListener(new DialogListenerUtils.SingleListDialogPickListener() {
            @Override
            public void pick(String item) {
                viewModel.dept.set(item);
            }
        });

        // 体检预约/挂号择时的回调监听
        viewModel.setPickTimeModeListener(new AppointViewModel.PickTimeModeListener() {
            @Override
            public void PickRegister(boolean isRegister) {
                Bundle bundle = new Bundle();
                Log.e("PickRegister: ", isRegister + "");
                bundle.putBoolean(Constant.BUNDLE_KEY, isRegister);
                startActivity(TimeChooserActivity.class, bundle);
            }
        });
    }
}

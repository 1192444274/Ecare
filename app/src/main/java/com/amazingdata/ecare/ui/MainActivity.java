package com.amazingdata.ecare.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.amazingdata.ecare.R;
import com.amazingdata.ecare.BR;
import com.amazingdata.ecare.base.BaseActivity;
import com.amazingdata.ecare.base.BaseViewModel;
import com.amazingdata.ecare.databinding.ActivityMainBinding;
import com.amazingdata.ecare.ui.appoint.AppointFragment;
import com.amazingdata.ecare.ui.drugstore.DrugStoreFragment;
import com.amazingdata.ecare.ui.home.HomeFragment;
import com.amazingdata.ecare.ui.notice.NoticeListActivity;
import com.amazingdata.ecare.ui.order.OrderFragment;
import com.amazingdata.ecare.ui.record.RecordFragment;

import java.util.ArrayList;
import java.util.List;

// 主Activity
public class MainActivity extends BaseActivity<ActivityMainBinding, BaseViewModel> {

    // 存储Fragments的List
    private List<Fragment> mFragments;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public int initVariableId() {
        return BR.mainViewModel;
    }

    @Override
    public void initData() {
        // 初始化toolbar
        initToolBar();

        // 初始化Fragments
        mFragments = new ArrayList<>();
        mFragments.add(new AppointFragment());
        mFragments.add(new DrugStoreFragment());
        mFragments.add(new HomeFragment());
        mFragments.add(new OrderFragment());
        mFragments.add(new RecordFragment());

        //默认选中首页
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.main_frame, mFragments.get(2));
        transaction.commitAllowingStateLoss();

        // 设置radioButton的点击监听，主要是为了中间突出的Home图标和切换Fragment
        binding.bottombar.rgBottombar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (checkedId != R.id.bottombar_rb_home) {
                    binding.bottombar.bottombarIvHome.setImageResource(R.mipmap.home);
                    binding.includetoolbar.toolbarRight.setVisibility(View.INVISIBLE);
                    switch (checkedId) {
                        case R.id.bottombar_rb_appoint:
                            transaction.replace(R.id.main_frame, mFragments.get(0));
                            binding.includetoolbar.toolbarText.setText("预约");
                            break;
                        case R.id.bottombar_rb_drugstore:
                            transaction.replace(R.id.main_frame, mFragments.get(1));
                            binding.includetoolbar.toolbarText.setText("药房");
                            break;
                        case R.id.bottombar_rb_order:
                            transaction.replace(R.id.main_frame, mFragments.get(3));
                            binding.includetoolbar.toolbarText.setText("购物车");
                            break;
                        case R.id.bottombar_rb_record:
                            transaction.replace(R.id.main_frame, mFragments.get(4));
                            binding.includetoolbar.toolbarText.setText("病历");
                            break;
                    }
                } else {
                    binding.includetoolbar.toolbarRight.setVisibility(View.VISIBLE);
                    binding.bottombar.bottombarIvHome.setImageResource(R.mipmap.home_click);
                    transaction.replace(R.id.main_frame, mFragments.get(2));
                    binding.includetoolbar.toolbarText.setText("首页");
                }
                transaction.commitAllowingStateLoss();
            }
        });

        // NaviationView增加头布局
        View view = binding.mainNavigator.inflateHeaderView(R.layout.drawer_head);

        // 设置NavigtionView的点击监听
        binding.mainNavigator.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // 点击具体条目的相应
                switch (item.getItemId()) {
                    case R.id.drawmenu_faceentry:
                        Toast.makeText(MainActivity.this, "人脸录入...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawmenu_pwdmodify:
                        Toast.makeText(MainActivity.this, "密码修改...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawmenu_setupsystem:
                        Toast.makeText(MainActivity.this, "系统设置...", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.drawmenu_logout:
                        Toast.makeText(MainActivity.this, "注销登录...", Toast.LENGTH_SHORT).show();
                        break;
                }
                item.setChecked(true);
                // 关闭drawerLayout
                binding.mainDrawer.closeDrawer(binding.mainNavigator);
                return true;
            }
        });
    }

    // 初始化toolBar
    private void initToolBar() {
        binding.includetoolbar.toolbarText.setText("首页");
        binding.includetoolbar.toolbarLeft.setImageResource(R.mipmap.personal);
        binding.includetoolbar.toolbarLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.mainDrawer.openDrawer(Gravity.LEFT);
            }
        });
        binding.includetoolbar.toolbarRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(NoticeListActivity.class);
            }
        });
    }
}

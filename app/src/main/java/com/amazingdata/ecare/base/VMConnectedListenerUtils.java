package com.amazingdata.ecare.base;

/**
 * @author Xiong
 * @date 2019/1/23 - 14:18
 */
// View 和 ViewModel联系的监听辅助类
public class VMConnectedListenerUtils {

    // 当viewModel感知到需要进行页面跳转或一些需要在UI界面进行操作的事
    // Activity / Fragment 等能感知的界面使用回调来操作
    public interface onViewChangeUI {
        void changeUI(Object o);
    }
}

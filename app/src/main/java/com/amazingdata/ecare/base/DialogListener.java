package com.amazingdata.ecare.base;

/**
 * @author Xiong
 * @date 2019/1/20 - 10:37
 */
// Dialog对话框的监听器
public interface DialogListener {
    void show_Dialog(String title);

    void dismiss_Dialog(boolean isCheck, String stuId);
}

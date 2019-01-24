package com.amazingdata.ecare.base;

import java.util.List;

/**
 * @author Xiong
 * @date 2019/1/22 - 13:24
 */
// 对话框监听器帮助类
public class DialogListenerUtils {

    // 假进度对话框的监听器接口
    public interface ProgressDialogListener {
        void show(String content);

        void dissmiss();
    }

    // 假进度对话框的监听器接口(携带状态数据)
    public interface ProgressDialogWithModeListener {
        void show(String content, int mode);

        void dissmiss(int mode);
    }

    // 基本对话框(带内容和两个选项)的监听器接口
    public interface BasicDialogListener {
        void show(String content, String positiveText, String negativeText);
    }

    // 基本对话框的点击监听
    public interface BasicDialogClickListener {
        void onPositive();

        void onNegative();
    }

    // 简单List选择对话框的监听接口
    public interface SingleListDialogListener {
        void show(String content, List<String> items, String pickHint);
    }

    // 简单List选择对话框的点击监听
    public interface SingleListDialogPickListener {
        void pick(String item);
    }
}

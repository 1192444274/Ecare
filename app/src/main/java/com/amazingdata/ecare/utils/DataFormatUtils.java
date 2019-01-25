package com.amazingdata.ecare.utils;

/**
 * @author Xiong
 * @date 2019/1/24 - 14:35
 */
// 数据格式化辅助类
public class DataFormatUtils {

    public static String getOrderNum(int oderNum) {
        return "订单号: " + oderNum;
    }

    public static String getCartPrice(float price) {
        return String.format("¥ %.2f", price);
    }
}

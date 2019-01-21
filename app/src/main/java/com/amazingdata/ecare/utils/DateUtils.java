package com.amazingdata.ecare.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Xiong
 * @date 2019/1/21 - 13:10
 */
public class DateUtils {

    private static SimpleDateFormat mDateFormat;
    private static SimpleDateFormat mTimeFormat;

    public static String getDate(Date date) {
        if (mDateFormat == null)
            mDateFormat = new SimpleDateFormat(Constant.DATE_TO_STRING);
        return mDateFormat.format(date);
    }

    public static String getTime(Date date) {
        if (mTimeFormat == null)
            mTimeFormat = new SimpleDateFormat(Constant.TIME_TO_STRING);
        return mTimeFormat.format(date);
    }

}

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
    private static SimpleDateFormat mTimeFormatToMinute;

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

    public static String getNextHalf(Date date) {
        if (mTimeFormatToMinute == null) {
            mTimeFormatToMinute = new SimpleDateFormat(Constant.TIME_TO_STRING_MINUTE);
        }
        String format = mTimeFormatToMinute.format(date);
        String end;
        if (date.getMinutes() == 30) {
            end = "-" + (date.getHours() + 1) + ":00";
        } else {
            end = "-" + date.getHours() + ":30";
        }
        return format + end;
    }

}

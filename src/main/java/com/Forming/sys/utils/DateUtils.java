package com.Forming.sys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * 字符串转化为Date
 */
public class DateUtils {
    public static final String DATE_PATTERN1 = "yyyy-MM-dd hh:mm:ss";
    public static Date stringToDate(String msg, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format.parse(msg);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}

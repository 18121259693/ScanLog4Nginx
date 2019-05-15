package com.zzzmh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;


public class CommonUtils {
    public static boolean isNotNull(Object o) {
        return o != null;
    }

    public static boolean isNotBlank(Object o) {
        return o != null && !"".equals(o);
    }

    public static void print(String string) {
        if ("0".equals(get("print"))) {
            System.out.println(string);
        }
    }
    public static Date dateParse(String string, String pattern) {
        Date result = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            if (isNotBlank(string)) {
                result = format.parse(string);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static Date dateParse(String string) {
        return dateParse(string,"yyyy-MM-dd HH:mm:ss");
    }

    public static String dateFormat(Date date, String pattern) {
        String result = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            if (isNotNull(date)) {
                result = format.format(date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String dateFormat(Date date) {
        return dateFormat(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getNextDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static String get(String key) {
        ResourceBundle resource = ResourceBundle.getBundle("config");
        return resource.getString(key);
    }

    public static void main(String[] args) {


    }

}

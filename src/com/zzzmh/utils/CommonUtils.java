package com.zzzmh.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class CommonUtils {
	public static boolean isNotNull(Object o){
		return o != null;
	}
	public static boolean isNotBlank(Object o){
		return o != null && !"".equals(o);
	}

	public static void print(String string){
		if("0".equals(get("print"))){
			System.out.println(string);
		}
	}

	public static Date dateParse(String string){
		Date result = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(isNotBlank(string)){
				result =  format.parse(string);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String dateFormat(Date date){
		String result = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if(isNotNull(date)){
				result =  format.format(date);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	public static String get(String key) {
		ResourceBundle resource = ResourceBundle.getBundle("config");
		return resource.getString(key); 
	}
	public static void main(String[] args) {


	}

}

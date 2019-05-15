package com.zzzmh.utils;

import java.util.ResourceBundle;


public class SystemUtils {
	public static String get(String key) {
		ResourceBundle resource = ResourceBundle.getBundle("config");
		return resource.getString(key); 
	}
	public static void main(String[] args) {
		System.out.println(get("SaveLogMaxDay"));

	}

}

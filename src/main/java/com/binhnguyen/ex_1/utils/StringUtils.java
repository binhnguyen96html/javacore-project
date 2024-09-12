package com.binhnguyen.ex_1.utils;

public class StringUtils {
	public static boolean isNullOrEmpty(String value) {
		if(value != null && value == "") {
			return false;
		}
		
		return true;
	}
}

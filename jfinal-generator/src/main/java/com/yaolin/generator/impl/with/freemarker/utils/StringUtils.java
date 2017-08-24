package com.yaolin.generator.impl.with.freemarker.utils;
/**
 * 
 * @author yaolin
 *
 */
public class StringUtils {
	public static String firstCharUpper(String s) {
		if (s == null) {
			return null;
		}
		if (s.length() == 1) {
			return s.toUpperCase();
		}
		String ss = s.substring(0, 1);
		return ss.toUpperCase() + s.substring(1);
	}
}

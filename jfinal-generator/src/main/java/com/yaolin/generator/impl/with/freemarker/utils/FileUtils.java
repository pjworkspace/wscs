package com.yaolin.generator.impl.with.freemarker.utils;

import java.io.File;
/**
 * 
 * @author yaolin
 *
 */
public class FileUtils {

	public static String combine(String firstPath, String secondPath) {
		if (secondPath == null || secondPath.length() == 0) {
			return firstPath;
		}
		if (firstPath == null || firstPath.length() == 0) {
			return secondPath;
		}
		if (firstPath.endsWith(File.separator) && secondPath.endsWith(File.separator)) {
			return firstPath.substring(0, firstPath.length() - 1) + secondPath;
		}
		if (!firstPath.endsWith(File.separator) && !secondPath.endsWith(File.separator)) {
			return firstPath + File.separator + secondPath;
		}
		return firstPath + secondPath;
	}
}

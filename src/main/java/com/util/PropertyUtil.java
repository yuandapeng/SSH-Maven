package com.util;

import java.util.ResourceBundle;

public class PropertyUtil {

	private static final ResourceBundle bundle = java.util.ResourceBundle.getBundle("ssh");
	
	public static final String getPropertyVal(String propertyName) {
		return bundle.getString(propertyName);
	}
}
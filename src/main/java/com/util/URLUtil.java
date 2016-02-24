package com.util;

public class URLUtil {

	//无需验证的url
	public static boolean isNotCheckURL(String url) {
		return url.contains("/index.action")||url.contains("/makeCode.action")||url.contains("/checkLogin.action")||url.contains("/login.action")||url.contains("/qrcodeLogin.action")||url.contains("/saveUser.action")||url.contains("/isLive.action");
	}
	
}

package com.util;

import java.util.HashMap;

@SuppressWarnings("unused")
public class Constants {
	public static final String BASE_URL = PropertyUtil.getPropertyVal("BASE_URL");
	public static final String UPLOAD_PATH=PropertyUtil.getPropertyVal("UPLOAD_PATH");
	public static final String LOGIN_USER_SESSION_KEY="login_user_session_key";
	public static final HashMap<String, Object> mp=new HashMap<String, Object>();
	
	
	public Constants() {
	
	}
	
	
	public enum Status{
		FAILURE("失败","0"),
		SUCCESS("成功","1"),
		ERR_USER_ISLIVE("用户已存在","ERR_USER_ISLIVE"),
		ERR_SESSION_TIMEOUT("登陆过期，请重新登陆", "err_session_timeout");
		private String name;
		private String status;
		private Status(String name, String status) {
			this.name = name;
			this.status = status;
		}
		
		public String toString() {
			return this.status;
		}
		public String getStatus() {
			return this.status;
		}
		
	}
	
	
	
	

}

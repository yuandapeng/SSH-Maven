package com.filter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class CheckLoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1710934076172945241L;

	public String intercept(ActionInvocation actionInvocation) throws Exception {
		System.out.println("intercept");
		
		return "";
		
	}

}

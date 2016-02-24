package com.filter;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;


public class StrutsAuditInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6072119656244948297L;

	protected void after(ActionInvocation dispatcher, String result)
			throws Exception {
	      System.out.println("after");
	}

	protected void before(ActionInvocation invocation) throws Exception {
		 System.out.println("before");
	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String result;
		before(invocation);
		result = invocation.invoke();
		after(invocation, result);
		return result;
	}

}

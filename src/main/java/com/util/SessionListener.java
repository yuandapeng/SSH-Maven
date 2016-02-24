package com.util;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
/**
 * 
 * 监听session生命周期
 * @author 袁培朋
 *
 */
public class SessionListener implements HttpSessionListener  {


	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		System.err.println("createSessionId:"+httpSessionEvent.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		System.err.println("destroySessionId:"+httpSessionEvent.getSession().getId());
	}

}

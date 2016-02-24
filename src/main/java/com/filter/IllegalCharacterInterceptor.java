package com.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

@SuppressWarnings({"rawtypes","unused"})
public class IllegalCharacterInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 通过核心调度器invocation来获得调度的Action上下文
		ActionContext actionContext = invocation.getInvocationContext();
		// 获取Action上下文的值栈
		ValueStack stack = actionContext.getValueStack();
		// 获取上下文的请求参数
		Map valueTreeMap = actionContext.getParameters();
		// 获得请求参数集合的迭代器
		Iterator iterator = valueTreeMap.entrySet().iterator();
		// 遍历组装请求参数
		while (iterator.hasNext()) {
			// 获得迭代的键值对
			Entry entry = (Entry) iterator.next();
			// 获得键值对中的键值
			String key = (String) entry.getKey();
			// 原请求参数，因为有可能一键对多值所以这里用的String[]
			String[] oldValues = null;
			// 对参数值转换成String类型的
			if (entry.getValue() instanceof String) {
				oldValues = new String[] { entry.getValue().toString() };
			} else {
				oldValues = (String[]) entry.getValue();
			}
			// 对请求参数过滤处理
			if (oldValues.length > 0) {
				for (int i = 0; i < oldValues.length; i++) {
					oldValues[i] = FilterIllegalCharacter(oldValues[i]
							.toString());
				}
			}
		}
		String result = null;
		try {
			// 调用下一个拦截器，如果拦截器不存在，则执行Action
			result = invocation.invoke();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private String FilterIllegalCharacter(String str) throws IOException {
//		FileReader fr = new FileReader(
//				OaConstants.ILLEGAL_CHARACTER_FILE_PATH);
//		BufferedReader br = new BufferedReader(fr);
//		String line = "";
//		while ((line = br.readLine()) != null) {
//			if (str.contains(line)) {
//				str = str.replaceAll(line, "");
//			}
//		}
		return str;
	}

}

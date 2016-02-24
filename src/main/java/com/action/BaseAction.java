package com.action;

import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import org.apache.struts2.ServletActionContext;

import com.util.Constants;
import com.dto.JsonDto;
import com.util.DateJsonValueProcessor;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String param;
	public String  base=Constants.BASE_URL;
	private JsonConfig jsonCongfig;

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}
   
	public BaseAction() {
		System.out.println("=============="+this.base);
	}

	/**
	 * 获取httpSession
	 * @return
	 */
	public HttpSession getSession(){
		return ServletActionContext.getRequest().getSession();
	}
	/**
	 * 获取http response
	 * 
	 * @return
	 */
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
	
	protected void writeJsonStatus(String status) {
		this.writeJson(JSONObject.fromObject(new JsonDto(status, null), this.getJsonCongfig(new String[]{"pageNo","total_page","total_num"})).toString());
	}
	/**
	 * 向前台输出字符串
	 * @param str
	 */
	public void writeJson(String str){
		HttpServletResponse response = getResponse();
		response.setContentType("text/javascript;charset=utf-8");
		try {
			response.getWriter().write(str);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String  ToJson(Object obj){
		return JSONObject.fromObject(obj).toString();
	}
	
	
	/**
	 * 获取json config
	 * 
	 * @param paramExcludes需要排除的实体属性
	 * @return
	 */
	public JsonConfig getJsonCongfig(String[] paramExcludes) {
		jsonCongfig = new JsonConfig();
		jsonCongfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonCongfig.registerJsonValueProcessor(java.util.Date.class,
				new DateJsonValueProcessor("yyyyMMdd HH:mm:ss"));
		jsonCongfig.registerJsonValueProcessor(Timestamp.class,
				new DateJsonValueProcessor("yyyyMMdd HH:mm:ss"));
		// if (paramExcludes != null && paramExcludes.length > 0) {
		paramExcludes = (String[]) arrayAddLength(paramExcludes, 2);
		paramExcludes[paramExcludes.length - 2] = "handler";
		paramExcludes[paramExcludes.length - 1] = "hibernateLazyInitializer";
		jsonCongfig.setExcludes(paramExcludes);
		// }
		return jsonCongfig;
	}
	/**
	 * 
	 * @author jean
	 * @param oldArray
	 * @param addLength
	 * @return
	 */
	private Object arrayAddLength(Object oldArray, int addLength) {
		Class<? extends Object> c = oldArray.getClass();
		if (!c.isArray())
			return null;
		Class<?> componentType = c.getComponentType();
		int length = Array.getLength(oldArray);
		int newLength = length + addLength;
		Object newArray = Array.newInstance(componentType, newLength);
		System.arraycopy(oldArray, 0, newArray, 0, length);
		return newArray;
	}
	
}

package com.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.alibaba.fastjson.JSONObject;
import com.dto.JsonDto;
import com.model.Person;
import com.service.LoginService;
import com.util.Constants;
import com.util.CreatUuid;
import com.util.TwoDimensionCode;

public class LoginAction  extends  BaseAction{
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    

	public LoginAction() {
		super();
	}

	@Autowired
	private LoginService loginService;
	
	public String index() {
		Map<String, Object> uuId=new HashMap<String, Object>();
		uuId.put("uuId", CreatUuid.genReqID());
		this.getSession().setAttribute("VIEW_PARAM", ToJson(uuId));
		return "success";
	}
	public void  login(){
		JSONObject jsonObject = JSONObject.parseObject(param);
		String passWord=jsonObject.getString("password");
		String userName=jsonObject.getString("username");
		Person user = loginService.getPersonInfo(userName, passWord);
		if (user!=null) {
			this.getSession().setAttribute(Constants.LOGIN_USER_SESSION_KEY, user);
			this.writeJson(ToJson(new JsonDto(Constants.Status.SUCCESS.getStatus(), user)));
		}else{
			this.writeJson(ToJson(new JsonDto(Constants.Status.FAILURE.getStatus(), user)));
		}
	}
	public String  angular(){
		return "success";
	}
    public void getPerson(){
    	List<Map<String, Object>> user=	loginService.getAllUser();
    	this.writeJson(ToJson(new JsonDto(Constants.Status.SUCCESS.getStatus(), user)));
    }
    
    public void  makeCode(){
    	JSONObject jsonObject = JSONObject.parseObject(param);
    	String uuId=jsonObject.getString("uuId");
		TwoDimensionCode.makeQRCode(uuId);
		Map<String, Object> url=new HashMap<String, Object>();
		url.put("url", uuId+".png");
		this.writeJson(ToJson(new JsonDto(Constants.Status.SUCCESS.getStatus(), url)));
    }
    /**
     * 二维码登录
     */
    public void qrcodeLogin(){
     JSONObject jsonObject = JSONObject.parseObject(param);
     String uuId=jsonObject.getString("uuId");
	     if (uuId!=null) {
			Constants.mp.put(uuId, "1");
		 } 
    }
    
    public void checkLogin(){
    	 JSONObject jsonObject = JSONObject.parseObject(param);
         String uuId=jsonObject.getString("uuId");
    	 Object obj= Constants.mp.get(uuId);
    	 if (obj!=null) {
    		 this.getSession().setAttribute(Constants.LOGIN_USER_SESSION_KEY, obj);
    		 this.writeJson(ToJson(new JsonDto(Constants.Status.SUCCESS.getStatus())));
    		 Constants.mp.remove(uuId);
    	 }
    	 else this.writeJson(ToJson(new JsonDto(Constants.Status.FAILURE.getStatus())));
    }

    
    public void saveUser(){
    	JSONObject jsonObject = JSONObject.parseObject(param);
    	String passWord=jsonObject.getString("passWord");
		String userName=jsonObject.getString("userName");
		boolean status=loginService.saveUser(userName, passWord);
		if (status) {
			this.writeJson(ToJson(new JsonDto(Constants.Status.SUCCESS.getStatus(),Constants.Status.SUCCESS.name())));
		}else{
			this.writeJson(ToJson(new JsonDto(Constants.Status.FAILURE.getStatus(),Constants.Status.ERR_USER_ISLIVE.name())));
		}
    }
    
    
    
    
}

package com.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Service;

import com.model.Person;
import com.util.MD5;

@SuppressWarnings("unchecked")
@Service
public class LoginService extends BaseService<Person>{
	
	public  Person getPersonInfo(String userName,String passWord){
		 HashMap<String, Object>  params=new HashMap<String, Object>();
		 params.put("passWord", MD5.encryption(passWord));
		 params.put("userName", userName);
		
		 String hqlCondition=" where entity.passWord=:passWord and entity.userName=:userName";
		
		 return   this.get(hqlCondition, params);
	}
	
	
	
	
	
    public List<Map<String,Object>> getAllUser(){
    	HashMap<String, Object>  params=new HashMap<String, Object>();
    	List<Map<String, Object>> list = baseDao.createSQLQuery("select p.id as id, p.name as userName, p.passWord as password from person p", params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    	return list;
    }

    
    /**
     * 注册方法
     * @param userName 用户名
     * @param password 密码
     * @return
     */
    public boolean saveUser(String userName,String passWord){
    	boolean status=false;
    	 String hqlCondition=" where entity.userName=:userName";
    	 Map<String , Object> params=new HashMap<String, Object>();
    	 params.put("userName", userName);
		 if (this.get(hqlCondition, params)==null) {
			 status=true;
			 Person person=new Person();
			 person.setPassWord(MD5.encryption(passWord));
			 person.setUserName(userName);
			 this.save(person);
		 }
    	return status;
    }
    
    public void hibernateTest(String userName,String passWord){
    	 HashMap<String, Object>  params=new HashMap<String, Object>();
		 params.put("passWord", MD5.encryption(passWord));
		 params.put("userName", userName);
    	 List<Map<String, Object>> list =null;
    	 String hql=" from Person entity where entity.passWord=:passWord and entity.userName=:userName";
		 //list=baseDao.createQuery(Person.class, hqlCondition, params).list();
		 list=baseDao.createQuery(hql, params).list();
		 //不会
		 //list=baseDao.createQuery(hql, o);
		 list= baseDao.createSQLQuery("select p.id as id, p.name as userName, p.passWord as password from person p where p.name=:userName and p.password=:passWord", params).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
		 /*List<Person> array=null; 
		              array = baseDao.find(Person.class, hql, params);
		              array = this.find(hql, params);
		              array = baseDao.find("select * from Person entity where entity.passWord=:passWord and entity.userName=:userName", params);
		 baseDao.count("");
		 baseDao.count(hql, params);*/
		 System.out.println(list);
    }    
    
    
	
}

package com.util;
import java.util.UUID;
/** 
* 利用UUID类生成随机ID
* 
* @author 袁培朋    2016-02-23  09:29
*/ 
public class CreatUuid  { 
        public static String genReqID() { 
                return UUID.randomUUID().toString().replace("-", "").toUpperCase();
               // return UUID.randomUUID().toString().toUpperCase(); 
        } 
}
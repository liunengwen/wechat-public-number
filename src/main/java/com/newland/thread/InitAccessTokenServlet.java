package com.newland.thread;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  

import com.newland.service.PropertyInfoService;
 
  
public class InitAccessTokenServlet extends HttpServlet {  
  
    public void init() throws ServletException {   
    	//获取公众号appid和secrect
        String WX_APPID = PropertyInfoService.getAppId();//公众号的唯一标识
		String WX_APPSECRET = PropertyInfoService.getSecret();
        AccessTokenThread.appid = WX_APPID;  
        AccessTokenThread.appsecret = WX_APPSECRET;  
  
        if ("".equals(AccessTokenThread.appid) || "".equals(AccessTokenThread.appsecret)) {  
            System.out.println("appid和appsecret未给出");  
        } else {  
            new Thread(new AccessTokenThread()).start();  
            new Thread(new JsApiTicketThread()).start();  
        }  
    }  
  
}  

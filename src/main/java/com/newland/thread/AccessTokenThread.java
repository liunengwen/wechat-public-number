package com.newland.thread;

import javax.servlet.ServletContext;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.model.weixin.pojo.AccessToken;
import com.newland.model.weixin.util.WeixinUtil;
  
public class AccessTokenThread implements Runnable { 
	
	private Logger log = LoggerFactory.getLogger(AccessTokenThread.class);

    public static String appid = "";  
    public static String appsecret = "";  
    public static AccessToken accessToken = null;  
  
    @Override  
    public void run() {  
        while (true) {  
            try {
            	log.info("========= AccessTokenThread 开始 ========");
            	AccessToken accessToken = WeixinUtil.getAccessToken(appid, appsecret);  
                if (null != accessToken) {
                	log.info("accessToken初始化成功:{}" + accessToken.getToken());  
                    // 全局缓存access_token  
                    ServletContext servletContext = ServletContextUtil.getServletContext();  
                    servletContext.setAttribute("access_token", accessToken.getToken());  
                    
                    // 有效期（秒）减去200秒，乘以1000(毫秒)——也就是在有效期的200秒前去请求新的accessToken  
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);
                    log.info("========= AccessTokenThread 结束 ========");
                } else {
                	log.info("=========accessToken为空，等一分钟，再次请求 ========");
                    // 等待一分钟，再次请求  
                    Thread.sleep(60 * 1000);  
                }  
            } catch (Exception e) {  
                try {
                	log.info("=========获取accessToken异常，等一分钟，再次请求 ========");
                    // 等待一分钟，再次请求  
                    Thread.sleep(60 * 1000);  
                } catch (Exception ex) {  
                    ex.printStackTrace();  
                }  
                e.printStackTrace();  
            }  
        }  
    }  
} 

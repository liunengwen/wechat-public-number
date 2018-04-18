package com.newland.thread;

import javax.servlet.ServletContext;  

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.newland.model.weixin.pojo.JsApiTicket;
import com.newland.model.weixin.util.WeixinUtil;

  
public class JsApiTicketThread implements Runnable {  
	
	private Logger log = LoggerFactory.getLogger(JsApiTicketThread.class);
  
    @Override  
    public void run() {  
        while (true) {  
            try {
            	log.info("========= JsApiTicketThread 开始 ========");
            	//从缓存中获取access_token
                ServletContext servletContext = ServletContextUtil.getServletContext();  
                String access_token = (String) servletContext.getAttribute("access_token"); 
                log.info("JsApiTicketThread 参数access_token:{}",access_token);  
                JsApiTicket jsApiTicket = null;  
                
                if(null != access_token && !"".equals(access_token)){  
                    // 获取jsapi_ticket  
                    jsApiTicket = WeixinUtil.getTicket(access_token);
                      
                    if (null != jsApiTicket) { 
                    	log.info("jsapi_ticket获取成功:" + jsApiTicket.getTicket());  
                        // 全局缓存jsapi_ticket  
                        servletContext.setAttribute("jsapi_ticket", jsApiTicket.getTicket());
                        // 有效期（秒）减去200秒，乘以1000(毫秒)——也就是在有效期的200秒前去请求新的accessToken  
                        Thread.sleep((jsApiTicket.getExpiresIn() - 200) * 1000); 
                        log.info("========= JsApiTicketThread 结束 ========");
                    }  
                }else{ 
                	log.info("=========JsApiTicketThread为空，等一分钟，再次请求 ========");
                    // 等待一分钟，再次请求  
                	Thread.sleep(60 * 1000);
                }
                  
            } catch (Exception e) {  
                try {
                	log.info("=========获取jsApiTicket异常，等一分钟，再次请求 ========");
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

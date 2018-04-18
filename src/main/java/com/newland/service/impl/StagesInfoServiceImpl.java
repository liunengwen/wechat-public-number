package com.newland.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.newland.model.weixin.pojo.OAuthInfo;
import com.newland.model.weixin.util.Constants;
import com.newland.model.weixin.util.WeixinUtil;
import com.newland.service.PropertyInfoService;
import com.newland.service.StagesInfoService;

@Service("stagesInfoService")
public class StagesInfoServiceImpl implements StagesInfoService{
	
	private Logger log = LoggerFactory.getLogger(StagesInfoServiceImpl.class);

	/**
	 * 分期-扫码获取用户openid
	 */
	@Override
	public void getOrderIdAndUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String code = request.getParameter("code");
		log.info("==============[getOrderIdAndUserOpenId],分期-扫码 获取网页授权code="+code);
		String orderId = request.getParameter("orderId");
		log.info("==============[getOrderIdAndUserOpenId],分期-扫码 获取订单orderId="+orderId);
		
		if(null != code && !"".equals(code)){
			if(null != orderId && !"".equals(orderId)){
				//根据code换取openId
				String appid = PropertyInfoService.getAppId();
				String secret = PropertyInfoService.getSecret();
				log.info("==============[getQrCodeUserOpenId],公众号 appid="+appid+",secret="+secret);
				OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
				if(!"".equals(oa) && null != oa){
					String orderIdUrl = PropertyInfoService.getStagesUserOpenIdUrl(oa.getOpenId(), orderId);
					 log.info("==============[getOrderIdAndUserOpenId],分期-扫码 跳转url="+orderIdUrl);
					 response.sendRedirect(orderIdUrl);
					 //测试环境
					 //response.sendRedirect("http://byStages-test.starpos.com.cn:2000/?openId="+oa.getOpenId()+"&orderId="+orderId);
					 //生产环境 https
					 //response.sendRedirect("https://bystages.starpos.com.cn:8443/?openId="+oa.getOpenId()+"&orderId="+orderId);
				}else{
					log.info("==============[getOrderIdAndUserOpenId],分期-扫码 获取网页授权openId失败！");
				}
			}else{
				log.info("==============[getOrderIdAndUserOpenId],分期-扫码 获取订单orderId失败！");
			}
	    }else{
		    log.info("==============[getOrderIdAndUserOpenId],分期-扫码 获取网页授权code失败！");
	    }
	}

	/**
	 * 分期-公众号点击按钮时，获取用户openid
	 */
	@Override
	public void getStagesUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String code = request.getParameter("code");
		log.info("==============[getStagesUserOpenId],分期-公众号 获取网页授权code="+code);
		
		if(null != code && !"".equals(code)){
			//根据code换取openId
			String appid = PropertyInfoService.getAppId();
			String secret = PropertyInfoService.getSecret();
			log.info("==============[getQrCodeUserOpenId],公众号 appid="+appid+",secret="+secret);
			OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
			if(!"".equals(oa) && null != oa){
				String stagesUrl = PropertyInfoService.getStagesUrl(oa.getOpenId());
				 log.info("==============[getStagesUserOpenId],分期-公众号 H5跳转URL="+stagesUrl);
				 response.sendRedirect(stagesUrl);
				 //测试环境
				 //response.sendRedirect("http://byStages-test.starpos.com.cn:2000/query/queryView/?openId="+oa.getOpenId());
				 //生产环境
				 //response.sendRedirect("https://bystages.starpos.com.cn:8443/query/queryView/?openId="+oa.getOpenId());
			}else{
				log.info("==============[getStagesUserOpenId],分期-公众号 获取网页授权openId失败！");
			}	
	    }else{
		    log.info("==============[getStagesUserOpenId],分期-公众号 获取网页授权code失败！");
	    }
	}

	
	
	
	

}

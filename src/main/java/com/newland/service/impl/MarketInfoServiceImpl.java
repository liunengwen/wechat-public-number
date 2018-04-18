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
import com.newland.service.MarketInfoService;
import com.newland.service.PropertyInfoService;

@Service("marketInfoService")
public class MarketInfoServiceImpl implements MarketInfoService{
	
	private Logger log = LoggerFactory.getLogger(MarketInfoServiceImpl.class);
	
	/**
	 * 游戏中心-公众号点击按钮时，获取用户openid
	 */
	@Override
	public void getMarketUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String code = request.getParameter("code");
		log.info("==============[getMarketUserOpenId],游戏中心-公众号 获取网页授权code="+code);
		
		if(null != code && !"".equals(code)){
			//根据code换取openId
			String appid = PropertyInfoService.getAppId();
			String secret = PropertyInfoService.getSecret();
			log.info("==============[getQrCodeUserOpenId],公众号 appid="+appid+",secret="+secret);
			OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
			if(!"".equals(oa) && null != oa){
				String marketUrl = PropertyInfoService.getMarketUrl(oa.getOpenId());
				log.info("==============[getMarketUserOpenId],游戏中心-公众号跳转url="+marketUrl);
				//测试环境
				response.sendRedirect(marketUrl);
				//测试环境
				//response.sendRedirect("https://starmwx-test.starpos.com.cn:7043/gameCenter/?openId="+oa.getOpenId());*/
				//生产环境
				//response.sendRedirect("https://website.starpos.com.cn/gameCenter/?openId="+oa.getOpenId());
			}else{
				log.info("==============[getMarketUserOpenId],游戏中心-公众号 获取网页授权openId失败！");
			}
		}else{
			log.info("==============[getMarketUserOpenId],游戏中心-公众号 获取网页授权code失败！");
		}
				
		
	}

}

package com.newland.service.impl;

import java.io.IOException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.newland.model.weixin.pojo.OAuthInfo;
import com.newland.model.weixin.util.SignUtil;
import com.newland.model.weixin.util.WeixinUtil;
import com.newland.service.MemberInfoService;
import com.newland.service.PropertyInfoService;
import com.newland.thread.ServletContextUtil;

@Service("memberInfoService")
public class MemberInfoServiceImpl implements MemberInfoService{
	
	private Logger log = LoggerFactory.getLogger(MemberInfoServiceImpl.class);

	/**
	 * 会员-扫码获取用户openid
	 */
	@Override
	public void getMemberInfoAndUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String code = request.getParameter("code");
		String scope = request.getParameter("scope");
		log.info("==============[getMemberInfoAndUserOpenId],会员-扫码 获取网页授权code="+code+"，网页跳转权限scope="+scope);
		//获取请求参数
		String sysCode = null;
		String payTotalAmt = null;
		String deviceType= null;
		String mercId= null;
		String mercName= null;
		String snNo= null;
		String paychannel= null;
		String txncnl= null;
		String qrCode= null;
		String v= null;
		
		String str = request.getParameter("str");
		log.info("==============[getMemberInfoAndUserOpenId],会员-扫码 获取请求参数str="+str);
		String bb = str.substring(str.indexOf("[")+1, str.lastIndexOf("]") );
		log.info("==============[getMemberInfoAndUserOpenId],会员-扫码 获取请求参数值bb="+bb);
		String[] aa =bb.split("\\/");
		log.info("==============[getMemberInfoAndUserOpenId],会员-扫码 请求参数 按/分割后数组长度，aa="+aa.length);
		//扫二维码调用
		if(aa.length<=3){
			for(int i =0 ;i<aa.length;i++){
				String cc[] = aa[i].split("\\=");
				if(cc[0].equals("qrCode")){
					qrCode = cc[1];
				}else if (cc[0].equals("sysCode")){
					sysCode = cc[1];
				}else if (cc[0].equals("v")){
					v = cc[1];
				}
			}
			log.info("==============[getMemberInfoAndUserOpenId],会员-app扫码,请求参数：qrCode="+qrCode+",sysCode="+sysCode+",v="+v);
		}else{
			//扫台牌调用
			for(int i =0 ;i<aa.length;i++){
				String cc[] = aa[i].split("\\=");
				if(cc[0].equals("payTotalAmt")){
					payTotalAmt = cc[1];
				}else if (cc[0].equals("deviceType")){
					deviceType = cc[1];
				}else if (cc[0].equals("mercId")){
					mercId = cc[1];
				}else if (cc[0].equals("mercName")){
					mercName = cc[1];
				}else if (cc[0].equals("snNo")){
					snNo = cc[1];
				}else if (cc[0].equals("paychannel")){
					paychannel = cc[1];
				}else if (cc[0].equals("txncnl")){
					txncnl = cc[1];
				}else if (cc[0].equals("sysCode")){
					sysCode = cc[1];
				}
			}
			log.info("==============[getMemberInfoAndUserOpenId],会员-扫台牌后，请求参数：payTotalAmt="+payTotalAmt+",deviceType="+deviceType+","
					+ "mercId="+mercId+",mercName="+mercName+",snNo="+snNo+",paychannel="+paychannel+",txncnl="+txncnl);
		}
		//获取openid
		if(null != code && !"".equals(code)){
			//根据code换取openId
			String appid = PropertyInfoService.getAppId();
			String secret = PropertyInfoService.getSecret();
			log.info("==============[getQrCodeUserOpenId],公众号 appid="+appid+",secret="+secret);
			OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
			if(!"".equals(oa) && null != oa){
				if("app".equals(sysCode)){
					String appUrl =null;
					if("caishen".equals(v) || "undefined".equals(v)){
						appUrl = PropertyInfoService.getMemberAppUrlByCaiShen(code, oa.getOpenId(), qrCode,v);
					}else{
						appUrl = PropertyInfoService.getMemberAppUrl(code, oa.getOpenId(), qrCode,v);
					}
					log.info("==============[getMemberInfoAndUserOpenId]，会员-扫二维码，跳转h5地址="+appUrl);
					response.sendRedirect(appUrl);					
				}else{
					String posbuiUrl = PropertyInfoService.getMemberPosbuiUrl(code, oa.getOpenId(), payTotalAmt, deviceType, mercId, mercName, snNo, paychannel, txncnl);
					log.info("==============[getMemberInfoAndUserOpenId]，会员-扫台牌，跳转h5地址="+posbuiUrl);
					response.sendRedirect(posbuiUrl);
				}
				 
			}else{
				log.info("==============[getMemberInfoAndUserOpenId]获取网页授权openId失败！");
			}
		}else{
			log.info("==============[getMemberInfoAndUserOpenId]获取网页授权code失败！");
		}
		
	}
	
	/**
	 * 会员-公众号点击按钮时，获取用户openid
	 */
	@Override
	public void getMemberUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String code = request.getParameter("code");
		log.info("==============[getMemberUserOpenId],会员-公众号 获取网页授权code="+code);
		if(null != code && !"".equals(code)){
			//根据code换取openId
			String appid = PropertyInfoService.getAppId();
			String secret = PropertyInfoService.getSecret();
			log.info("==============[getQrCodeUserOpenId],公众号 appid="+appid+",secret="+secret);
			OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
			if(!"".equals(oa) && null != oa){
				 String memberUrl = PropertyInfoService.getMemberUrl(oa.getOpenId());
				 log.info("==============[getMemberUserOpenId],会员-公众号，H5跳转Url="+memberUrl);
				 response.sendRedirect(memberUrl);
				 //测试环境
				 /*response.sendRedirect("https://bystages-test.starpos.com.cn:9443/cardList?openId="+oa.getOpenId());*/
				 //生产环境
				 //log.info("==============[getMemberUserOpenId],会员-公众号 获取网页授权openID="+oa.getOpenId());
				 //response.sendRedirect("https://website.starpos.com.cn/member/cardList?openId="+oa.getOpenId());
			}else{
				log.info("==============[getMemberUserOpenId],会员-公众号 获取网页授权openId失败！");
			}	
	    }else{
		    log.info("==============[getMemberUserOpenId],会员-公众号 获取网页授权code失败！");
	    }
	}

	/**
	 * 会员-消费者收到券信息时，可以选择分享给他人
	 */
	@Override
	public Map<String, Object> sendMemberShareCouponInfo(HttpServletRequest request, JSONObject json) throws IOException {
		Map<String, Object> ret = new HashMap<String, Object>();
		
		 // 从全局缓存中取出jsapi_ticket  
        ServletContext servletContext = ServletContextUtil.getServletContext();  
        String jsapi_ticket = (String) servletContext.getAttribute("jsapi_ticket");
        log.info("=== sendMemberShareCouponInfo jsapi_ticket：{}=========",jsapi_ticket);
        //获取公众号appid和secrect
        String appId = PropertyInfoService.getAppId();//公众号的唯一标识 		
 	    // 必填，生成签名的时间戳
        String timestamp = Long.toString(System.currentTimeMillis() / 1000); 
        // 必填，生成签名的随机串
        String nonceStr = UUID.randomUUID().toString();
        
        //获取前端传参url
        //encodeURIComponent编码转换 
        String requestUrl = URLDecoder.decode(json.getString("url"), "UTF-8");
        log.info("=== sendMemberShareCouponInfo 获取前端传参url：{}=========",requestUrl);
 		
        // 注意这里参数名必须全部小写，且必须有序
        String signature = null;
        if(jsapi_ticket != null ){
        	String sign = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + nonceStr+ "&timestamp=" + timestamp + "&url=" + requestUrl;
            signature = SignUtil.getSignature(sign);
        }
        
        ret.put("appId", appId);
        ret.put("timestamp", timestamp);
        ret.put("nonceStr", nonceStr);
        ret.put("signature", signature);
        log.info("==== sendMemberShareCouponInfo 微信config配置信息，ret：{}=========",ret.toString());
        return ret;
	}
	
}

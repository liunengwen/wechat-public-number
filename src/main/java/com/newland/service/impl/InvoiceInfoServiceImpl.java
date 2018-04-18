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
import com.newland.service.InvoiceInfoService;
import com.newland.service.PropertyInfoService;

@Service("invoiceInfoService")
public class InvoiceInfoServiceImpl implements InvoiceInfoService{
	
	private Logger log = LoggerFactory.getLogger(InvoiceInfoServiceImpl.class);

	/**
	 * 电子发票-扫码获取用户openid
	 */
	@Override
	public void getQrCodeUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String qrcode = request.getParameter("qrcode");
		log.info("==============[getQrCodeUserOpenId],电子发票-扫码 获取qrcode="+qrcode);
		String code = request.getParameter("code");
		log.info("==============[getQrCodeUserOpenId],电子发票-扫码 获取网页授权code="+code);
		if(null != code && !"".equals(code)){
			if(null != qrcode && !"".equals(qrcode)){ 
				//根据code换取openId
				String appid = PropertyInfoService.getAppId();
				String secret = PropertyInfoService.getSecret();
				log.info("==============[getQrCodeUserOpenId],公众号appid="+appid+",secret="+secret);
				OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
				if(!"".equals(oa) && null != oa){
					 String qrCodeUrl = PropertyInfoService.getInvoiceQrCodeUrl(oa.getOpenId(), qrcode);
					 log.info("==============[getQrCodeUserOpenId],电子发票-扫码后跳转URL="+qrCodeUrl);
					 //测试环境
					 response.sendRedirect(qrCodeUrl);
					 //response.sendRedirect("http://invoice-test.starpos.com.cn/?openId="+oa.getOpenId()+"&qrcode="+qrcode);
					 //生产环境 https
					 //response.sendRedirect("https://invoice.starpos.com.cn:8081/?openId="+oa.getOpenId()+"&qrcode="+qrcode);
				}else{
					log.info("==============[getQrCodeUserOpenId],电子发票-扫码 获取网页授权openId失败！");
				}
			}
	    }else{
		    log.info("==============[getQrCodeUserOpenId],电子发票-扫码 获取网页授权code失败！");
	    }
	}

	/**
	 * 电子发票-公众号点击按钮时，获取用户openid
	 */
	@Override
	public void getInvoiceUserOpenId(HttpServletRequest request,HttpServletResponse response) throws IOException {
		//接受参数
		String code = request.getParameter("code");
		log.info("==============[getInvoiceUserOpenId],电子发票-公众号 获取网页授权code="+code);
		String scope = request.getParameter("scope");
		log.info("==============[getInvoiceUserOpenId],电子发票-公众号 获取网页跳转权限="+scope);
		if(null != code && !"".equals(code)){
			log.info("==============[getInvoiceUserOpenId]获取网页授权code不为空，code="+code);
			//根据code换取openId
			String appid = PropertyInfoService.getAppId();
			String secret = PropertyInfoService.getSecret();
			log.info("==============[getQrCodeUserOpenId],公众号 appid="+appid+",secret="+secret);
			OAuthInfo oa = WeixinUtil.getOAuthOpenId(appid,secret,code);
			if(!"".equals(oa) && null != oa){
				String invoiceUrl = PropertyInfoService.getInvoiceUrl(oa.getOpenId());
				log.info("==============[getInvoiceUserOpenId],电子发票-公众号跳转url="+invoiceUrl);
				//测试环境
				response.sendRedirect(invoiceUrl);
				 //response.sendRedirect("http://invoice-test.starpos.com.cn/gzh/queryInvoice?openId="+oa.getOpenId());
				//生产环境 https
				//response.sendRedirect("https://invoice.starpos.com.cn:8081/gzh/queryInvoice?openId="+oa.getOpenId());
			}else{
				log.info("==============[getInvoiceUserOpenId],电子发票-公众号 获取网页授权openId失败！");
			}
		}else{
			log.info("==============[getInvoiceUserOpenId],电子发票-公众号 获取网页授权code失败！");
		}
				
		
	}

}

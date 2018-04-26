package com.newland.service;

import com.newland.common.PropertyPlaceholder;

/**
 * 获取properties中的各项目地址信息
 * @author tl
 *
 */
public class PropertyInfoService {

	//电子发票-扫码后重定向地址
	public static String getInvoiceQrCodeUrl(String openId, String qrCode) {
		return ((String) PropertyPlaceholder.getProperty("invoice_qrCode_url")).replace("OPENID", openId).replace("QRCODE", qrCode);
	}
	
	//电子发票-公众号点击按钮后重定向url
	public static String getInvoiceUrl(String openId) {
		return ((String) PropertyPlaceholder.getProperty("invoice_url")).replace("OPENID", openId);
	}
	
	//分期-扫码后重定向地址
	public static String getStagesUserOpenIdUrl(String openId, String orderId) {
		return ((String) PropertyPlaceholder.getProperty("stages_orderId_url")).replace("OPENID", openId).replace("ORDERID", orderId);
	}
	
	//分期-公众号点击按钮后重定向url
	public static String getStagesUrl(String openId) {
		return ((String) PropertyPlaceholder.getProperty("stages_url")).replace("OPENID", openId);
	}
	
	//会员-扫码后重定向地址
	public static String getMemberAppUrl(String code, String openId, String qrCode,String type) {
		return ((String) PropertyPlaceholder.getProperty("member_app_url")).replace("CODE", code).replace("OPENID", openId).replace("QRNO", qrCode).replace("TYPE", type);
	}
	
	//会员-扫码后重定向地址(收银台)
	public static String getMemberAppUrlByCaiShen(String code, String openId, String qrCode,String type) {
		return ((String) PropertyPlaceholder.getProperty("member_caishen_url")).replace("CODE", code).replace("OPENID", openId).replace("QRNO", qrCode).replace("TYPE", type);
	}
	
	//会员-扫台牌输入金额后，重定向地址
	public static String getMemberPosbuiUrl(String code, String openId, String payTotalAmt, 
			String deviceType, String mercId, String mercName, String snNo, String paychannel, String txncnl) {
		String url = ((String) PropertyPlaceholder.getProperty("member_pusbui_url")).replace("CODE", code).replace("OPENID", openId).replace("PAYTOTALAMT", payTotalAmt)
				.replace("DEVICETYPE", deviceType).replace("MERCID", mercId).replace("MERCNAME", mercName).replace("SNNO", snNo)
				.replace("PAYCHANNEL", paychannel).replace("TXNCNL", txncnl);
		return url;
	}
		
	//会员-公众号点击按钮后重定向url
	public static String getMemberUrl(String openId) {
		return ((String) PropertyPlaceholder.getProperty("member_url")).replace("OPENID", openId);
	}
	
	//会员-公众号点击按钮后重定向url
	public static String getMarketUrl(String openId) {
		return ((String) PropertyPlaceholder.getProperty("market_url")).replace("OPENID", openId);
	}
	
	//获取微信公众号appid
	public static String getAppId() {
		return (String) PropertyPlaceholder.getProperty("weixin_appId");
	}
	
	//获取微信公众号secret
	public static String getSecret() {
		return (String) PropertyPlaceholder.getProperty("weixin_appSecret");
	}
	
}

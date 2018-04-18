package com.newland.util;

import com.newland.common.PropertyPlaceholder;



/**
 * Description: 配置项工具类
 * 获取properties中的公共地址信息
 * @author tl
 * 
 */
public class PropertyUtils {
	
	/**
	 * 网页授权获取用户信息
	 * @param access_token 授权得到的access_token
	 * @param openid  授权获取的openid
	 * @return
	 */
	public static String getOpenIdUrl(String appid, String secret, String code) {
		return ((String) PropertyPlaceholder.getProperty("o_auth_openid_url")).replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
	}
	
	/**
	 * 获取access_token
	 * @param appid  账号
	 * @param appsecret  密钥
	 * @return
	 */
	public static String getAccessTokenUrl(String appid, String secret) {
		return ((String) PropertyPlaceholder.getProperty("access_token_url")).replace("APPID", appid).replace("APPSECRET", secret);
	}
	
	/**
	 * 创建菜单
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static String createMenuUrl(String accessToken) {
		return ((String) PropertyPlaceholder.getProperty("menu_create_url")).replace("ACCESS_TOKEN", accessToken);
	}
	
	/**
	 * 删除菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static String deleteMenuUrl(String accessToken) {
		return ((String) PropertyPlaceholder.getProperty("menu_delete_url")).replace("ACCESS_TOKEN", accessToken);
	}
	
	/**
	 * 网页授权获取用户信息
	 * @param access_token 授权得到的access_token
	 * @param openid  授权获取的openid
	 * @return
	 */
	public static String getUserInfoUrl(String access_token,String openid) {
		return ((String) PropertyPlaceholder.getProperty("userinfo_url")).replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
	}
	
	/**
	 * 微信JSSDK的ticket请求URL地址——jsapi_ticket
	 * @param access_token 凭证获取 access_token
	 * @return
	 */
	public static String getJSApiTicket(String access_token) {
		return ((String) PropertyPlaceholder.getProperty("jsapi_ticket_url")).replace("ACCESS_TOKEN", access_token);
	}
}

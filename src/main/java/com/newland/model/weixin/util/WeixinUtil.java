package com.newland.model.weixin.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;






import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.newland.model.weixin.pojo.AccessToken;
import com.newland.model.weixin.pojo.JsApiTicket;
import com.newland.model.weixin.pojo.Menu;
import com.newland.model.weixin.pojo.OAuthInfo;
import com.newland.model.weixin.pojo.UserInfo;
import com.newland.util.PropertyUtils;



/**
 * 公众平台通用接口工具类
 * 
 */
public class WeixinUtil {
	private static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl
	 *            请求地址
	 * @param requestMethod
	 *            请求方式（GET、POST）
	 * @param outputStr
	 *            提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		log.info("==============进入httpRequest方法===============");
		
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			URL url = new URL(null,requestUrl,new sun.net.www.protocol.https.Handler()); 
			log.info("==============发送的url="+url+"===============");
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			log.info("==============httpUrlConn="+httpUrlConn+"===============");
			
			httpUrlConn.setSSLSocketFactory(ssf);
			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}
		
			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			log.info("==============将返回的输入流转换成字符串，buffer.toString()="+buffer.toString()+"===============");
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.parseObject(buffer.toString());
			log.info("======返回结果，jsonObject="+jsonObject+"========");
		} catch (ConnectException ce) {
			log.error("======Weixin server connection timed out ======");
		} catch (Exception e) {
			log.error("======https request error:{}======", e);
		}
		log.info("==============httpRequest方法结束===============");
		return jsonObject;
	}
	
	public static boolean httpRequestNoResult(String requestUrl, String requestMethod, String outputStr) {
		boolean result = false;
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(null, tm, new java.security.SecureRandom());
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod)){
				httpUrlConn.connect();
			}

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			if(httpUrlConn.getResponseCode()==200){
				result = true;
			}
			
			httpUrlConn.disconnect();
		} catch (ConnectException ce) {
			//log.error("Weixin server connection timed out.");
		} catch (Exception e) {
			//log.error("https request error:{}", e);
		}
		return result;
	}

	/**
	 * 获取access_token
	 * 
	 * @param appid
	 *            凭证
	 * @param appsecret
	 *            密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;
		String requestUrl =PropertyUtils.getAccessTokenUrl(appid, appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (Exception e) {
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject
						.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	
	/**
	 * 创建菜单
	 * 
	 * @param menu
	 *            菜单实例
	 * @param accessToken
	 *            有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url =PropertyUtils.createMenuUrl(accessToken);
		//String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = null;//JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
			}
		}

		return result;
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
	public static int deleteMenu(Menu menu, String accessToken) {
		int result = 0;
		// 拼装创建菜单的url
		String url =PropertyUtils.deleteMenuUrl(accessToken);
		//String url = menu_delete_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = null;//JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getIntValue("errcode")) {
				result = jsonObject.getIntValue("errcode");
			}
		}

		return result;
	}
	
	/**
	 * 网页授权获取openId第2步，根据code取得openId
	 * @param appid 公众号的唯一标识
	 * @param secret 公众号的appsecret密钥
	 * @param code code为换取access_token的票据          
	 * @return 
	 */
	public static OAuthInfo getOAuthOpenId(String appid, String secret, String code ) {
		OAuthInfo oAuthInfo = null;
		//String requestUrl = o_auth_openid_url.replace("APPID", appid).replace("SECRET", secret).replace("CODE", code);
		String requestUrl = PropertyUtils.getOpenIdUrl(appid, secret, code);
		log.info("=====getOAuthOpenId requestUrl:"+requestUrl+"==============");

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		log.info("=====getOAuthOpenId jsonObject:"+jsonObject+"==============");
		
		// 如果请求成功
		if (null != jsonObject) {
			try {
				oAuthInfo = new OAuthInfo();
				oAuthInfo.setAccessToken(jsonObject.getString("access_token"));
				oAuthInfo.setExpiresIn(jsonObject.getIntValue("expires_in"));
				oAuthInfo.setRefreshToken(jsonObject.getString("refresh_token"));
				oAuthInfo.setOpenId(jsonObject.getString("openid"));
				oAuthInfo.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				oAuthInfo = null;
				// 获取token失败
				log.error("网页授权获取openId失败 errcode:{} errmsg:{}", jsonObject
						.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return oAuthInfo;
	}
	/**
	 * 网页授权获取用户信息
	 * @param access_token 授权得到的access_token
	 * @param openid  授权获取的openid
	 * @return
	 */
	public static UserInfo getUserInfo(String access_token,String openid ) {
		UserInfo userInfo = null;
		String requestUrl =PropertyUtils.getUserInfoUrl(access_token, openid);
		//String requestUrl = userinfo_url.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
		log.info("==============requestUrl:"+requestUrl+"==============");

		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		log.info("==============jsonObject:"+jsonObject+"==============");
		
		// 如果请求成功
		if (null != jsonObject) {
			try {
				userInfo = new UserInfo();
				userInfo.setNickname(jsonObject.getString("nickname"));
//				oAuthInfo.setAccessToken(jsonObject.getString("access_token"));
//				oAuthInfo.setExpiresIn(jsonObject.getIntValue("expires_in"));
//				oAuthInfo.setRefreshToken(jsonObject.getString("refresh_token"));
//				oAuthInfo.setOpenId(jsonObject.getString("openid"));
//				oAuthInfo.setScope(jsonObject.getString("scope"));
			} catch (Exception e) {
				userInfo = null;
				// 获取token失败
				log.error("网页授权获取openId失败 errcode:{} errmsg:{}", jsonObject
						.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return userInfo;
	}
	
	/**
	 * 获取jsapi_ticket
	 * 
	 * @param accessToken
	 *            access_Token
	 * @return
	 */
	public static JsApiTicket getTicket(String accessToken) {
		JsApiTicket jsApiTicket = new JsApiTicket();
		String requestUrl =PropertyUtils.getJSApiTicket(accessToken);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				jsApiTicket.setTicket(jsonObject.getString("ticket"));
				jsApiTicket.setExpiresIn(jsonObject.getIntValue("expires_in"));
			} catch (Exception e) {
				// 获取token失败
				log.error("获取ticket失败 errcode:{} errmsg:{}", jsonObject
						.getIntValue("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return jsApiTicket;
	}
}
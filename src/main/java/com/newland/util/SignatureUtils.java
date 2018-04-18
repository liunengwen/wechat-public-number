package com.newland.util;

import com.newland.common.Constants;
/**
 * 签名工具类
 * @author fangxu.ge
 *
 */
public class SignatureUtils {

	public static String sign(String content, String key, String signType) throws Exception{
		String signature = null;
		if (Constants.SIGN_TYPE_MD5.equals(signType)){
			content += key;
			signature = MD5.MD5Encode(content).toUpperCase();
		}
		return signature;
	}
}

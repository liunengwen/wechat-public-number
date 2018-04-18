package com.newland.model.weixin.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;



public class SignUtil {
	
	
	/**
	 * 字符串加密辅助方法 
	 * 
	 */
	public static String getSignature(String sign){
		String signature ="";
		try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(sign.getBytes("UTF-8"));
            signature = SignUtil.byteToHex(crypt.digest());
        } catch (NoSuchAlgorithmException e) {
        	
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
		return signature;
	}
	
	/**
	 * 
	 * 方法名：byteToHex</br>
	 * 详述：字符串加密辅助方法 </br>
	 * @param hash
	 * @return 说明返回值含义
	 * @throws 说明发生此异常的条件
	 */
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;

    }
	    
	    
}

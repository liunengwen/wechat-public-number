package com.newland.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

/**
 * User: rizenguo
 * Date: 2014/10/23
 * Time: 15:43
 */
public class MD5 {
    private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7",
            "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 转换字节数组为16进制字串
     * @param b 字节数组
     * @return 16进制字串
     */
    public static String byteArrayToHexString(byte[] b) {
        StringBuilder resultSb = new StringBuilder();
        for (byte aB : b) {
            resultSb.append(byteToHexString(aB));
        }
        return resultSb.toString();
    }

    /**
     * 转换byte到16进制
     * @param b 要转换的byte
     * @return 16进制格式
     */
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n = 256 + n;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * MD5编码
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            resultString = origin;
            MessageDigest md = MessageDigest.getInstance("MD5");
            resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }
    
    /*** 
     * MD5加码 生成32位md5码 
     */  
     public static String string2MD5(String inStr){  
     MessageDigest md5 = null;  
     try{  
       md5 = MessageDigest.getInstance("MD5");  
     }catch (Exception e){  
       e.printStackTrace();  
       return "";  
     }  
     char[] charArray = inStr.toCharArray();  
     byte[] byteArray = new byte[charArray.length];  
       
       
     for (int i = 0; i < charArray.length; i++)  
       byteArray[i] = (byte) charArray[i];  
       byte[] md5Bytes = md5.digest(byteArray);  
       StringBuffer hexValue = new StringBuffer();  
       for (int i = 0; i < md5Bytes.length; i++){  
         int val = ((int) md5Bytes[i]) & 0xff;  
         if (val < 16)  
           hexValue.append("0");  
           hexValue.append(Integer.toHexString(val));  
       }  
     return hexValue.toString(); 
     }  
       
       
     /** 
     * 加密解密算法 执行一次加密，两次解密 
     */   
     public static String convertMD5(String inStr){
     char[] a = inStr.toCharArray();  
     for (int i = 0; i < a.length; i++){  
     a[i] = (char) (a[i] ^ 't');  
     }  
     String s = new String(a);  
     return s;  
     }

     
     
     /**
 	 * MD5值计算<p>
 	 * MD5的算法在RFC1321 中定义:
 	 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
 	 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
 	 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
 	 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
 	 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
 	 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
 	 *
 	 * @param res 源字符串
 	 * @return md5值
 	 */
 	public final static String md5Digest(String res) {
 		if(res ==null||"".equals(res)){
 			return null;
 		}
 		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
 		byte[] strTemp = null;
 		try {
 			strTemp = res.getBytes("utf-8");
 		} catch (UnsupportedEncodingException e1) {
 			// TODO Auto-generated catch block
 			e1.printStackTrace();
 		}
 		
 		//byte[] strTemp = res.getBytes();
 		try {
 			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
 			mdTemp.update(strTemp);
 			byte[] md = mdTemp.digest();
 			int j = md.length;
 			char str[] = new char[j * 2];
 			int k = 0;
 			for (int i = 0; i < j; i++) {
 				byte byte0 = md[i];
 				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
 				str[k++] = hexDigits[byte0 & 0xf];
 			}
 			String dd = new String(str);
 			return dd;
 		} catch (Exception e) {
 			return null;
 		}
 	}

 	/**
 	 * MD5值计算+Base64<p>
 	 * MD5的算法在RFC1321 中定义:
 	 * 在RFC 1321中
 	 * 
 	 * @param res 源字符串
 	 * @return md5值
 	 */
 	public final static byte[] md5SrcDigest(String res) {
 		if(res == null || "".equals(res)){
 			return null;
 		}
 		byte[] strTemp = res.getBytes();
 		try {
 			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
 			mdTemp.update(strTemp);
 			byte[] md = mdTemp.digest();
 			return md;
 		} catch (Exception e) {
 			return null;
 		}
 	}
 	 /*public static void main(String[] args) {
	String url = "signatureUrl = http://openapi.1card1.cn/OpenApi/Add_Member?openId=B4410473271D49BE8D95FF5030629AD0&signature=";
	Map<String, Object> data = new HashMap<>();
	data.put("password", "4446064");
	data.put("memberGroupName", "银卡");
	data.put("userAccount", "10000");
	data.put("cardId", "10001");
	long timeStamp = System.currentTimeMillis()/1000;
	String signature = getSignature("B4410473271D49BE8D95FF5030629AD0", "CU2KJ1", data, timeStamp);
	String urls = url+signature+"&timestamp="+timeStamp;
	System.out.println(urls);
}*/

}

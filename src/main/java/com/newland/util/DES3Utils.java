package com.newland.util;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;

import org.apache.commons.codec.binary.Base64;

public class DES3Utils {
	
	// 密钥 长度不得小于24
	private final static String secretKey = "201707194267420170719426";
	// 向量 可有可无 终端后台也要约定
	private final static String iv = "01234567";
	// 加解密统一使用的编码方式
	private final static String encoding = "UTF-8";
 
   /**
   * 3DES加密
   *
   * @param plainText
   *            普通文本
   * @return
   * @throws Exception
   */
   public static String encrypt(String plainText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        Key deskey = keyfactory.generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, deskey, ips);
        byte[] encryptData = cipher.doFinal(plainText.getBytes(encoding));
        
        return Base64.encodeBase64String(encryptData);
  }
 
   /**
   * 3DES解密
   *
   * @param encryptText
   *            加密文本
   * @return
   * @throws Exception
   */
   public static String decrypt(String encryptText) throws Exception {
        DESedeKeySpec spec = new DESedeKeySpec(secretKey.getBytes());
        SecretKeyFactory keyfactory = SecretKeyFactory.getInstance("DESede");
        Key deskey = keyfactory. generateSecret(spec);
        Cipher cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding" );
        IvParameterSpec ips = new IvParameterSpec(iv.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, deskey,ips);
        byte[] decryptData = cipher.doFinal(Base64.decodeBase64(encryptText));
 
        return new String(decryptData, encoding);
    }
      
}

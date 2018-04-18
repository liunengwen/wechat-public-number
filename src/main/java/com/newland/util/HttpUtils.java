package com.newland.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Description: 附件工具类
 * @author fangxu.ge
 * @date 2017年8月10日 下午7:51:11
 */
public class HttpUtils {
	
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * 从url下载文件到目录
	 * @param url
	 * @return
	 */
	public static String downloadFromUrl(String fileUrl,String fileName,String savePath) {
		//文件保存位置，按日期分开，如果目录不存在则创建
		File saveDir = new File(savePath+File.separator+DateUtils.getToday());
		if(!saveDir.exists()){
			saveDir.mkdir();
		}
		File file = new File(saveDir+File.separator+fileName);
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			URL url = new URL(fileUrl);
	        HttpURLConnection urlCon = (HttpURLConnection) url.openConnection();
	        urlCon.setConnectTimeout(60000);
	        urlCon.setReadTimeout(60000);
	        int code = urlCon.getResponseCode();
	        if (code != HttpURLConnection.HTTP_OK) {
	        	log.error("=== downloadFromUrl error code ：{}  ===",code);
	            return null;
	        }
	        //读文件流
	        in = new DataInputStream(urlCon.getInputStream());
	        out = new DataOutputStream(new FileOutputStream(file));
	        byte[] buffer = new byte[1024];
	        int count = 0;
	        while ((count = in.read(buffer)) > 0) {
	            out.write(buffer, 0, count);
	        }
	        
		} catch (Exception e) {
			log.error("=== downloadFromUrl error ：{}  ===",e.getMessage(),e);
			return null;
		} finally {
			try {
				if(out != null){
					out.close();
		        }
		        if(in != null){
		        	in.close();
		        }
			} catch (IOException e) {
				log.error("=== downloadFromUrl close stream error ：{}  ===",e.getMessage(),e);
			}  
		}
        
        return file.getPath();

    }

}

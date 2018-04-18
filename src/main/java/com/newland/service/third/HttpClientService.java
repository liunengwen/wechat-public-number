package com.newland.service.third;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;

import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newland.model.response.HttpResult;
import com.newland.util.DateUtils;


@Service
public class HttpClientService implements BeanFactoryAware {

	private Logger log = LoggerFactory.getLogger(HttpClientService.class);
	
	@Autowired
	private BeanFactory beanFactory;

	@Autowired
	private RequestConfig config;

	/**
	 * 无参GET方法 author zhangp
	 * @param url
	 * @return
	 * @throws Exception 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) 
			throws Exception {
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(config);
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient(url).execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		}finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	/**
	 * 有参GET方法 author zhangp
	 * @param url
	 * @param params headers
	 * @return
	 * @throws Exception 
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGetAddHeader(String url, Map<String, String> params) 
			throws Exception {
		HttpGet httpGet = new HttpGet(url);
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				httpGet.addHeader(entry.getKey(), entry.getValue());
			}
		}
		httpGet.setConfig(config);
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient(url).execute(httpGet);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		}finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	/**
	 * 有参GET方法 author zhangp
	 * @param url
	 * @param params
	 * @return
	 * @throws Exception 
	 */
	public String doGet(String url, Map<String, String> params)
			throws Exception {
		URIBuilder uri = new URIBuilder(url);
		if (params != null) {
			for (Entry<String, String> entry : params.entrySet()) {
				uri.setParameter(entry.getKey(), entry.getValue());
			}
		}
		return this.doGet(uri.build().toString());
	}

	/**
	 * 有参数POST方法 author zhangp
	 * @param url
	 * @param params
	 * @return
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> params)
			throws Exception {
		String timestamp = DateUtils.getStringDateYMDHMSS();
		log.info("====== doPost start timestamp : {} ,url : {} ,param : {} ======",timestamp,url,params);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(config);
		httpPost.addHeader("Content-Type",
				"application/x-www-form-urlencoded; charset=UTF-8");
		if (params != null) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>();
			for (Entry<String, String> entry : params.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
			CloseableHttpResponse response = null;
			try {
				response = getHttpClient(url).execute(httpPost);
				String data = null;
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					data = EntityUtils.toString(response.getEntity(), "UTF-8");
				}
				int statusCode = response.getStatusLine().getStatusCode();
				log.info("====== doPost end timestamp : {} ,resultCode : {} ,resultData : {} ======",timestamp,statusCode,data);
				return new HttpResult(statusCode,data);
			} finally {
				if (response != null) {
					response.close();
				}
			}
		}
		return null;
	}

	/**
	 * 有参数POST方法 author zhangp
	 * @param url
	 * @param json
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public HttpResult doPost(String url, String json)
			throws Exception {
		String timestamp = DateUtils.getStringDateYMDHMSS();
		log.info("====== doPost start timestamp : {} ,url : {} ,jsonParam : {} ======",timestamp,url,json);
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(config);
		if (StringUtils.isNoneEmpty(json)) {
			StringEntity stringEntity = new StringEntity(json,
					ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
		}
		CloseableHttpResponse response = null;
		try {
			response = getHttpClient(url).execute(httpPost);
			String data = null;
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				data = EntityUtils.toString(response.getEntity(), "UTF-8");
			}
			int statusCode = response.getStatusLine().getStatusCode();
			log.info("====== doPost end timestamp : {} ,resultCode : {} ,resultData : {} ======",timestamp,statusCode,data);
			return new HttpResult(statusCode,data);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}

	/**
	 * 配置多例
	 * @throws Exception 
	 * */
	public CloseableHttpClient getHttpClient(String url) throws Exception {
		if(url.startsWith("https")){ //SSL
			return buildSSLCloseableHttpClient();
		}else{
			return this.beanFactory.getBean(CloseableHttpClient.class);
		}
	}

	public void setBeanFactory(BeanFactory paramBeanFactory) throws BeansException {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * https信任所有证书
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("deprecation")
	private CloseableHttpClient buildSSLCloseableHttpClient() throws Exception {
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
                new TrustStrategy() {
                    // 信任所有
                    public boolean isTrusted(X509Certificate[] chain,
                            String authType) throws CertificateException {
                        return true;
                    }
                }).build();
        // ALLOW_ALL_HOSTNAME_VERIFIER:这个主机名验证器基本上是关闭主机名验证的,实现的是一个空操作，并且不会抛出javax.net.ssl.SSLException异常。  
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(  
                sslContext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        return HttpClients.custom().setSSLSocketFactory(sslsf).build();  
    }

}

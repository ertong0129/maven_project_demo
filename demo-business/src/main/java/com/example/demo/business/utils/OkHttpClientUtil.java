package com.example.demo.business.utils;

import okhttp3.Dispatcher;
import okhttp3.OkHttpClient;

import javax.net.ssl.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

public class OkHttpClientUtil {
	
	/**
	 * 获取免ssl验证的OkHttpClient
	 * @return
	 */
	public static OkHttpClient getUnsafeOkHttpClient() {
		return getUnsafeOkHttpClient(null);
	}
	
	/**
	 * 获取免ssl验证的OkHttpClient
	 * @param dispatcher
	 * @return
	 */
	public static OkHttpClient getUnsafeOkHttpClient(Dispatcher dispatcher) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		
		X509TrustManager trustManager = new TrustAllManager();
		HostnameVerifier hostnameVerifier = new TrustAllHostnameVerifier();
		
		builder.sslSocketFactory(createSSLSocketFactory(trustManager), trustManager);
		builder.hostnameVerifier(hostnameVerifier);
		if (dispatcher != null) {
			builder.dispatcher(dispatcher);
		}
		builder.connectTimeout(4, TimeUnit.SECONDS);
		builder.readTimeout(4, TimeUnit.SECONDS);
		builder.writeTimeout(4, TimeUnit.SECONDS);
		return builder.build();
	}
	
	/**
	 * 自定义超时时间
	 * @param dispatcher
	 * @return
	 */
	public static OkHttpClient getUnsafeOkHttpClient(Dispatcher dispatcher, Integer connectTimeout, Integer readTimeout, Integer writeTimeout) {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		
		X509TrustManager trustManager = new TrustAllManager();
		HostnameVerifier hostnameVerifier = new TrustAllHostnameVerifier();
		
		builder.sslSocketFactory(createSSLSocketFactory(trustManager), trustManager);
		builder.hostnameVerifier(hostnameVerifier);
		if (dispatcher != null) {
			builder.dispatcher(dispatcher);
		}
		builder.connectTimeout(connectTimeout, TimeUnit.SECONDS);
		builder.readTimeout(readTimeout, TimeUnit.SECONDS);
		builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
		return builder.build();
	}
	
	/**
	 * 获取SSLSocketFactory
	 * @param trustManager
	 * @return
	 */
	public static SSLSocketFactory createSSLSocketFactory(TrustManager trustManager) {
		if (trustManager == null) {
			trustManager = new TrustAllManager();
		}
		SSLSocketFactory sslSocketFactory = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[] { trustManager }, new SecureRandom());
			sslSocketFactory = sc.getSocketFactory();
		} catch (Exception e) {
			
		}
		return sslSocketFactory;
	}
	
	/**
	 * 信任所有的trustManager内部类
	 * @author duoyi
	 */
	private static class TrustAllManager implements X509TrustManager {

		@Override
		public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			
		}

		@Override
		public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
			
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}
	
	/**
	 * 信任所有的HostnameVerifier内部类
	 * @author duoyi
	 */
	private static class TrustAllHostnameVerifier implements HostnameVerifier {

		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
		
	}
}

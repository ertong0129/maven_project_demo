package com.example.demo.business.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.*;

@Slf4j
public class EasyHttpClient {
	
	private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded");
	
	private static OkHttpClient defaultHttpClient;
	private static OkHttpClient shumeiHttpClient;
	private static OkHttpClient tongdunHttpClient;
	private OkHttpClient okHttpClient;
	
	private String url;//请求url
	private List<String> urls;//请求url集合
	private String path = "";//请求路径，拼接在url上
	private List<String> usedUrls;
	private int retryTimes = 0;//重试次数
	private Map<String, Object> paramMap;//请求参数
	private Map<String, String> headMap;//请求头
	private String body;//请求体
	private String requestId;//标识
	private boolean printLog = true;
	
	static {
		init();
	}
	
	private EasyHttpClient() {
		
	}
	
	private EasyHttpClient(OkHttpClient okHttpClient) {
		this.okHttpClient = okHttpClient;
	}
	
	public static EasyHttpClient newInstance() {
		return new EasyHttpClient(defaultHttpClient);
	}

	public static EasyHttpClient newShumeiInstance() {
		return new EasyHttpClient(shumeiHttpClient);
	}

	public static EasyHttpClient newTongdunInstance() {
		return new EasyHttpClient(tongdunHttpClient);
	}
	
	public static EasyHttpClient newInstance(OkHttpClient okHttpClient) {
		return new EasyHttpClient(okHttpClient);
	}
	
	private static void init() {
		Dispatcher dispatcher = new Dispatcher();
		//以下请求数配置只在异步调用的情况下生效
		dispatcher.setMaxRequests(512);
		dispatcher.setMaxRequestsPerHost(128);
		long start = System.currentTimeMillis();
		defaultHttpClient = OkHttpClientUtil.getUnsafeOkHttpClient(dispatcher);
		// 数美http请求类
		shumeiHttpClient = OkHttpClientUtil.getUnsafeOkHttpClient(null, 1, 1, 1);
		// 同盾http请求
		tongdunHttpClient = OkHttpClientUtil.getUnsafeOkHttpClient(null, 1, 1, 1);
		long end = System.currentTimeMillis();
		log.info(String.format("初始化耗时：%sms", end - start));
	}

	/**
	 * 设置是否打印日志的标识
	 * @param flag
	 * @return
	 */
	public EasyHttpClient printLog(boolean flag) {
		this.printLog = flag;
		return this;
	}
	
	/**
	 * 设置请求的url
	 * @param url
	 * @return
	 */
	public EasyHttpClient url(String url) {
		if (url.contains(",")) {
			return url(url.split(","));
		} else {
			this.url = url;
			return this;
		}
	}
	
	/**
	 * 设置多个url用于随机请求
	 * @param url
	 * @return
	 */
	public EasyHttpClient url(String... url) {
		return urls(Arrays.asList(url));
	}
	
	/**
	 * 设置多个url用于随机请求
	 * @param urlList
	 * @return
	 */
	public EasyHttpClient urls(List<String> urlList) {
		if (urlList.size() == 1) {
			return url(urlList.get(0));
		}
		this.urls = new ArrayList<>();
		this.usedUrls = new ArrayList<>();
		this.urls.addAll(urlList);
		return this;
	}
	
	/**
	 * 设置请求路径
	 * @param path
	 * @return
	 */
	public EasyHttpClient path(String path) {
		this.path = path;
		return this;
	}
	
	/**
	 * 增加queryString参数
	 * @param key
	 * @param value
	 * @return
	 */
	public EasyHttpClient addParam(String key, String value) {
		if (paramMap == null) {
			paramMap = new HashMap<>();
		}
		paramMap.put(key, value);
		return this;
	}
	
	/**
	 * 增加queryString参数
	 * @param map
	 * @return
	 */
	public EasyHttpClient addParamMap(Map<String, ?> map) {
		if (paramMap == null) {
			paramMap = new HashMap<>();
		}
		paramMap.putAll(map);
		return this;
	}
	
	/**
	 * 增加请求头
	 * @param key
	 * @param value
	 * @return
	 */
	public EasyHttpClient addHead(String key, String value) {
		if (headMap == null) {
			headMap = new HashMap<>();
		}
		headMap.put(key, value);
		return this;
	}
	
	/**
	 * 增加请求头
	 * @param map
	 * @return
	 */
	public EasyHttpClient addHeadMap(Map<String, String> map) {
		if (headMap == null) {
			headMap = new HashMap<>();
		}
		headMap.putAll(map);
		return this;
	}
	
	/**
	 * 设置请求体
	 * @param body
	 * @return
	 */
	public EasyHttpClient body(String body) {
		this.body = body;
		return this;
	}
	
	/**
	 * 设置Form类型的请求体
	 * @param bodyMap
	 * @return
	 */
	public EasyHttpClient body(Map<String, Object> bodyMap) {
		List<String> paramList = new ArrayList<>();
		bodyMap.forEach((key, value) -> paramList.add(key + "=" + String.valueOf(value)));
		String bodyString = String.join("&", paramList);
		return body(bodyString);
	}
	
	/**
	 * 设置重试次数
	 * @param retryTimes
	 * @return
	 */
	public EasyHttpClient retry(Integer retryTimes) {
		this.retryTimes = retryTimes;
		return this;
	}
	
	/**
	 * get请求
	 * @return
	 */
	public String get() {
		Request request = buildRequest("GET", null);
		return call2(okHttpClient, request, retryTimes);
	}
	
	/**
	 * head 请求
	 * @return
	 */
	public Map<String, Object> head() {
		Request request = buildRequest("HEAD", null);
		long start = System.currentTimeMillis();
		Response response = call(okHttpClient, request);
		long end = System.currentTimeMillis();
		printLog(request, Optional.ofNullable(response).map(Response::code).orElse(null), "", end - start, 0);
		if (response != null && response.isSuccessful()) {
			Map<String, Object> map = new LinkedHashMap<>();
			Headers headers = response.headers();
			headers.names().forEach(name -> map.put(name, headers.get(name)));
			return map;
		}
		return null;
	}
	
	/**
	 * post请求（json）
	 * @return
	 */
	public String post() {
		return post(JSON);
	}
	
	/**
	 * post请求（form表单）
	 * @return
	 */
	public String postForm() {
		return post(FORM);
	}
	
	/**
	 * post请求实际工作方法
	 * @param contentType
	 * @return
	 */
	private String post(MediaType contentType) {
		if (body == null) {
			body = "";
		}
		RequestBody requestBody = RequestBody.create(contentType, body);
		Request request = buildRequest("POST", requestBody);
		return call2(okHttpClient, request, retryTimes);
	}
	
	/**
	 * 构造request
	 * @param method
	 * @param requestBody
	 * @return
	 */
	private Request buildRequest(String method, RequestBody requestBody) {
		Request.Builder builder = new Request.Builder();
		builder.url(buildHttpUrl());
		if (headMap != null) {
			builder.headers(Headers.of(headMap));
		}
		builder.method(method, requestBody);
		return builder.build();
	}
	
	/**
	 * 构建请求的url
	 * @return
	 */
	private HttpUrl buildHttpUrl() {
		HttpUrl httpUrl = HttpUrl.parse(selectUrl());
		if (paramMap != null) {
			HttpUrl.Builder builder = httpUrl.newBuilder();
			//使用okhttp原生的urlencode，会有部分特殊符号不会编码（如：{}），导致请求400失败
			/*for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				builder.addQueryParameter(entry.getKey(), Optional.ofNullable(entry.getValue()).map(String::valueOf).orElse(null));
			}*/
			try {
				for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
					if (entry.getValue() != null) {
						builder.addEncodedQueryParameter(URLEncoder.encode(entry.getKey(), "UTF-8"), 
								URLEncoder.encode(Optional.ofNullable(entry.getValue()).map(String::valueOf).orElse(null), "UTF-8"));
					}
				}
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException("urlencode异常");
			}
			httpUrl = builder.build();
		}
		return httpUrl;
	}
	
	/**
	 * 随机选择一个url
	 * @return
	 */
	private String selectUrl() {
		String selectedUrl;
		if (this.urls != null) {
			if (this.urls.isEmpty()) {
				this.urls.addAll(usedUrls);
				usedUrls.clear();
			}
			Random random = new Random();
			int index = random.nextInt(this.urls.size());
			selectedUrl = this.urls.get(index);
			this.urls.remove(index);
			this.usedUrls.add(selectedUrl);
		} else {
			selectedUrl = this.url;
		}
		return selectedUrl + this.path;
	}
	
	/**
	 * 执行http请求
	 * @param client
	 * @param request
	 * @return
	 */
	private Response call(OkHttpClient client, Request request) {
		Response response = null;
		try {
			log.info("执行httpcall请求：{}", request.url());
			response = client.newCall(request).execute();
		} catch (IOException e) {
			log.error("执行httpcall请求失败, url:{}, body:{}", request.url(), request.body());
			//throw new RuntimeException("执行httpcall请求失败");
		}
		return response;
	}
	
	/**
	 * 执行http请求，记录日志，加上重试机制
	 * @param client
	 * @param request
	 * @param retryTimes
	 * @return
	 */
	private String call2(OkHttpClient client, Request request, int retryTimes) {
		long start = System.currentTimeMillis();
		Response response = call(client, request);
		long end = System.currentTimeMillis();
		Integer responseCode = Optional.ofNullable(response).map(Response::code).orElse(null);
		String responseBody = parseStringResponse(response);
		//打印日志
		printLog(request, responseCode, responseBody, end - start, retryTimes);
		if (retryTimes > 0 && (response == null || !response.isSuccessful())) {
			return call2(client, request, retryTimes - 1);
		}
		
		return responseBody;
	}
	
	/**
	 * 解析http请求返回的数据
	 * @param response
	 * @return
	 */
	private String parseStringResponse(Response response) {
		try {
			return Optional.ofNullable(response).map(Response::body).map(responseBody -> {
				 try {
					return responseBody.string();
				} catch (IOException e) {
					
				}
				return null;
			}).orElse(null);
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
	
	/**
	 * 打印日志
	 * @param request
	 * @param responseCode
	 * @param responseBody
	 * @param duration
	 * @param leftRetryTimes
	 */
	private void printLog(Request request, Integer responseCode, String responseBody, long duration, int leftRetryTimes) {
		if (!printLog) {
			return;
		}
		try {
			if (this.requestId == null || "".equals(this.requestId)) {
				this.requestId = UUID.randomUUID().toString().replace("-", "");
			}
			String requestUrl = Optional.ofNullable(request).map(Request::url).map(HttpUrl::url).map(URL::toString).orElse(null);
			String method = Optional.ofNullable(request).map(Request::method).orElse(null);
			String msg = String.format("执行http请求[%sms]:%n" + 
							"[url]: %s%n" + 
							"[method]: %s%n" +
							"%s" + 
							"%s" +
							"[responseCode]: %s%n" +
							"[responseBody]: %s%n" +
							"[requestId]: %s%n" +
							"[leftRetryTimes]: %s", duration, requestUrl, method, 
						headMap == null ? "" : "[requestHead]: " + JSONObject.toJSONString(headMap) + "\n", 
						"GET".equalsIgnoreCase(method) ? "" : "[requestBody]: " + body + "\n", 
						responseCode, responseBody, this.requestId, leftRetryTimes);
			log.info(msg);
		} catch (Exception e) {
			log.error("执行http请求日志打印失败", e);
		}
	}
}

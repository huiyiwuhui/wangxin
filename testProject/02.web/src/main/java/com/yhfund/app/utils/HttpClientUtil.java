package com.yhfund.app.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.Map.Entry;

public class HttpClientUtil {
	public static final Log logger = LogFactory.getLog("httpclient");

	public static String httpReader(String url) {
		return httpReader(url, "UTF-8");
	}

	public static String httpReader(String url, String code) {
		HttpClient client = null;
		if (url.startsWith("https://")) {
			client = wrapClient(getHttpClient());
		} else {
			client = getHttpClient();
		}
		HttpGet get = new HttpGet(url);
		try {
			HttpResponse res = client.execute(get);
			String result = doWithRespAsString(res);
			EntityUtils.consume(res.getEntity());
			return result;
		} catch (IOException e) {
			logger.error("WeiXinLogin:httpReader执行http请求发生异常", e);
			return null;
		} finally {
			try {
				client.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}

	public static HttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {

				public void checkClientTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws java.security.cert.CertificateException {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
						String paramString) throws java.security.cert.CertificateException {
				}

				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("https", 443, ssf));
			PoolingClientConnectionManager mgr = new PoolingClientConnectionManager(registry);
			return new DefaultHttpClient(mgr, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String httpGet(String url, Map<String, Object> params) {
		url = urlParams(url, params);
		return httpReader(url, "UTF-8");

	}

	public static String httpPost(String url, Map<String, Object> paramMap, String code) {
		if (url == null || url.trim().length() == 0)
			return null;
		HttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		if (paramMap != null) {
			List<org.apache.http.NameValuePair> nvps = new ArrayList<org.apache.http.NameValuePair>();
			Set<Entry<String, Object>> entrySet = paramMap.entrySet();
			for (Iterator<Entry<String, Object>> iter = entrySet.iterator(); iter.hasNext();) {
				Entry<String, Object> me = iter.next();
				nvps.add(new BasicNameValuePair(me.getKey(), (String) me.getValue()));
			}
			post.setEntity(new UrlEncodedFormEntity(nvps, Consts.UTF_8));
		}
		BasicHttpContext context = new BasicHttpContext();
		try {
			HttpResponse res = httpClient.execute(post, context);
			String result = doWithRespAsString(res);
			EntityUtils.consume(res.getEntity());
			return result;

		} catch (IOException e) {
			logger.error("HttpClientUtil:httpPost执行http请求发生异常", e);
			return null;
		} finally {
			try {
				httpClient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}

	}

	public static String httpPost(String url, Map<String, Object> params) {
		return HttpClientUtil.httpPost(url, params, "UTF-8");
	}


	public static String httpPostUrl(String url, Map<String, Object> params) {
		if (url == null || url.trim().length() == 0)
			return null;
		url = urlParams(url, params);
		HttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		try {
			HttpResponse res = httpClient.execute(post);
			String result = doWithRespAsString(res);
			EntityUtils.consume(res.getEntity());
			return result;
		} catch (IOException e) {
			logger.error("HttpClientUtil:httpPostUrl执行http请求发生异常", e);
			return null;
		} finally {
			try {
				httpClient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("m", "t_operate");
		map.put("dstart", "20100815");
		map.put("dend", "20100825");
		map.put("k", "d0fa2c60a263ad288759842dde77b084");
		long start = System.currentTimeMillis();
		// String str =
		// httpPost("http://192.168.1.88:8080/data_api.php?m=t_operate&dstart=20100815&dend=20100825&k=d0fa2c60a263ad288759842dde77b084",map,"utf-8");
		// String str = httpPost(
		// "http://192.168.4.34/bi/data_api.php?m=t_operate&dstart=20100815&dend=20100825&k=d0fa2c60a263ad288759842dde77b084",
		// map, "utf-8");
		String str = httpJosnPost("http://127.0.0.1:8088/wx/active/test", "{\"a\":1}", "utf-8");

		System.out.println(str);
		System.out.println(System.currentTimeMillis() - start);
	}

	public static String httpJosnPost(String url, String json, String code) {
		if (url == null || url.trim().length() == 0)
			return null;
		HttpClient httpClient = getHttpClient();
		HttpPost post = new HttpPost(url);
		post.setHeader("Content-Type", "application/json");
		post.setEntity(new StringEntity(json, ContentType.create(ContentType.APPLICATION_JSON.getMimeType(),
				Consts.UTF_8)));
		try {
			HttpResponse res = httpClient.execute(post);
			String result = doWithRespAsString(res);
			EntityUtils.consume(res.getEntity());
			return result;

		} catch (IOException e) {
			logger.error("HttpClientUtil:httpJosnPost执行http请求发生异常", e);
			return null;
		} finally {
			try {
				httpClient.getConnectionManager().shutdown();
			} catch (Exception ignore) {
			}
		}
	}

	/**
	 * 将参数Map转化为查询字符串. 参数格式: key: 字符串 value: 基本数据类型, 枚举类型, List, 或基本数据类型数组.
	 */
	@SuppressWarnings("rawtypes")
	public static String urlParams(String url, Map<String, Object> params) {
		if (params == null || params.entrySet().size() == 0) {
			return url;
		}
		StringBuilder sb = new StringBuilder();
		// 将Map转成查询字符串.
		for (Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object v = entry.getValue();
			if (key != null && v != null) {
				if (v instanceof List || v instanceof Object[]) {
					Object[] values;
					if (v instanceof List) {
						values = ((List) v).toArray();
					} else {
						values = (Object[]) v;
					}
					for (Object value : values) {
						if (value != null) {
							sb.append(urlEncode(key) + "=" + urlEncode(value.toString()) + "&");
						}
					}
				} else {
					sb.append(urlEncode(key) + "=" + urlEncode(v.toString()) + "&");
				}
			}
		}
		// 去掉结尾的"&"
		if (sb.length() > 0 && sb.charAt(sb.length() - 1) == '&') {
			sb.deleteCharAt(sb.length() - 1);
		}

		if (sb.length() == 0) {
			return url;
		}

		String result;
		if (url.contains("?")) {
			result = url + "&" + sb.toString();
		} else {
			result = url + "?" + sb.toString();
		}
		return result;
	}

	/**
	 * URL 编码.
	 */
	protected static String urlEncode(String input) {
		try {
			return URLEncoder.encode(input, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalArgumentException("Unsupported Encoding Exception", e);
		}
	}

	private static HttpClient getHttpClient() {
		HttpClient client = new DefaultHttpClient();
		client.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET, Consts.UTF_8);
		client.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		return client;
	}

	private static String doWithRespAsString(HttpResponse res) throws IOException {
		HttpEntity en = res.getEntity();
		InputStream is = en.getContent();
//		byte[] bs = new byte[2048];
//		StringBuffer sb = new StringBuffer();
//		int cnt = 0;
//		while (true) {
//			cnt = is.read(bs);
//			if (cnt == -1) {
//				break;
//			}
//			sb.append(new String(bs, 0, cnt, Consts.UTF_8));
//		}
//		return sb.toString();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
		StringBuffer sbf = new StringBuffer();
		String line = null;
		while ((line = br.readLine()) != null)
		{
		sbf.append(line);
		}
		return sbf.toString();
	}
	
}

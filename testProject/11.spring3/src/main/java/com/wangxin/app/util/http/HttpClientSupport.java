//package com.wangxin.app.util.http;
//
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//import org.apache.http.*;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.entity.UrlEncodedFormEntity;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
//import org.apache.http.entity.mime.content.StringBody;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.apache.http.message.BasicHeader;
//import org.apache.http.message.BasicNameValuePair;
//import org.apache.http.params.BasicHttpParams;
//import org.apache.http.params.CoreProtocolPNames;
//import org.apache.http.params.HttpParams;
//
//import java.io.*;
//import java.net.URL;
//import java.net.URLEncoder;
//import java.nio.charset.Charset;
//import java.util.*;
//import java.util.Map.Entry;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * @Description
// * @author liuzhaoq
// * @date 2013-9-24
// */
//public class HttpClientSupport {
//	public static final Log log = LogFactory.getLog(HttpClientSupport.class);
//
//	/**
//	 * 文本类型的MIME类型的关键字列表
//	 */
//	private String textContentType = "text,html,xhtml,xml,json";
//	/**
//	 * 文本消息的MIME类型的正则表达式
//	 */
//	private Pattern pattern;
//
//	public HttpClientSupport() {
//		String[] textContentTypes = textContentType.split(",");
//		StringBuilder sb = new StringBuilder();
//		for (String s : textContentTypes) {
//			sb.append(s + "|");
//		}
//		String regex = sb.toString();
//		regex = regex.substring(0, regex.length() - 1);
//		pattern = Pattern.compile(".*(" + regex + ")+.*",
//				Pattern.CASE_INSENSITIVE);
//	}
//
//	/**
//	 * 字符集
//	 */
//	private String charset = "UTF-8";
//	private HttpParams params = new BasicHttpParams();
//
//	public HttpResult doPostFile(String url, Map<String, Object> paramsPost,
//			File file, String fileName, Map<String, String> headers,
//			boolean... strict) throws IOException {
//
//		HttpClient httpclient = new DefaultHttpClient();
//		httpclient.getParams().setParameter(
//				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//		HttpPost post = new HttpPost(url);
//		// 构造POST参数
//		long boundary = Math.round(Math.random() * 90000) + 10000;
//		MultipartEntity formParams = new MultipartEntity();
//		// if (strict != null && strict.length > 0) {
//		// if (strict[0])
//		// formParams = new MultipartEntity(HttpMultipartMode.STRICT, "A"
//		// + boundary + "-----------------------------------"
//		// + boundary + "A", Charset.forName(charset));
//		// else
//		// formParams = new MultipartEntity(
//		// HttpMultipartMode.BROWSER_COMPATIBLE, "A" + boundary
//		// + "-----------------------------------"
//		// + boundary + "A", Charset.forName(charset));
//		// } else {
//		// formParams = new MultipartEntity(HttpMultipartMode.STRICT, "A"
//		// + boundary + "-----------------------------------"
//		// + boundary + "A", Charset.forName(charset));
//		// }
//		if (paramsPost != null) {
//			Set<String> ketSet = paramsPost.keySet();
//			for (String key : ketSet) {
//				formParams.addPart(
//						key,
//						new StringBody((paramsPost.get(key) == null ? ""
//								: paramsPost.get(key)).toString(), Charset
//								.forName(charset)));
//			}
//		}
//		formParams.addPart("uploadfile", new FileBody(file, "image/jpeg"));
//		HttpResult hr = new HttpResult();
//		InputStream ins = null;
//		try {
//			log.info("The request Content-Type is "
//					+ formParams.getContentType());
//			if (headers != null && headers.size() >= 1) {
//				Set<Entry<String, String>> set = headers.entrySet();
//				for (Entry<String, String> entry : set) {
//					if (entry.getKey().equals(HeaderKey.CONTENT_TYPE.value()))
//						continue;
//					post.setHeader(new BasicHeader(entry.getKey(), entry
//							.getValue()));
//				}
//			}
//			post.setHeader("cookie", headers.get("cookie"));
//
//			// 将POST参数添加到消息正文
//			post.setEntity(formParams);
//			HttpResponse response = httpclient.execute(post);
//			StatusLine st = response.getStatusLine();
//			if (st != null) {
//				readContent(st.getStatusCode(), response, hr, ins);
//			} else {
//				throw new IOException("读取数据超时！");
//			}
//		} finally {
//			if (ins != null)
//				ins.close();
//		}
//		return hr;
//	}
//
//	private String queryStringParser(String queryString)
//			throws UnsupportedEncodingException {
//		StringTokenizer st = new StringTokenizer(queryString, "&");
//		StringBuilder sb = new StringBuilder();
//		while (st.hasMoreTokens()) {
//			String pairs = st.nextToken();
//			if (pairs == null || "".equals(pairs)) {
//				// undo
//			} else {
//				if (pairs.contains("=") && pairs.indexOf("=") != 0) {
//					String key = pairs.substring(0, pairs.indexOf('='));
//					String value = pairs.substring(pairs.indexOf('=') + 1);
//					sb.append(key
//							+ "="
//							+ URLEncoder.encode(value == null ? "" : value,
//									charset) + "&");
//				} else {
//					sb.append(URLEncoder.encode(pairs, charset) + "&");
//				}
//			}
//		}
//		String query = sb.toString();
//		return query.substring(0, query.length() - 1);
//	}
//
//	public HttpResult doGet(String url, Map<String, String> headers)
//			throws IOException {
//		HttpClient httpclient = new DefaultHttpClient(params);
//		URL urls = new URL(url);
//		String path = urls.getProtocol() + "://" + urls.getHost()
//				+ (urls.getPort() == -1 ? "" : ":" + urls.getPort())
//				+ urls.getPath();
//		String query = urls.getQuery();
//		String encodeUrl = "";
//		if (query != null && !"".equals(query))
//			encodeUrl = path + "?" + queryStringParser(query);
//		else
//			encodeUrl = path;
//		log.info("The request url encode-queryparams is " + encodeUrl);
//		HttpGet get = new HttpGet(encodeUrl);
//		HttpResult hr = new HttpResult();
//		InputStream ins = null;
//		try {
//			if (headers != null && headers.size() >= 1) {
//				Set<Entry<String, String>> set = headers.entrySet();
//				for (Entry<String, String> entry : set) {
//					get.setHeader(new BasicHeader(entry.getKey(), entry
//							.getValue()));
//				}
//			}
//			log.info("The request Content-Type is "
//					+ Arrays.toString(get.getHeaders(HeaderKey.CONTENT_TYPE
//							.value())));
//			HttpResponse response = httpclient.execute(get);
//			StatusLine st = response.getStatusLine();
//			if (st != null) {
//				readContent(st.getStatusCode(), response, hr, ins);
//			} else {
//				throw new IOException("读取数据超时！");
//			}
//		} finally {
//			if (ins != null)
//				ins.close();
//		}
//		return hr;
//	}
//
//	public HttpResult doPost(String url, Map<String, Object> paramsPost,
//			Map<String, String> headers) throws IOException {
//		log.info("The request url is " + url);
//		HttpClient httpclient = new DefaultHttpClient(params);
//		HttpPost post = new HttpPost(url);
//		// 构造POST参数
//		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
//		if (paramsPost != null) {
//			Set<String> ketSet = paramsPost.keySet();
//			for (String key : ketSet) {
//				formparams
//						.add(new BasicNameValuePair(key,
//								(paramsPost.get(key) == null ? "" : paramsPost
//										.get(key)).toString()));
//			}
//		}
//		UrlEncodedFormEntity formParams = new UrlEncodedFormEntity(formparams,
//				charset);
//
//		HttpResult hr = new HttpResult();
//		InputStream ins = null;
//		try {
//			if (headers != null && headers.size() >= 1) {
//				Set<Entry<String, String>> set = headers.entrySet();
//				for (Entry<String, String> entry : set) {
//					post.setHeader(new BasicHeader(entry.getKey(), entry
//							.getValue()));
//					if (entry.getKey().equals(HeaderKey.CONTENT_TYPE.value()))
//						formParams.setContentType(entry.getValue());
//				}
//			}
//			log.info("The request Content-Type is "
//					+ Arrays.toString(post.getHeaders(HeaderKey.CONTENT_TYPE
//							.value())));
//			// 将POST参数添加到消息正文
//			post.setEntity(formParams);
//			HttpResponse response = httpclient.execute(post);
//			StatusLine st = response.getStatusLine();
//			if (st != null) {
//				readContent(st.getStatusCode(), response, hr, ins);
//			} else {
//				throw new IOException("读取数据超时！");
//			}
//		} finally {
//			if (ins != null)
//				ins.close();
//		}
//		return hr;
//	}
//
//	private void readContent(int status, HttpResponse response, HttpResult hr,
//			InputStream ins) throws IOException {
//		log.info("Http Status Code is [ " + status + " ] !");
//		hr.setCode(status);
//		Header[] hs = response.getAllHeaders();
//		Map<String, String> map = new HashMap<String, String>();
//		for (Header header : hs) {
//			map.put(header.getName(), header.getValue());
//			log.info("Http Header is [ " + header.getName() + " : "
//					+ header.getValue() + " ] !");
//		}
//		hr.setHeaders(map);
//		if (status == 200) {
//			HttpEntity entity = response.getEntity();
//			ins = entity.getContent();
//			boolean text = false;
//			Matcher matcher = pattern
//					.matcher(entity.getContentType() == null ? "application/octet-stream"
//							: entity.getContentType().getValue());
//			boolean bool = matcher.matches();
//			if (bool) {
//				text = true;
//			}
//			if (text) {
//				BufferedReader br = null;
//				try {
//					StringBuilder sb = new StringBuilder();
//					String str_line = null;
//					br = new BufferedReader(
//							new InputStreamReader(ins,
//									(entity.getContentEncoding() != null
//											&& !"".equals(entity
//													.getContentEncoding()
//													.getValue()) ? entity
//											.getContentEncoding().getValue()
//											: charset)));
//					while ((str_line = br.readLine()) != null) {
//						sb.append(str_line);
//					}
//					String result = sb.toString();
//					log.info("Http Content is [ " + result + " ] !");
//					hr.setContent(result);
//				} finally {
//					if (br != null)
//						br.close();
//				}
//			} else {
//				BufferedInputStream bis = null;
//				byte[] b = new byte[Integer.parseInt(map
//						.get(HeaderKey.CONTENT_LENGTH.value()))];
//				try {
//					int len;
//					bis = new BufferedInputStream(ins);
//					int offset = 0, length = b.length;
//					log.info("offset is  " + offset + " !");
//					log.info("length is  " + length + " !");
//					while ((len = bis.read(b, offset, length)) != -1) {
//						offset = offset + len;
//						length = length - len;
//						log.info("offset is  " + offset + " !");
//						log.info("length is  " + length + " !");
//						log.info("Read " + len + " bytes!");
//						if (length == 0)
//							break;
//					}
//				} finally {
//					if (bis != null)
//						bis.close();
//				}
//				hr.setByteArray(b);
//			}
//		}
//	}
//
//}

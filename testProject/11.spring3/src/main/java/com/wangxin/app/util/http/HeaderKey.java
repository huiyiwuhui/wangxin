//package com.wangxin.app.util.http;
//
///**
// * @Description
// * @author liuzhaoq
// * @date 2013-9-24
// */
//public enum HeaderKey {
//	CONTENT_TYPE("Content-Type"), CONTENT_LENGTH("Content-Length"), USER_AGENT(
//			"User-Agent"), HOST("Host"), ACCEPT("Accept"), ACCEPT_LANGUAGE(
//			"Accept-Lanaguage"), ACCEPT_ENCODING("Accept-Encoding"), CONNECTION(
//			"Connection"), COOKIE("Cookie"), CACHE_CONTROL("Cache-Control"), DATE(
//			"Date"), EXPIRES("Expires"), PROXY_CONNECTION("Proxy-Connection");
//
//	private final String value;
//
//	HeaderKey(String v) {
//		value = v;
//	}
//
//	public String value() {
//		return value;
//	}
//
//	public static HeaderKey fromValue(String v) {
//		for (HeaderKey c : HeaderKey.values()) {
//			if (c.value.equals(v)) {
//				return c;
//			}
//		}
//		throw new IllegalArgumentException(v);
//	}
//}
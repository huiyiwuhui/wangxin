package com.yhfund.utils;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Spring MVC 工具类.
 * 
 * @author liuwei
 */
public class SpringMVCUtils {
	private static Logger logger = Logger.getLogger(SpringMVCUtils.class);
	// -- header 常量定义 --//
	private static final String HEADER_ENCODING = "encoding";
	private static final String HEADER_NOCACHE = "no-cache";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final boolean DEFAULT_NOCACHE = true;
	public static final String MESSAGE_ATTRIBUTE_NAME = "actionMessage";
	public static ModelAndView AJAX_SUCCESS = new ModelAndView("ajax-success");

	private static final JsonMapper jsonMapper = JsonMapper.buildNonDefaultMapper();
	
	private static final JsonMapper normalJsonMapper = JsonMapper.buildNormalMapper();
	

	/**
	 * 直接输出内容的简便函数.
	 * 
	 * eg. render(response, "text/plain", "hello", "encoding:GBK");
	 * render(response, "text/plain", "hello", "no-cache:false");
	 * render(response, "text/plain", "hello", "encoding:GBK",
	 * "no-cache:false");
	 * 
	 * @param headers
	 *            可变的header数组，目前接受的值为"encoding:"或"no-cache:",默认值分别为UTF-8和true.
	 */
	public static void render(HttpServletResponse response, final String contentType, final String content,
			final String... headers) {
		initResponseHeader(response, contentType, headers);
		logger.debug("renderValue:"+content);
		try {
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static void renderJson(HttpServletResponse resp, Object data, String... headers) {
		String result = normalJsonMapper.toJson(data);
		render(resp, "text/plain", result, headers);
	}
	
	public static void renderJsonNormal(HttpServletResponse resp, Object data, String... headers) {
		String result = normalJsonMapper.toJson(data);
		render(resp, "text/plain", result, headers);
	}


	/**
	 * 分析并设置contentType与headers.
	 */
	private static HttpServletResponse initResponseHeader(HttpServletResponse response, final String contentType,
			final String... headers) {
		// 分析headers参数
		String encoding = DEFAULT_ENCODING;
		boolean noCache = DEFAULT_NOCACHE;
		for (String header : headers) {
			String headerName = StringUtils.substringBefore(header, ":");
			String headerValue = StringUtils.substringAfter(header, ":");

			if (StringUtils.equalsIgnoreCase(headerName, HEADER_ENCODING)) {
				encoding = headerValue;
			} else if (StringUtils.equalsIgnoreCase(headerName, HEADER_NOCACHE)) {
				noCache = Boolean.parseBoolean(headerValue);
			} else {
				throw new IllegalArgumentException(headerName + "不是一个合法的header类型");
			}
		}

		// 设置headers参数
		String fullContentType = contentType + ";charset=" + encoding;
		response.setContentType(fullContentType);
		if (noCache) {
			response.setDateHeader("Expires", 1L);
			response.addHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache, no-store, max-age=0");
		}

		return response;
	}

	/**
	 * 将POJO转为Map,用于构造ModelAndView
	 */
	public static Map<String, Object> toParameterMap(Object parameter) {
		if (parameter instanceof Map) {
			return (Map) parameter;
		} else {
			try {
				return PropertyUtils.describe(parameter);
			} catch (Exception e) {
				org.springframework.util.ReflectionUtils.handleReflectionException(e);
				return null;
			}
		}
	}

	/**
	 * 使用session在两个页面间传递信息: 取出属性值.<br>
	 * 前一个页面向session中添加属性"controllerMessage" <br>
	 * 后一个页面从session取出"controllerMessage"属性值并removeAttribute.<br>
	 * 
	 * @see #setControllerMessage(javax.servlet.http.HttpSession, String)
	 * @return session的"controllerMessage"属性值
	 */
	public static String getControllerMessage(HttpSession session) {
		String controllerMessage = (String) session.getAttribute("controllerMessage");
		session.removeAttribute("controllerMessage");
		return controllerMessage;
	}

	/**
	 * 使用session在两个页面间传递信息: 存入属性值.<br>
	 *
	 * @see #getControllerMessage(javax.servlet.http.HttpSession)
	 */
	public static void setControllerMessage(HttpSession session, String message) {
		session.setAttribute("controllerMessage", message);
	}

	/**
	 * 将数组转为List.
	 */
	public static <T> List<T> asList(T[] array) {
		if (array == null) {
			return Collections.emptyList();
		}
		return Arrays.asList(array);
	}
}

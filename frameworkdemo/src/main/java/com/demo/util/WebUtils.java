package com.demo.util;

import org.apache.http.message.BasicNameValuePair;

import com.demo.constans.Constants;
import com.demo.web.RequestContext;
import com.google.common.collect.Lists;

import javax.servlet.http.HttpServletRequest;

import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.List;
import java.util.regex.Pattern;

/**
 * User: zhaotianwu DateTime: 2014/9/11 13:31 Copyright © 2014/9/11 Shanghai
 * Raxtone Software Co.,Ltd Allright Reserved
 */
public class WebUtils extends org.springframework.web.util.WebUtils {

	public static String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];

		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	public static boolean isAjaxRequest(HttpServletRequest request) {
		if (request == null) {
			return false;
		} 
		return (null!=request.getHeader("accept")&&request.getHeader("accept").indexOf("application/json") > -1 || (request
				.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf(
				"XMLHttpRequest") > -1));
	}

	/**
	 * 构造一个编码的url
	 * 
	 * @return
	 */
	public static String buildReturnUrlFromRequest(String baseUrl, List<BasicNameValuePair> params) {
		StringBuilder sb = new StringBuilder(baseUrl).append("?");
		for (BasicNameValuePair param : params) {
			sb.append(param.getName()).append("=").append(param.getValue()).append("&");
		}
		return sb.substring(0,sb.length()-1);

	}
	public static boolean isLoginUri(String uri){
		
		List<String> whiteList =Lists.newArrayList(Constants.WHITE_URL.split(","));
		for (String whiteName : whiteList) {
			if (Pattern.matches(whiteName, uri)) {
				return true;
			}
		}
		return false;
	}
	public static boolean isLogined(){
		return RequestContext.getLoginUser()!=null;
	}
	public static String buildReturnUrl(HttpServletRequest request) {
	    	StringBuffer sb=new StringBuffer(request.getRequestURL()).append("?");
	        Enumeration parameterNames = request.getParameterNames();
	        while (parameterNames.hasMoreElements()) {
	            String key = String.valueOf(parameterNames.nextElement());
	            String[] values =request.getParameterValues(key);
	            for (String value : values) {
	            	sb.append(key).append("=").append(value).append("&");
	            }
	        }
	        return sb.substring(0,sb.length()-1);
	    }
}

package com.demo.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 * @JSONField(serialize=false) 
 * 在属性get方法上加serialize=false可设置属性不输出 name=""
 * 可设置序列化后的属性名称 试用场景 实体对象往协议层数据转换
 * @author Fe
 * 
 */
public class FastJson {

	public static final Logger logger = LoggerFactory.getLogger(FastJson.class);

	public static SerializeConfig mapping = new SerializeConfig();

	public static String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer(
				DEFAULT_DATE_FORMAT));
		JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.DisableCircularReferenceDetect
				.getMask();
	}

	public static <T> String toJson(T t) {
		return JSON.toJSONString(t, mapping);
	}

	public static <T> T fromJson(String json, Class<T> t) {
		return (T) JSON.parseObject(json, t);
	}

	public static JSONObject fromJson(String json) {
		return JSON.parseObject(json);
	}

	public static <T> List<T> jsonToList(String json, Class<T> t) {
		return (List<T>) JSON.parseArray(json, t);
	}

	public static JSONArray jsonToList(String json) {
		return JSON.parseArray(json);
	}

	/**
	 * 
	 * @param object
	 *            从jsonobject 目录节点
	 * @param strings
	 *            层次关系
	 * @return
	 */
	public static Object xNodes(JSONObject jsonObj, String... dirTree) {
		JSONObject deepJsonObj = jsonObj;
		int last = dirTree.length - 1;
		for (int i = 0; i < dirTree.length; i++) {
			if (deepJsonObj == null) {
				return null;
			}
			String node = dirTree[i];
			if (i == last) {
				return deepJsonObj.get(node);
			}
			deepJsonObj = deepJsonObj.getJSONObject(node);
		}
		return null;
	}

	/**
	 * 
	 * @param object
	 *            从jsonobject 目录节点,最后一层自动增加“Text_Value”
	 * @param strings
	 *            层次关系
	 * @return
	 */
	public static Object xNodesForTV(JSONObject jsonObj, String... dirTree) {
		JSONObject testJsonObj = (JSONObject) xNodes(jsonObj, dirTree);
		if (testJsonObj != null) {
			return testJsonObj.get("Text_Value");
		}
		return null;
	}
}

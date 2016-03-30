package com.demo.util.execl.back;

import java.util.List;

import com.demo.util.execl.ExeclInfo;

public interface ImCallBack<T> {
	/**
	 * 行数验证等等
	 * @param data
	 * @return
	 */
	boolean preValidate(ExeclInfo info);
	/**
	 * 每一个行数据验证回调
	 * @param list
	 * @param data
	 * @param index
	 * @return  返回每行的错误提示
	 */
	String cellValidate(List<T> list,T data,int index);
	/**
	 * 成功回调
	 * @param data
	 */
	void success(List<T> data);
	/**
	 * 错误回调
	 * @param uri execl访问uri
	 */
	void error(String uri);
}

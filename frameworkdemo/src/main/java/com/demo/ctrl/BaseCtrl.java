package com.demo.ctrl;

import com.demo.util.RsData;
import com.demo.web.RequestContext;

public abstract class BaseCtrl {
	
	/**
	 * 存取request作用域里的值
	 * @param name
	 * @param value
	 */
	protected void set(String name,Object value){
		ctx().request().setAttribute(name, value);
	}
	@SuppressWarnings("unchecked")
	protected <T extends Object> T get(String name,Class<T> clz){
		return (T)ctx().request().getAttribute(name);
	}
	/**
	 * 存取session作用域里面的值
	 * @param <T>
	 * @param name
	 * @param value
	 */
	protected void setSession(String name,Object value){
		ctx().session().setAttribute(name, value);
	}
	@SuppressWarnings("unchecked")
	protected <T extends Object> T getSession(String name,Class<T> clz){
		return (T)ctx().session().getAttribute(name);
	}
	protected void putResponse(Object data){
		RsData rsData = new RsData(ctx().getRs(), data);
		ctx().setRsData(rsData);
	}
	protected RequestContext ctx() {
		return RequestContext.get();
	}
}

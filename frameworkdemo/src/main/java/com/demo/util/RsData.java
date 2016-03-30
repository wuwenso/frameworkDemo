package com.demo.util;

public class RsData {
	
	private String rs;
	private Object rm;
	
    public RsData(String rs, Object rm){
    	this.rs = rs;
    	this.rm = rm;
    }
	public String getRs() {
		return rs;
	}
	public void setRs(String rs) {
		this.rs = rs;
	}
	public Object getRm() {
		return rm;
	}
	public void setRm(Object rm) {
		this.rm = rm;
	}
	
}

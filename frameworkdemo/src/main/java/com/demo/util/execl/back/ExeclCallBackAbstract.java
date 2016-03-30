package com.demo.util.execl.back;



public abstract class ExeclCallBackAbstract<T> implements ImCallBack<T>{

	private int status=1;
	private String message;
	private String newFileUri;
	
	public String getNewFileUri() {
		return newFileUri;
	}

	public void setNewFileUri(String newFileUri) {
		this.newFileUri = newFileUri;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

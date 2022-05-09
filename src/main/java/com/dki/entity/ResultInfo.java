package com.dki.entity;

public class ResultInfo {
	private boolean result;
	private String errorCode;
	private String errorMsg;

	public boolean getResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public ResultInfo(boolean result, String errorCode, String errorMsg) {
		super();
		this.result = result;
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
}

package com.dki.controller;

public enum CommonErrorCodeStatus {
	
	NO_PARAMETER("E-001", "필수 PARAMETER 누락"),
	NO_ID("E-002", "존재하지 않는 ID입니다."),
	NO_TODO("E-003", "Todo List가 존재하지 않습니다.");
	
	private String code;
	private String msg;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	private CommonErrorCodeStatus(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
	
}

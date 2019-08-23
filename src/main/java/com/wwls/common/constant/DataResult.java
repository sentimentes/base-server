package com.wwls.common.constant;

public class DataResult {

	private Integer status;//返回结果状态；非0失败；0成功；
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	private String msg;//返回结果携带消息
	private Object data;//返回结果需要封装的实体对象
	private String token;//返回结果携带的 令牌（此令牌只有在登录，或者注册成功后才有）
	
}

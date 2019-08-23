package com.wwls.modules.sys.entity;

import java.util.Date;

import com.wwls.common.persistence.DataEntity;

/**
 * 用户token
 * @version 2013-05-15
 */
public class SysUserToken extends DataEntity<SysUserToken> {

	private static final long serialVersionUID = 1L;
	private String tokenName;//用户token
	private String sessionId;//会话ID
	private Date addTime;//添加时间
	
	
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getTokenName() {
		return tokenName;
	}

	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	public SysUserToken(){
		super();
	}

	public SysUserToken(String id){
		super(id);
	}
}
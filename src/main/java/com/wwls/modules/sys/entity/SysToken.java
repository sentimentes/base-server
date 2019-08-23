package com.wwls.modules.sys.entity;

import java.util.Date;

import com.wwls.common.persistence.DataEntity;

/**
 * 用户token
 * @version 2013-05-15
 */
public class SysToken extends DataEntity<SysToken> {

	private static final long serialVersionUID = 1L;
	private String userName;//用户token
	private String password;
	private Date addTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
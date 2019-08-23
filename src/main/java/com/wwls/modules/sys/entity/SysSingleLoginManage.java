package com.wwls.modules.sys.entity;

import java.util.Date;

import com.wwls.common.persistence.DataEntity;

/*
 * author:leijinlian
 * date:2018-08-06
 * description:持久化每一个登录成功的session和userId,用来处理单点登录
 * */
public class SysSingleLoginManage extends DataEntity<SysSingleLoginManage> {
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String sessionId;		// sessionId
	private Date createDate;
	private String activeFlag;   //session活动状态
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	
	
	
}

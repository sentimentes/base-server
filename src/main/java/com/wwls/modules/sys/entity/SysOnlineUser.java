package com.wwls.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 在线用户管理Entity
 * @author hugang
 * @version 2017-07-27
 */
public class SysOnlineUser extends DataEntity<SysOnlineUser> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	
	public SysOnlineUser() {
		super();
	}

	public SysOnlineUser(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户ID长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
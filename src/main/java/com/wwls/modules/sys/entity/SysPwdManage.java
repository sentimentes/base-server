package com.wwls.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 用户密码管理Entity
 * @author hugang
 * @version 2017-07-28
 */
public class SysPwdManage extends DataEntity<SysPwdManage> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;		// 用户登陆名
	private String pwd;		// 加密密码
	
	public SysPwdManage() {
		super();
	}

	public SysPwdManage(String id){
		super(id);
	}

	@Length(min=0, max=128, message="用户登陆名长度必须介于 0 和 128 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=256, message="加密密码长度必须介于 0 和 256 之间")
	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
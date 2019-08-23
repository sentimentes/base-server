/**

 */
package com.wwls.modules.application.entity.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import com.wwls.common.persistence.DataEntity;

/**
 * 应用常规用户Entity
 * @author chenbaichuan
 * @version 2016-06-23
 */
public class AppRoutineUser extends DataEntity<AppRoutineUser> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String muserId;		// 用户主表great_main_user的id
	private String nickName;		// 昵称
	private String loginName;		// 登陆名
	private String password;		// 登陆密码
	private String verification;		// 验证码
	private String officeId;		// sys_office的id
	private String clientId;		// app标识
	private String operationType;  //操作类型  0：注册；1，找回密码

	
	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public AppRoutineUser() {
		super();
	}

	public AppRoutineUser(String id){
		super(id);
	}

	@Length(min=0, max=64, message="用户主表great_main_user的id长度必须介于 0 和 64 之间")
	public String getMuserId() {
		return muserId;
	}

	public void setMuserId(String muserId) {
		this.muserId = muserId;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=0, max=64, message="登陆名长度必须介于 0 和 64 之间")
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=64, message="登陆密码长度必须介于 0 和 64 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=64, message="验证码长度必须介于 0 和 64 之间")
	public String getVerification() {
		return verification;
	}

	public void setVerification(String verification) {
		this.verification = verification;
	}
	
 
	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	@Length(min=0, max=64, message="app标识长度必须介于 0 和 64 之间")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
}
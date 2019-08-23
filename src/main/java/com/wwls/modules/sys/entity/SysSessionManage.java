package com.wwls.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import com.wwls.common.persistence.DataEntity;
import com.wwls.modules.sys.utils.JiaoYanUtils;

/**
 * session管理Entity
 * @author hugang
 * @version 2017-06-21
 */
public class SysSessionManage extends DataEntity<SysSessionManage> {
	
	private static final long serialVersionUID = 1L;
	private String userId;		// 用户ID
	private String sessionId;		// sessionId
	private Integer timeout;//会话结束时间
	private Integer onlineCount;//在线人数配置
	
	public Integer getOnlineCount() {
		return onlineCount;
	}

	public void setOnlineCount(Integer onlineCount) {
		this.onlineCount = Integer.parseInt(JiaoYanUtils.numFilter(""+onlineCount));
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = Integer.parseInt(JiaoYanUtils.numFilter(""+timeout));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public SysSessionManage() {
		super();
	}

	public SysSessionManage(String id){
		super(id);
	}
	
	@Length(min=0, max=64, message="sessionId长度必须介于 0 和 64 之间")
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
}
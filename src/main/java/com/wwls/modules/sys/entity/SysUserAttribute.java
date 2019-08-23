package com.wwls.modules.sys.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.utils.JiaoYanUtils;
import com.wwls.common.persistence.DataEntity;

/**
 * 用户属性配置Entity
 * @author hugang
 * @version 2017-06-20
 */
public class SysUserAttribute extends DataEntity<SysUserAttribute> {
	
	private static final long serialVersionUID = 1L;
	private Integer abnormalTimes;		// 异常触发次数
	private Integer lockTime;		// 异常触发锁定时间
	private Date visitStart;		// 访问开始时间
	private Date visitEnd;		// 访问结束时间
	private String boundedIp;		// 访问受限ip
	private Integer expirationDate;		// 口令有效期限（1到90天）
	private Office office;		// 机构ID
	private String userId;		// 用户id
	private String lockStartTime;		// 锁定时间创建
	private Integer timess;//发生异常的次数
	
	
	public Integer getTimess() {
		return timess;
	}

	public void setTimess(Integer timess) {
		this.timess = Integer.parseInt(JiaoYanUtils.numFilter(""+timess));
	}

	public Integer getLockTime() {
		return lockTime;
	}

	public void setLockTime(Integer lockTime) {
		this.lockTime = Integer.parseInt(JiaoYanUtils.numFilter(""+lockTime));
	}

	public SysUserAttribute() {
		super();
	}

	public SysUserAttribute(String id){
		super(id);
	}

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVisitStart() {
		return visitStart;
	}

	public void setVisitStart(Date visitStart) {
		this.visitStart = visitStart;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getVisitEnd() {
		return visitEnd;
	}

	public void setVisitEnd(Date visitEnd) {
		this.visitEnd = visitEnd;
	}
	
	@Length(min=0, max=64, message="受限ip长度必须介于 0 和 64 之间")
	public String getBoundedIp() {
		return boundedIp;
	}

	public void setBoundedIp(String boundedIp) {
		this.boundedIp = JiaoYanUtils.emailFilter(boundedIp);
	}
	
	
	public Integer getAbnormalTimes() {
		return abnormalTimes;
	}

	public void setAbnormalTimes(Integer abnormalTimes) {
		this.abnormalTimes = Integer.parseInt(JiaoYanUtils.numFilter(""+abnormalTimes));
	}

	public Integer getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Integer expirationDate) {
		this.expirationDate = Integer.parseInt(JiaoYanUtils.numFilter(""+expirationDate));
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getLockStartTime() {
		return lockStartTime;
	}

	public void setLockStartTime(String lockStartTime) {
		this.lockStartTime = lockStartTime;
	}
	
	
}
package com.wwls.modules.application.entity;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 应用角色Entity
 * @author xudongdong
 * @version 2016-06-24
 */
public class CommonRole extends DataEntity<CommonRole> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 应用角色名称
	private String sn;		// 标示
	private String remark;		// 备注
	private String roleIds;   //临时变量
	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public CommonRole() {
		super();
	}

	public CommonRole(String id){
		super(id);
	}

	@Length(min=0, max=100, message="应用角色名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=100, message="标示长度必须介于 0 和 100 之间")
	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}
	
	@Length(min=0, max=255, message="备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
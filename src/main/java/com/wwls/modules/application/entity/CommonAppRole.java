package com.wwls.modules.application.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 应用角色关联管理Entity
 * @author mengyanan
 * @version 2016-06-24
 */
public class CommonAppRole extends DataEntity<CommonAppRole> {

	private static final long serialVersionUID = 1L;
	private String appId; // common_app_info的id
	private String roleId; // 角色common_role的id
	private String remark; // 备注
	private String name;// 名称
	private List<String> roleIds;////角色ID集合
    

	public List<String> getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	private CommonRole commonRole;//角色对象
	public CommonRole getCommonRole() {
		return commonRole;
	}

	public void setCommonRole(CommonRole commonRole) {
		this.commonRole = commonRole;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonAppRole() {
		super();
	}

	public CommonAppRole(String id) {
		super(id);
	}

	@Length(min = 0, max = 64, message = "common_app_info的id长度必须介于 0 和 64 之间")
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	@Length(min = 0, max = 64, message = "角色common_role的id长度必须介于 0 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
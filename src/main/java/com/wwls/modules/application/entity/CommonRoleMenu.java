package com.wwls.modules.application.entity;

import java.util.List;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 菜单角色关联Entity
 * 
 * @author mengyann
 * @version 2016-06-24
 */
public class CommonRoleMenu extends DataEntity<CommonRoleMenu> {

	private static final long serialVersionUID = 1L;
	private String roleId; // common_role的id
	private String menuId; // 权限菜单common_menu的id
	private String remark; // 备注
	private String name;// 名称
 	private CommonMenu commonMenu;// 菜单
	private List<String> menuIds;//// 菜单ID集合

 

	public List<String> getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(List<String> menuIds) {
		this.menuIds = menuIds;
	}

	public CommonMenu getCommonMenu() {
		return commonMenu;
	}

	public void setCommonMenu(CommonMenu commonMenu) {
		this.commonMenu = commonMenu;
	}

 

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CommonRoleMenu() {
		super();
	}

	public CommonRoleMenu(String id) {
		super(id);
	}

 

	@Length(min = 0, max = 64, message = "权限菜单common_menu的id长度必须介于 0 和 64 之间")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Length(min = 0, max = 255, message = "备注长度必须介于 0 和 255 之间")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Length(min = 0, max = 64, message = "common_role的id长度必须介于 0 和 64 之间")
	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
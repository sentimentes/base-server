package com.wwls.modules.application.entity;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 应用菜单Entity
 * @author mengyanan
 * @version 2016-06-24
 */
public class CommonMenu extends DataEntity<CommonMenu> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String sort;		// 排序
	private String api;         //对外API
	private String href;		// 链接
	private String target;		// 目标
	private String icon;		// 图标
	private String appId;// 临时变量
	private String isShow;		// 是否在菜单中显示
	private String menuType;		// 菜单类型
	private String integral;		// 积分
	private String menuIds;   //临时变量
	 
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	

	public String getMenuIds() {
		return menuIds;
	}

	public void setMenuIds(String menuIds) {
		this.menuIds = menuIds;
	}

	public CommonMenu() {
		super();
	}

	public CommonMenu(String id){
		super(id);
	}

	@Length(min=1, max=100, message="名称长度必须介于 1 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=2000, message="链接长度必须介于 0 和 2000 之间")
	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}
	
	@Length(min=0, max=20, message="目标长度必须介于 0 和 20 之间")
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	@Length(min=0, max=100, message="图标长度必须介于 0 和 100 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	@Length(min=0, max=1, message="是否在菜单中显示长度必须介于 0 和 1 之间")
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
	@Length(min=0, max=20, message="菜单类型长度必须介于 0 和 20 之间")
	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	
	@Length(min=0, max=10, message="积分长度必须介于 0 和 10 之间")
	public String getIntegral() {
		return integral;
	}

	public void setIntegral(String integral) {
		this.integral = integral;
	}
	
}
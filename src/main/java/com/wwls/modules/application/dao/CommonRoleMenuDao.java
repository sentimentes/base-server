package com.wwls.modules.application.dao;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.CommonRoleMenu;

/**
 * 菜单角色关联DAO接口
 * @author mengyann
 * @version 2016-06-24
 */
@MyBatisDao
public interface CommonRoleMenuDao extends CrudDao<CommonRoleMenu> {
	/**
	 * 查询已经分配的菜单列表
	 ***/
	public List<CommonRoleMenu> findAlreadyList(CommonRoleMenu commonRoleMenu);

}
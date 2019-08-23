package com.wwls.modules.application.dao;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.CommonAppRole;

/**
 * 应用角色关联管理DAO接口
 * 
 * @author mengyanan
 * @version 2016-06-24
 */
@MyBatisDao
public interface CommonAppRoleDao extends CrudDao<CommonAppRole> {
	
	/***
	 * 查找已经分配的角色列表
	 * ***/
	public List<CommonAppRole>findAlreadyList(CommonAppRole commonapprole);
}
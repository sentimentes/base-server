package com.wwls.modules.application.dao;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.CommonRole;

/**
 * 应用角色DAO接口
 * 
 * @author xudongdong
 * @version 2016-06-24
 */
@MyBatisDao
public interface CommonRoleDao extends CrudDao<CommonRole> {
	/***
	 * 查找尚未配的角色列表
	 ***/
	public List<CommonRole> findNotYetList(CommonRole commonRole);

	 

}
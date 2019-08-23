package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysUserRole;

/**
 * 用户角色关联DAO接口
 * @author hugang
 * @version 2017-07-31
 */
@MyBatisDao
public interface SysUserRoleDao extends CrudDao<SysUserRole> {
	
}
package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysSessionManage;

/**
 * session管理DAO接口
 * @author hugang
 * @version 2017-06-21
 */
@MyBatisDao
public interface SysSessionManageDao extends CrudDao<SysSessionManage> {
	
}
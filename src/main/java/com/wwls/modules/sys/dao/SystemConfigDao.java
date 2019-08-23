package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SystemConfig;

/**
 * 系统配置DAO接口
 * @author hugang
 * @version 2016-07-01
 */
@MyBatisDao
public interface SystemConfigDao extends CrudDao<SystemConfig> {
	
}
package com.wwls.modules.application.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.CommonAppVersion;

/**
 * app版本管理DAO接口
 * @author fanbo
 * @version 2016-07-01
 */
@MyBatisDao
public interface CommonAppVersionDao extends CrudDao<CommonAppVersion> {
	
}
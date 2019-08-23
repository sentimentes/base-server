package com.wwls.modules.application.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.CommonAppInfo;

/**
 * 应用信息DAO接口
 * @author mengyanan
 * @version 2016-06-24
 */
@MyBatisDao
public interface CommonAppInfoDao extends CrudDao<CommonAppInfo> {
	
}
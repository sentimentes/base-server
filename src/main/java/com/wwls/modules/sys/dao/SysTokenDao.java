package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysToken;

/**
 * tokenDAO接口
 * @version 2014-05-16
 */
@MyBatisDao
public interface SysTokenDao extends CrudDao<SysToken> {

	
}

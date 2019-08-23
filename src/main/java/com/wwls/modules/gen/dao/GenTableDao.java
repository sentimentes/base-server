package com.wwls.modules.gen.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.gen.entity.GenTable;

/**
 * 业务表DAO接口
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
	
}

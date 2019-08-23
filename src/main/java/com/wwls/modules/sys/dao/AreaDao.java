package com.wwls.modules.sys.dao;

import java.util.List;

import com.wwls.common.persistence.TreeDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.Area;

/**
 * 区域DAO接口
 * @version 2014-05-16
 */
@MyBatisDao
public interface AreaDao extends TreeDao<Area> {
	public List<Area> findPhoneAll(Area area);
}

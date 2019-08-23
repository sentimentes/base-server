package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysUserAttribute;

/**
 * 用户属性配置DAO接口
 * @author hugang
 * @version 2017-06-20
 */
@MyBatisDao
public interface SysUserAttributeDao extends CrudDao<SysUserAttribute> {
	//根据用户ID创建锁定的时间
	public int updateStatus(SysUserAttribute sysUserAttribute);
	
	//根据用户ID更新异常次数
	public int updateTimes(SysUserAttribute sysUserAttribute);
}
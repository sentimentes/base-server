package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysOnlineUser;

/**
 * 在线用户管理DAO接口
 * @author hugang
 * @version 2017-07-27
 */
@MyBatisDao
public interface SysOnlineUserDao extends CrudDao<SysOnlineUser> {
	
}
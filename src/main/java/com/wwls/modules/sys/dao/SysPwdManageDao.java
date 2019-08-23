package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysPwdManage;

/**
 * 用户密码管理DAO接口
 * @author hugang
 * @version 2017-07-28
 */
@MyBatisDao
public interface SysPwdManageDao extends CrudDao<SysPwdManage> {
	
}
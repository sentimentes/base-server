package com.wwls.modules.sys.dao;

import java.util.List;
import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.SysSingleLoginManage;

/**
 * 单点登录管理DAO接口
 * @author leijinlian
 * @version 2018-08-06
 */
@MyBatisDao
public interface SysSingleLoginManageDao extends CrudDao<SysSingleLoginManage> {
	public List<SysSingleLoginManage> getOldList(SysSingleLoginManage sysSingleLoginManage);
	public void  deleteOldSessions(SysSingleLoginManage sysSingleLoginManage);
}
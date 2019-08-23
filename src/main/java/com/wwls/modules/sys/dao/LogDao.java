package com.wwls.modules.sys.dao;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.Log;

/**
 * 日志DAO接口
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {
	public Log getLoginCount(Log log);//登录异常
	public Log getYqfwCount(Log log);//越权访问
	public Log getYbycCount(Log log);//一般异常
	public Log getIpCount(Log log);//ip异常
	public Log getZcCount(Log log);//正常日志
	
	public Log getTotalCount(Log log);//总日志数
}

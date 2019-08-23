package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.common.utils.DateUtils;
import com.wwls.modules.sys.dao.LogDao;
import com.wwls.modules.sys.entity.Log;

/**
 * 日志Service
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {

	public Page<Log> findPage(Page<Log> page, Log log) {
		if(log.getSortType()==null){
			log.setSortType("0");
		}

		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
public List<Log> findList(Log log) {
		
		if(log.getSortType()==null){
			log.setSortType("1");
		}
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findList(log);
		
	}
	
	
	/**
	 * 获取登录异常日志数量
	 */
	public Log getLoginCount(Log log) {
		if (log.getSortType() == null) {
			log.setSortType("1");
		}
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return dao.getLoginCount(log);

	}
	
	
	/**
	 * 统计越权访问总数
	 */
	public Log getYqfwCount(Log log) {
		if (log.getSortType() == null) {
			log.setSortType("1");
		}
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return dao.getYqfwCount(log);

	}
	
	/**
	 * 统计一般异常总数
	 */
	public Log getYbycCount(Log log) {
		if (log.getSortType() == null) {
			log.setSortType("1");
		}
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return dao.getYbycCount(log);

	}
	
	
	/**
	 * 统计Ip异常总数
	 */
	public Log getIpCount(Log log) {
		if (log.getSortType() == null) {
			log.setSortType("1");
		} 
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return dao.getIpCount(log);

	}
	
	
	/**
	 * 统计正常日志总数
	 */
	public Log getZcCount(Log log) {
		if (log.getSortType() == null) {
			log.setSortType("1");
		}
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return dao.getZcCount(log);

	}
	
	
	/**
	 * 统计日志总数
	 */
	public Log getTotalCount(Log log) {
		if (log.getSortType() == null) {
			log.setSortType("1");
		}
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null) {
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null) {
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		return dao.getTotalCount(log);

	}

}

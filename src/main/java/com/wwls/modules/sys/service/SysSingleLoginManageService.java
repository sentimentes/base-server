package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysSingleLoginManage;
import com.wwls.modules.sys.dao.SysSingleLoginManageDao;

/**
 * 单点登录管理Service
 * @author leijinlian
 * @version 2018-08-06
 */
@Service
@Transactional(readOnly = true)
public class SysSingleLoginManageService extends CrudService<SysSingleLoginManageDao, SysSingleLoginManage> {
    @Autowired
	public SysSingleLoginManageDao sysSingleLoginManageDao;
	public SysSingleLoginManage get(String id) {
		return super.get(id);
	}
	
	public List<SysSingleLoginManage> findList(SysSingleLoginManage sysSingleLoginManage) {
		return super.findList(sysSingleLoginManage);
	}
	
	@Transactional(readOnly = false)
	public void save(SysSingleLoginManage sysSingleLoginManage) {
		dao.insert(sysSingleLoginManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysSingleLoginManage sysSingleLoginManage) {
		super.delete(sysSingleLoginManage);
	}
	public List<SysSingleLoginManage> getOldList(SysSingleLoginManage sysSingleLoginManage) {
		return dao.getOldList(sysSingleLoginManage);
	}
	@Transactional(readOnly = false)
	public void deleteOldSessions(SysSingleLoginManage sysSingleLoginManage) {
		dao.deleteOldSessions(sysSingleLoginManage);
	}
}
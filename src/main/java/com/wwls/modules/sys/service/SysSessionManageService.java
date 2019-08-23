package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysSessionManage;
import com.wwls.modules.sys.dao.SysSessionManageDao;

/**
 * session管理Service
 * @author hugang
 * @version 2017-06-21
 */
@Service
@Transactional(readOnly = true)
public class SysSessionManageService extends CrudService<SysSessionManageDao, SysSessionManage> {

	public SysSessionManage get(String id) {
		return super.get(id);
	}
	
	public List<SysSessionManage> findList(SysSessionManage sysSessionManage) {
		return super.findList(sysSessionManage);
	}
	
	public Page<SysSessionManage> findPage(Page<SysSessionManage> page, SysSessionManage sysSessionManage) {
		return super.findPage(page, sysSessionManage);
	}
	
	@Transactional(readOnly = false)
	public void save(SysSessionManage sysSessionManage) {
		dao.update(sysSessionManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysSessionManage sysSessionManage) {
		super.delete(sysSessionManage);
	}
	
}
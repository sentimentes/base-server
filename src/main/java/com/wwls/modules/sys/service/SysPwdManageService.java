package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysPwdManage;
import com.wwls.modules.sys.dao.SysPwdManageDao;

/**
 * 用户密码管理Service
 * @author hugang
 * @version 2017-07-28
 */
@Service
@Transactional(readOnly = true)
public class SysPwdManageService extends CrudService<SysPwdManageDao, SysPwdManage> {

	public SysPwdManage get(String id) {
		return super.get(id);
	}
	
	public List<SysPwdManage> findList(SysPwdManage sysPwdManage) {
		return super.findList(sysPwdManage);
	}
	
	public Page<SysPwdManage> findPage(Page<SysPwdManage> page, SysPwdManage sysPwdManage) {
		return super.findPage(page, sysPwdManage);
	}
	
	@Transactional(readOnly = false)
	public void save(SysPwdManage sysPwdManage) {
		super.save(sysPwdManage);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysPwdManage sysPwdManage) {
		super.delete(sysPwdManage);
	}
	
}
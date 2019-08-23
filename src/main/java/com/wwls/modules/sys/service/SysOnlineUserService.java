package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysOnlineUser;
import com.wwls.modules.sys.dao.SysOnlineUserDao;

/**
 * 在线用户管理Service
 * @author hugang
 * @version 2017-07-27
 */
@Service
@Transactional(readOnly = true)
public class SysOnlineUserService extends CrudService<SysOnlineUserDao, SysOnlineUser> {

	public SysOnlineUser get(String id) {
		return super.get(id);
	}
	
	public List<SysOnlineUser> findList(SysOnlineUser sysOnlineUser) {
		return super.findList(sysOnlineUser);
	}
	
	public Page<SysOnlineUser> findPage(Page<SysOnlineUser> page, SysOnlineUser sysOnlineUser) {
		return super.findPage(page, sysOnlineUser);
	}
	
	@Transactional(readOnly = false)
	public void save(SysOnlineUser sysOnlineUser) {
		super.save(sysOnlineUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysOnlineUser sysOnlineUser) {
		super.delete(sysOnlineUser);
	}
	
}
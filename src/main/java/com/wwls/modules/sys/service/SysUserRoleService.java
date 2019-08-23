package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysUserRole;
import com.wwls.modules.sys.dao.SysUserRoleDao;

/**
 * 用户角色关联Service
 * @author hugang
 * @version 2017-07-31
 */
@Service
@Transactional(readOnly = true)
public class SysUserRoleService extends CrudService<SysUserRoleDao, SysUserRole> {

	public SysUserRole get(String id) {
		return super.get(id);
	}
	
	public List<SysUserRole> findList(SysUserRole sysUserRole) {
		return super.findList(sysUserRole);
	}
	
	public Page<SysUserRole> findPage(Page<SysUserRole> page, SysUserRole sysUserRole) {
		return super.findPage(page, sysUserRole);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserRole sysUserRole) {
		super.save(sysUserRole);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserRole sysUserRole) {
		super.delete(sysUserRole);
	}
	
}
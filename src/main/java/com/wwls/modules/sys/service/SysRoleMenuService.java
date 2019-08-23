package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysRoleMenu;
import com.wwls.modules.sys.dao.SysRoleMenuDao;

/**
 * 角色菜单管理Service
 * @author hugang
 * @version 2017-07-31
 */
@Service
@Transactional(readOnly = true)
public class SysRoleMenuService extends CrudService<SysRoleMenuDao, SysRoleMenu> {

	public SysRoleMenu get(String id) {
		return super.get(id);
	}
	
	public List<SysRoleMenu> findList(SysRoleMenu sysRoleMenu) {
		return super.findList(sysRoleMenu);
	}
	
	public Page<SysRoleMenu> findPage(Page<SysRoleMenu> page, SysRoleMenu sysRoleMenu) {
		return super.findPage(page, sysRoleMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(SysRoleMenu sysRoleMenu) {
		super.save(sysRoleMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysRoleMenu sysRoleMenu) {
		super.delete(sysRoleMenu);
	}
	
}
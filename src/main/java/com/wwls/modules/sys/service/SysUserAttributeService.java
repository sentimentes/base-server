package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.sys.entity.SysUserAttribute;
import com.wwls.modules.sys.dao.SysUserAttributeDao;

/**
 * 用户属性配置Service
 * @author hugang
 * @version 2017-06-20
 */
@Service
@Transactional(readOnly = true)
public class SysUserAttributeService extends CrudService<SysUserAttributeDao, SysUserAttribute> {

	public SysUserAttribute get(String id) {
		return super.get(id);
	}
	
	public List<SysUserAttribute> findList(SysUserAttribute sysUserAttribute) {
		return super.findList(sysUserAttribute);
	}
	
	public Page<SysUserAttribute> findPage(Page<SysUserAttribute> page, SysUserAttribute sysUserAttribute) {
		return super.findPage(page, sysUserAttribute);
	}
	
	@Transactional(readOnly = false)
	public void save(SysUserAttribute sysUserAttribute) {
		super.save(sysUserAttribute);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysUserAttribute sysUserAttribute) {
		super.delete(sysUserAttribute);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(SysUserAttribute sysUserAttribute) {
		dao.updateStatus(sysUserAttribute);
	}
	
	@Transactional(readOnly = false)
	public void updateTimes(SysUserAttribute sysUserAttribute) {
		dao.updateTimes(sysUserAttribute);
	}
	
}
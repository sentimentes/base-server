package com.wwls.modules.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.dao.CommonAppRoleDao;
import com.wwls.modules.application.entity.CommonAppRole;

/**
 * 应用角色关联管理Service
 * 
 * @author mengyanan
 * @version 2016-06-24
 */
@Service
@Transactional(readOnly = true)
public class CommonAppRoleService extends CrudService<CommonAppRoleDao, CommonAppRole> {

	public CommonAppRole get(String id) {
		return super.get(id);
	}

	public List<CommonAppRole> findList(CommonAppRole commonAppRole) {
		return super.findList(commonAppRole);
	}

	public Page<CommonAppRole> findPage(Page<CommonAppRole> page, CommonAppRole commonAppRole) {
		return super.findPage(page, commonAppRole);
	}
	
	/****
	 * 查看已经分配的列表
	 * */
	public Page<CommonAppRole> findAlreadyPage(Page<CommonAppRole> page, CommonAppRole commonAppRole) {
		commonAppRole.setPage(page);
		
		 return page.setList(dao.findAlreadyList(commonAppRole));
	}

	@Transactional(readOnly = false)
	public void save(CommonAppRole commonAppRole) {
		if(commonAppRole!=null &&commonAppRole.getRoleIds()!=null ){
		for(String roleId:commonAppRole.getRoleIds()){
			CommonAppRole roleapp = new CommonAppRole();
			roleapp.setRoleId(roleId);
			roleapp.setAppId(commonAppRole.getAppId());
			super.save(roleapp);
			
		}	
			
		}
		
	}

	@Transactional(readOnly = false)
	public void delete(CommonAppRole commonAppRole) {
		super.delete(commonAppRole);
	}

	
}
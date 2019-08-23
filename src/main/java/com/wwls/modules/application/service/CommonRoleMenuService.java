package com.wwls.modules.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.dao.CommonRoleMenuDao;
import com.wwls.modules.application.entity.CommonRoleMenu;

/**
 * 菜单角色关联Service
 * @author mengyann
 * @version 2016-06-24
 */
@Service
@Transactional(readOnly = true)
public class CommonRoleMenuService extends CrudService<CommonRoleMenuDao, CommonRoleMenu> {

	public CommonRoleMenu get(String id) {
		return super.get(id);
	}

	// 查找菜单的列表
	public List<CommonRoleMenu> findList(CommonRoleMenu commonRoleMenu) {
		return super.findList(commonRoleMenu);
	}

 
	public Page<CommonRoleMenu> findPage(Page<CommonRoleMenu> page, CommonRoleMenu commonRoleMenu) {
		commonRoleMenu.setPage(page);
		page.setList(dao.findList(commonRoleMenu));
		return page;

	}

	// 已经分配菜單
	public Page<CommonRoleMenu> findAlreadyPage(Page<CommonRoleMenu> page, CommonRoleMenu commonRoleMenu) {
		commonRoleMenu.setPage(page);
		page.setList(dao.findAlreadyList(commonRoleMenu));
		return page;

	}
	
	
	@Transactional(readOnly = false)
	public void save(CommonRoleMenu commonRoleMenu) {
	if(commonRoleMenu!=null&&commonRoleMenu.getMenuIds()!=null){
		
		for(String menuId:commonRoleMenu.getMenuIds()){
			CommonRoleMenu crm = new CommonRoleMenu();
			crm.setMenuId(menuId);
			crm.setRoleId(commonRoleMenu.getRoleId());
			super.save(crm);
		}
		
		
	}	
		
		
	}

	@Transactional(readOnly = false)
	public void delete(CommonRoleMenu commonRoleMenu) {
		super.delete(commonRoleMenu);
	}

}
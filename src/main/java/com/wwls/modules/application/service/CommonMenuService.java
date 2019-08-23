package com.wwls.modules.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.entity.CommonMenu;
import com.wwls.modules.application.dao.CommonMenuDao;

/**
 * 应用菜单Service
 * @author mengyanan
 * @version 2016-06-24
 */
@Service
@Transactional(readOnly = true)
public class CommonMenuService extends CrudService<CommonMenuDao, CommonMenu> {

	public CommonMenu get(String id) {
		return super.get(id);
	}
	
	public List<CommonMenu> findList(CommonMenu commonMenu) {
		return super.findList(commonMenu);
	}
	
	public List<CommonMenu> findAppMenuList(CommonMenu commonMenu) {
		return dao.findAppMenuList(commonMenu);
	}
	public Page<CommonMenu> findPage(Page<CommonMenu> page, CommonMenu commonMenu) {
		return super.findPage(page, commonMenu);
	}
	
	
	/*** 查找尚未分配的列表 ****/
	@Transactional(readOnly = false)
	public Page<CommonMenu> findNotYetPage(Page<CommonMenu> page, CommonMenu commonMenu) {
		commonMenu.setPage(page);

		return page.setList(dao.findNotYetList(commonMenu));
	}

	
	@Transactional(readOnly = false)
	public void save(CommonMenu commonMenu) {
		super.save(commonMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommonMenu commonMenu) {
		super.delete(commonMenu);
	}
	
}
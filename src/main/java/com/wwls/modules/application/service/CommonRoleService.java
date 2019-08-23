package com.wwls.modules.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.dao.CommonRoleDao;
import com.wwls.modules.application.entity.CommonRole;

/**
 * 应用角色Service
 * @author xudongdong
 * @version 2016-06-24
 */
@Service
@Transactional(readOnly = true)
public class CommonRoleService extends CrudService<CommonRoleDao, CommonRole> {

	public CommonRole get(String id) {
		return super.get(id);
	}

	public List<CommonRole> findList(CommonRole commonRole) {
		return super.findList(commonRole);
	}

	/*** 查找尚未分配的列表 ****/
	@Transactional(readOnly = false)
	public Page<CommonRole> findNotYetPage(Page<CommonRole> page, CommonRole commonRole) {
		commonRole.setPage(page);

		return page.setList(dao.findNotYetList(commonRole));
	}

 

	public Page<CommonRole> findPage(Page<CommonRole> page, CommonRole commonRole) {
		return super.findPage(page, commonRole);
	}

	@Transactional(readOnly = false)
	public void save(CommonRole commonRole) {
		super.save(commonRole);
	}

	@Transactional(readOnly = false)
	public void delete(CommonRole commonRole) {
		super.delete(commonRole);
	}

}
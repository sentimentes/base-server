/**

 */
package com.wwls.modules.application.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.dao.user.AppMainUserDao;
import com.wwls.modules.application.entity.user.AppMainUser;

/**
 * 应用用户主表Service
 * @author chenbaichuan
 * @version 2016-06-23
 */
@Service
@Transactional(readOnly = true)
public class AppMainUserService extends CrudService<AppMainUserDao, AppMainUser> {

	public AppMainUser get(String id) {
		return super.get(id);
	}
	
	public List<AppMainUser> findList(AppMainUser appMainUser) {
		return super.findList(appMainUser);
	}
	
	public List<AppMainUser> findTokenList(AppMainUser appMainUser) {
		return dao.findTokenList(appMainUser);
	}
	public Page<AppMainUser> findPage(Page<AppMainUser> page, AppMainUser appMainUser) {
		return super.findPage(page, appMainUser);
	}
	
	@Transactional(readOnly = false)
	public void save(AppMainUser appMainUser) {
		dao.saveInformation(appMainUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(AppMainUser appMainUser) {
		super.delete(appMainUser);
	}
	@Transactional(readOnly = false)
	public void insert(AppMainUser appMainUser) {
		dao.insert(appMainUser);
	}
	
	@Transactional(readOnly = false)
	public void saveInformation(AppMainUser appMainUser) {
		dao.saveInformation(appMainUser);
	}
	
	@Transactional(readOnly = false)
	public void updateInfo(AppMainUser appMainUser) {
		dao.updateInfo(appMainUser);
	}
	
	@Transactional(readOnly = false)
	public void updateImg(AppMainUser appMainUser) {
		dao.updateImg(appMainUser);
	}
}
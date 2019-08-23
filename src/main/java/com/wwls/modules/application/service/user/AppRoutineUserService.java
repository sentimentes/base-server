package com.wwls.modules.application.service.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.dao.user.AppMainUserDao;
import com.wwls.modules.application.dao.user.AppRoutineUserDao;
import com.wwls.modules.application.entity.user.AppRoutineUser;

/**
 * 应用常规用户Service
 * @author chenbaichuan
 * @version 2016-06-23
 */
@Service
@Transactional(readOnly = true)
public class AppRoutineUserService extends CrudService<AppRoutineUserDao, AppRoutineUser> {
	@Autowired
	private AppMainUserDao appmainuserdao;

	public AppRoutineUser get(String id) {
		return super.get(id);
	}
	
	/**
	 * 根据主用户id获取常规用户的信息
	 * @param appRoutineUser
	 * @return
	 */
	public AppRoutineUser getUserInfoById(AppRoutineUser appRoutineUser) {
		return dao.getUserInfoById(appRoutineUser);
	}
	
	public List<AppRoutineUser> findList(AppRoutineUser appRoutineUser) {
		return super.findList(appRoutineUser);
	}
	
	public Page<AppRoutineUser> findPage(Page<AppRoutineUser> page, AppRoutineUser appRoutineUser) {
		return super.findPage(page, appRoutineUser);
	}
	
	@Transactional(readOnly = false)
	public void save(AppRoutineUser appRoutineUser) {
		super.save(appRoutineUser);
	}
	
//	@Transactional(readOnly = false)
//	public void saveObject(AppRoutineUser appRoutineUser,AppMainUser appmainuser) {
//		appmainuser.setNickName(appRoutineUser.getLoginName());
//		appmainuser.setGrade("1");
//		appmainuser.setGradeName("LV1");
//		appmainuser.setLoginType("1");
//		//appmainuser.setChannelId("注册用户");
//		if(appRoutineUser.getMuserId()!=null){
//			appmainuser.setId(appRoutineUser.getMuserId());
//		}else{
//			appmainuser.preInsert();
//			appRoutineUser.setMuserId(appmainuser.getId());
//		}
//		
//		appmainuserdao.insert(appmainuser);
//		
//		McShoppingCar mcshoppingcar = new McShoppingCar();
//		mcshoppingcar.preInsert();
// 
//		mcshoppingcar.setMemberId(appmainuser.getId());
//		mcshoppingcar.setShopId(appmainuser.getOfficeId());
//		mcShoppingCarDao.insert(mcshoppingcar);
//		super.save(appRoutineUser);
//	}
	
	@Transactional(readOnly = false)
	public void delete(AppRoutineUser appRoutineUser) {
		super.delete(appRoutineUser);
	}
	public AppRoutineUser getByLoginName(AppRoutineUser appRoutineUser){
		return dao.getByLoginName(appRoutineUser);
	}

	public AppMainUserDao getAppmainuserdao() {
		return appmainuserdao;
	}

	public void setAppmainuserdao(AppMainUserDao appmainuserdao) {
		this.appmainuserdao = appmainuserdao;
	}
	
}
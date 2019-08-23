/**

 */
package com.wwls.modules.application.dao.user;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.user.AppRoutineUser;

/**
 * 应用常规用户DAO接口
 * @author chenbaichuan
 * @version 2016-06-23
 */
@MyBatisDao
public interface AppRoutineUserDao extends CrudDao<AppRoutineUser> {
	public AppRoutineUser getByLoginName(AppRoutineUser appRoutineUser);
	
	/**
	 * 根据主用户id获取常规用户信息
	 * @param appRoutineUser
	 * @return
	 */
	public AppRoutineUser getUserInfoById(AppRoutineUser appRoutineUser);
}
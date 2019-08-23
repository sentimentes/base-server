/**

 */
package com.wwls.modules.application.dao.user;

import java.util.List;
import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.user.AppMainUser;

/**
 * 应用用户主表DAO接口
 * @author chenbaichuan
 * @version 2016-06-23
 */
@MyBatisDao
public interface AppMainUserDao extends CrudDao<AppMainUser> {
	
	
	public void saveObject(AppMainUser appMainUser);
	public void updateInfo(AppMainUser appMainUser);
	public List<AppMainUser> findTokenList(AppMainUser appMainUser);
	public int saveInformation(AppMainUser appMainUser) ;
	
	public int updateImg(AppMainUser appMainUser);
	
	/**
	 * 用户充值更新接口
	 * @author hugang
	 * @version 2016-09-02 PM 18:56
	 */
	public int updateMyMoney(AppMainUser appMainUser);
	
	/**
	 * 更新用户沃豆数量
	 * @author hugang
	 * @version 2016-12-08 AM 09:56
	 */
	public int updateMyWoDou(AppMainUser appMainUser);
	
	public AppMainUser getByWhiteListId(String user);
	
	public int updateById(AppMainUser appMainUser);
	
	public void saveImg(AppMainUser appMainUser);
}
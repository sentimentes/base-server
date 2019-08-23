/**

 */
package com.wwls.modules.application.dao.user;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.user.AppThirdPartUser;

/**
 * 应用第三方用户DAO接口
 * @author chenbaichuan
 * @version 2016-06-23
 */
@MyBatisDao
public interface AppThirdPartUserDao extends CrudDao<AppThirdPartUser> {
	public AppThirdPartUser getUserByOpenId(AppThirdPartUser appThirdPartUser);
	
	public AppThirdPartUser getUserByUnionId(AppThirdPartUser appThirdPartUser);
	
	//根据openid和unionid修改用户第三方数据
	public int updateByUnionId(AppThirdPartUser appThirdPartUser);
	
	//根据openId获取工会工会用户信息
	public AppThirdPartUser getLuUserByOpenId(AppThirdPartUser appThirdPartUser);
	
	//根据用户主id获取第三方用户信息
	public AppThirdPartUser getUserInfoById(AppThirdPartUser appThirdPartUser);
	
	//根据主用户手机号获取第三方用户的信息
	public List<AppThirdPartUser> getByPhone(AppThirdPartUser appThirdPartUser);
	

	//根据id修改手机号
	public int updateById(AppThirdPartUser appThirdPartUser);
	
	public int updateImg(AppThirdPartUser appThirdPartUser);
	
	public AppThirdPartUser getPhone(AppThirdPartUser appThirdPartUser);
}
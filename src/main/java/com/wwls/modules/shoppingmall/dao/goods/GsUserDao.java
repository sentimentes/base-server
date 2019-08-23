package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsUser;

/**
 * 用户管理DAO接口
 * @author leixiaoming
 * @version 2019-04-10
 */
@MyBatisDao
public interface GsUserDao extends CrudDao<GsUser> {
	void upPassword(GsUser gsUser);
	void answerSave(GsUser gsUser);
	
}
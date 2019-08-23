package com.wwls.modules.application.dao;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.application.entity.CommonMenu;
 
/**
 * 应用菜单DAO接口
 * @author mengyanan
 * @version 2016-06-24
 */
@MyBatisDao
public interface CommonMenuDao extends CrudDao<CommonMenu> {
	/***
	 * 查找尚未配的菜单列表
	 ***/
	public List<CommonMenu> findNotYetList(CommonMenu commonMenu);
	/***
	 * @author xudongdong
	 * @version 2016-07-02
	 * 查找应用对应的菜单列表
	 * */
	public List<CommonMenu> findAppMenuList(CommonMenu commonMenu);
}
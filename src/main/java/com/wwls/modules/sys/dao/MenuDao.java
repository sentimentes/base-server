package com.wwls.modules.sys.dao;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.Menu;

/**
 * 菜单DAO接口
 * @version 2014-05-16
 */
@MyBatisDao
public interface MenuDao extends CrudDao<Menu> {

	public List<Menu> findByParentIdsLike(Menu menu);

	public List<Menu> findByUserId(Menu menu);
	
	public int updateParentIds(Menu menu);
	
	public int updateSort(Menu menu);
	
	/**
	 * 按照父级id获取子菜单列表
	 * @param menu
	 * @return
	 */
	public List<Menu> findByParentId(Menu menu);
	
}

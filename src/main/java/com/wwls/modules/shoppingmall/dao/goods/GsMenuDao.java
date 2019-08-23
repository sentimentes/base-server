package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;

/**
 * 商品菜单管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsMenuDao extends CrudDao<GsMenu> {
	/** 批量上下架 */
	void upDownShelf(GsMenu gsMenu);

	/** 批量修改排序 */
	void updateSort(GsMenu gsMenu);
}
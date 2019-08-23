package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;

/**
 * 商品分类细分管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsMenuSmallDao extends CrudDao<GsMenuSmall> {
	/** 批量上下架 */
	void upDownShelf(GsMenuSmall gsMenuSmall);

	/** 批量修改排序 */
	void updateSort(GsMenuSmall gsMenuSmall);
}
package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuValue;

/**
 * 商品分类细分值管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsMenuValueDao extends CrudDao<GsMenuValue> {
	/** 批量上下架 */
	void upDownShelf(GsMenuValue gsMenuValue);

	/** 批量修改排序 */
	void updateSort(GsMenuValue gsMenuValue);
}
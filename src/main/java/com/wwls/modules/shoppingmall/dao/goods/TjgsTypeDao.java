package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsActivity;
import com.wwls.modules.shoppingmall.entity.goods.TjgsType;

/**
 * 特价商品类型管理DAO接口
 * @author leixiaoming
 * @version 2019-04-01
 */
@MyBatisDao
public interface TjgsTypeDao extends CrudDao<TjgsType> {
	/** 批量上下架 */
	void upDownShelf(TjgsType tjgsType);

	/** 批量修改排序 */
	void updateSort(TjgsType tjgsType);
}
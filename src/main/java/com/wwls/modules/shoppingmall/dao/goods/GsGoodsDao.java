package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;

/**
 * 商品管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsGoodsDao extends CrudDao<GsGoods> {
	/** 批量上下架 */
	void upDownShelf(GsGoods gsGoods);

	/** 批量修改排序 */
	void updateSort(GsGoods gsGoods);
}
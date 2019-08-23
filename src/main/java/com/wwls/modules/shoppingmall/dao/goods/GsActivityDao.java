package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsActivity;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;

/**
 * 导航广告管理DAO接口
 * @author leixiaoming
 * @version 2019-03-29
 */
@MyBatisDao
public interface GsActivityDao extends CrudDao<GsActivity> {
	/** 批量上下架 */
	void upDownShelf(GsActivity gsActivity);

	/** 批量修改排序 */
	void updateSort(GsActivity gsActivity);
}
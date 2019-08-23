package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;

/**
 * 物流管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsLogisticsDao extends CrudDao<GsLogistics> {
	/** 批量上下架 */
	void upDownShelf(GsLogistics gsLogistics);

	/** 批量修改排序 */
	void updateSort(GsLogistics gsLogistics);
}
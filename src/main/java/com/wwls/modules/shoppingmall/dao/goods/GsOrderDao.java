package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsOrder;

/**
 * 订单管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsOrderDao extends CrudDao<GsOrder> {
	void updateStatus(GsOrder gsOrder);
	void takeh(GsOrder gsOrder);
	void shouh(GsOrder gsOrder);
	Integer  getjf(GsOrder gsOrder);
}
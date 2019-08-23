package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsShoppingCart;

/**
 * 购物车管理DAO接口
 * @author leixiaoming
 * @version 2019-03-15
 */
@MyBatisDao
public interface GsShoppingCartDao extends CrudDao<GsShoppingCart> {
	
}
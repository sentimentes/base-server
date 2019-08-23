package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsType;

/**
 * 商品分类管理DAO接口
 * @author leixiaoming
 * @version 2019-03-27
 */
@MyBatisDao
public interface GsTypeDao extends CrudDao<GsType> {
	
}
package com.wwls.modules.shoppingmall.dao.goods;

import java.util.List;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsTjgs;

/**
 * 特价商品绑定DAO接口
 * @author leixiaoming
 * @version 2019-04-01
 */
@MyBatisDao
public interface GsTjgsDao extends CrudDao<GsTjgs> {
	List<GsTjgs> getgslist(GsTjgs gsTjgs);
}
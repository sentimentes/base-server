package com.wwls.modules.shoppingmall.dao.goods;

import com.wwls.common.persistence.CrudDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.shoppingmall.entity.goods.GsDj;

/**
 * 积分等级管理DAO接口
 * @author 雷小明
 * @version 2019-05-06
 */
@MyBatisDao
public interface GsDjDao extends CrudDao<GsDj> {
	
}
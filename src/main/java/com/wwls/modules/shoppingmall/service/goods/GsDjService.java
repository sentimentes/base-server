package com.wwls.modules.shoppingmall.service.goods;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsDj;
import com.wwls.modules.shoppingmall.dao.goods.GsDjDao;

/**
 * 积分等级管理Service
 * @author 雷小明
 * @version 2019-05-06
 */
@Service
@Transactional(readOnly = true)
public class GsDjService extends CrudService<GsDjDao, GsDj> {

	public GsDj get(String id) {
		return super.get(id);
	}
	
	public List<GsDj> findList(GsDj gsDj) {
		return super.findList(gsDj);
	}
	
	public Page<GsDj> findPage(Page<GsDj> page, GsDj gsDj) {
		return super.findPage(page, gsDj);
	}
	
	@Transactional(readOnly = false)
	public void save(GsDj gsDj) {
		super.save(gsDj);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsDj gsDj) {
		super.delete(gsDj);
	}
	
}
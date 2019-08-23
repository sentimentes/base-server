package com.wwls.modules.shoppingmall.service.goods;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsType;
import com.wwls.modules.shoppingmall.dao.goods.GsTypeDao;

/**
 * 商品分类管理Service
 * @author leixiaoming
 * @version 2019-03-27
 */
@Service
@Transactional(readOnly = true)
public class GsTypeService extends CrudService<GsTypeDao, GsType> {

	public GsType get(String id) {
		return super.get(id);
	}
	
	public List<GsType> findList(GsType gsType) {
		return super.findList(gsType);
	}
	
	public List<GsType> findAllList(GsType gsType) {
		return dao.findAllList(gsType);
	}
	
	public Page<GsType> findPage(Page<GsType> page, GsType gsType) {
		return super.findPage(page, gsType);
	}
	
	@Transactional(readOnly = false)
	public void save(GsType gsType) {
		super.save(gsType);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsType gsType) {
		super.delete(gsType);
	}
	
}
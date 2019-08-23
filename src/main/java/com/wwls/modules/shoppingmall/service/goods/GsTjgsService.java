package com.wwls.modules.shoppingmall.service.goods;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsTjgs;
import com.wwls.modules.shoppingmall.dao.goods.GsTjgsDao;

/**
 * 特价商品绑定Service
 * @author leixiaoming
 * @version 2019-04-01
 */
@Service
@Transactional(readOnly = true)
public class GsTjgsService extends CrudService<GsTjgsDao, GsTjgs> {

	public GsTjgs get(String id) {
		return super.get(id);
	}
	
	public List<GsTjgs> getgslist(GsTjgs gsTjgs) {
		return dao.getgslist(gsTjgs);
	}
	public List<GsTjgs> findList(GsTjgs gsTjgs) {
		return super.findList(gsTjgs);
	}
	
	public Page<GsTjgs> findPage(Page<GsTjgs> page, GsTjgs gsTjgs) {
		return super.findPage(page, gsTjgs);
	}
	
	@Transactional(readOnly = false)
	public void save(GsTjgs gsTjgs) {
		super.save(gsTjgs);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsTjgs gsTjgs) {
		super.delete(gsTjgs);
	}
	
}
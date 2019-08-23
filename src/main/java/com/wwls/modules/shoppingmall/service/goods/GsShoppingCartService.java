package com.wwls.modules.shoppingmall.service.goods;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsShoppingCart;
import com.wwls.modules.shoppingmall.dao.goods.GsShoppingCartDao;

/**
 * 购物车管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsShoppingCartService extends CrudService<GsShoppingCartDao, GsShoppingCart> {

	public GsShoppingCart get(String id) {
		return super.get(id);
	}
	
	public List<GsShoppingCart> findList(GsShoppingCart gsShoppingCart) {
		return super.findList(gsShoppingCart);
	}
	
	public Page<GsShoppingCart> findPage(Page<GsShoppingCart> page, GsShoppingCart gsShoppingCart) {
		return super.findPage(page, gsShoppingCart);
	}
	
	@Transactional(readOnly = false)
	public void save(GsShoppingCart gsShoppingCart) {
		super.save(gsShoppingCart);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsShoppingCart gsShoppingCart) {
		super.delete(gsShoppingCart);
	}
	
}
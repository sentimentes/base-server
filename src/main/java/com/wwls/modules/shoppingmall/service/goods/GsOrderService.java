package com.wwls.modules.shoppingmall.service.goods;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsOrder;
import com.wwls.modules.shoppingmall.dao.goods.GsOrderDao;

/**
 * 订单管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsOrderService extends CrudService<GsOrderDao, GsOrder> {

	public GsOrder get(String id) {
		return super.get(id);
	}
	
	public List<GsOrder> findList(GsOrder gsOrder) {
		return super.findList(gsOrder);
	}
	
	public Integer getjf(GsOrder gsOrder) {
		return dao.getjf(gsOrder);
	}
	
	public Page<GsOrder> findPage(Page<GsOrder> page, GsOrder gsOrder) {
		return super.findPage(page, gsOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(GsOrder gsOrder) {
		super.save(gsOrder);
	}
	@Transactional(readOnly = false)
	public void takeh(GsOrder gsOrder) {
		dao.takeh(gsOrder);
	}
	@Transactional(readOnly = false)
	public void shouh(GsOrder gsOrder) {
		dao.shouh(gsOrder);
	}
	@Transactional(readOnly = false)
	public void add(GsOrder gsOrder) {
		dao.insert(gsOrder);
	}
	
	@Transactional(readOnly = false)
	public void updateStatus(GsOrder gsOrder) {
		dao.updateStatus(gsOrder);
	}
	
	
	@Transactional(readOnly = false)
	public void delete(GsOrder gsOrder) {
		super.delete(gsOrder);
	}
	
}
package com.wwls.modules.shoppingmall.service.goods;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsAddress;
import com.wwls.modules.shoppingmall.dao.goods.GsAddressDao;

/**
 * 会员地址管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsAddressService extends CrudService<GsAddressDao, GsAddress> {

	public GsAddress get(String id) {
		return super.get(id);
	}
	
	public List<GsAddress> findList(GsAddress gsAddress) {
		return super.findList(gsAddress);
	}
	
	public Page<GsAddress> findPage(Page<GsAddress> page, GsAddress gsAddress) {
		return super.findPage(page, gsAddress);
	}
	
	@Transactional(readOnly = false)
	public void save(GsAddress gsAddress) {
		super.save(gsAddress);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsAddress gsAddress) {
		super.delete(gsAddress);
	}
	
}
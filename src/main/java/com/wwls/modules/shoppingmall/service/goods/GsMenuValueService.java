package com.wwls.modules.shoppingmall.service.goods;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuValue;
import com.wwls.modules.shoppingmall.dao.goods.GsMenuValueDao;

/**
 * 商品分类细分值管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsMenuValueService extends CrudService<GsMenuValueDao, GsMenuValue> {

	public GsMenuValue get(String id) {
		return super.get(id);
	}
	
	public List<GsMenuValue> findList(GsMenuValue gsMenuValue) {
		return super.findList(gsMenuValue);
	}
	
	public List<GsMenuValue> findAllList(GsMenuValue gsMenuValue) {
		return dao.findAllList(gsMenuValue);
	}
	
	public Page<GsMenuValue> findPage(Page<GsMenuValue> page, GsMenuValue gsMenuValue) {
		return super.findPage(page, gsMenuValue);
	}
	
	@Transactional(readOnly = false)
	public void save(GsMenuValue gsMenuValue) {
		super.save(gsMenuValue);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsMenuValue gsMenuValue) {
		super.delete(gsMenuValue);
	}
	

	@Transactional(readOnly = false)
	public void upDownShelf(GsMenuValue gsMenuValue) {
		List<String> idList = gsMenuValue.getIdList();
		gsMenuValue.setUpdateDate(new Date());
		for(String id:idList){
			gsMenuValue.setId(id);
			GsMenuValue elCanteenAdvertising1 = dao.get(gsMenuValue);
			if("0".equals(elCanteenAdvertising1.getUpDownShelf())){
				elCanteenAdvertising1.setUpDownShelf("1");//将未上架的改为已上架
			}else{
				elCanteenAdvertising1.setUpDownShelf("0");//将已上架的改为未上架
			}
			dao.upDownShelf(elCanteenAdvertising1);
		}
	}

	/** 批量修改排序 */
	@Transactional(readOnly = false)
	public void updateSort(GsMenuValue gsMenuValue) {
		dao.updateSort(gsMenuValue);
	}
	
}
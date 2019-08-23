package com.wwls.modules.shoppingmall.service.goods;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.entity.goods.GsMenuSmall;
import com.wwls.modules.shoppingmall.dao.goods.GsMenuSmallDao;

/**
 * 商品分类细分管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsMenuSmallService extends CrudService<GsMenuSmallDao, GsMenuSmall> {

	public GsMenuSmall get(String id) {
		return super.get(id);
	}
	
	public List<GsMenuSmall> findList(GsMenuSmall gsMenuSmall) {
		return super.findList(gsMenuSmall);
	}
	public List<GsMenuSmall> findAllList(GsMenuSmall gsMenuSmall) {
		return dao.findAllList(gsMenuSmall);
	}
	public Page<GsMenuSmall> findPage(Page<GsMenuSmall> page, GsMenuSmall gsMenuSmall) {
		return super.findPage(page, gsMenuSmall);
	}
	
	@Transactional(readOnly = false)
	public void save(GsMenuSmall gsMenuSmall) {
		super.save(gsMenuSmall);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsMenuSmall gsMenuSmall) {
		super.delete(gsMenuSmall);
	}
	

	@Transactional(readOnly = false)
	public void upDownShelf(GsMenuSmall gsMenuSmall) {
		List<String> idList = gsMenuSmall.getIdList();
		gsMenuSmall.setUpdateDate(new Date());
		for(String id:idList){
			gsMenuSmall.setId(id);
			GsMenuSmall elCanteenAdvertising1 = dao.get(gsMenuSmall);
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
	public void updateSort(GsMenuSmall gsMenuSmall) {
		dao.updateSort(gsMenuSmall);
	}
	
}
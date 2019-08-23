package com.wwls.modules.shoppingmall.service.goods;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;
import com.wwls.modules.shoppingmall.entity.goods.GsMenu;
import com.wwls.modules.shoppingmall.dao.goods.GsMenuDao;

/**
 * 商品菜单管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsMenuService extends CrudService<GsMenuDao, GsMenu> {

	public GsMenu get(String id) {
		return super.get(id);
	}
	
	public List<GsMenu> findList(GsMenu gsMenu) {
		return super.findList(gsMenu);
	}
	
	public List<GsMenu> findAllList(GsMenu gsMenu) {
		return dao.findAllList(gsMenu);
	}
	
	public Page<GsMenu> findPage(Page<GsMenu> page, GsMenu gsMenu) {
		return super.findPage(page, gsMenu);
	}
	
	@Transactional(readOnly = false)
	public void save(GsMenu gsMenu) {
		super.save(gsMenu);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsMenu gsMenu) {
		super.delete(gsMenu);
	}
	
	@Transactional(readOnly = false)
	public void upDownShelf(GsMenu gsMenu) {
		List<String> idList = gsMenu.getIdList();
		gsMenu.setUpdateDate(new Date());
		for(String id:idList){
			gsMenu.setId(id);
			GsMenu elCanteenAdvertising1 = dao.get(gsMenu);
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
	public void updateSort(GsMenu gsMenu) {
		dao.updateSort(gsMenu);
	}
	
}
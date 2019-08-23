package com.wwls.modules.shoppingmall.service.goods;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.entity.goods.GsLogistics;
import com.wwls.modules.shoppingmall.dao.goods.GsLogisticsDao;

/**
 * 物流管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsLogisticsService extends CrudService<GsLogisticsDao, GsLogistics> {

	public GsLogistics get(String id) {
		return super.get(id);
	}
	
	public List<GsLogistics> findList(GsLogistics gsLogistics) {
		return super.findList(gsLogistics);
	}
	
	public List<GsLogistics> findAllList(GsLogistics gsLogistics) {
		return dao.findAllList(gsLogistics);
	}
	
	public Page<GsLogistics> findPage(Page<GsLogistics> page, GsLogistics gsLogistics) {
		return super.findPage(page, gsLogistics);
	}
	
	@Transactional(readOnly = false)
	public void save(GsLogistics gsLogistics) {
		super.save(gsLogistics);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsLogistics gsLogistics) {
		super.delete(gsLogistics);
	}
	
	
	@Transactional(readOnly = false)
	public void upDownShelf(GsLogistics gsLogistics) {
		List<String> idList = gsLogistics.getIdList();
		gsLogistics.setUpdateDate(new Date());
		for(String id:idList){
			gsLogistics.setId(id);
			GsLogistics elCanteenAdvertising1 = dao.get(gsLogistics);
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
	public void updateSort(GsLogistics gsLogistics) {
		dao.updateSort(gsLogistics);
	}
	
}
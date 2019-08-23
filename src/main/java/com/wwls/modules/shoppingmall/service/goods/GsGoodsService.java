package com.wwls.modules.shoppingmall.service.goods;

import java.io.FileOutputStream;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.common.utils.FileUtils;
import com.wwls.modules.shoppingmall.entity.goods.GsActivity;
import com.wwls.modules.shoppingmall.entity.goods.GsGoods;
import com.wwls.modules.shoppingmall.dao.goods.GsGoodsDao;

/**
 * 商品管理Service
 * @author leixiaoming
 * @version 2019-03-15
 */
@Service
@Transactional(readOnly = true)
public class GsGoodsService extends CrudService<GsGoodsDao, GsGoods> {

	public GsGoods get(String id) {
		return super.get(id);
	}
	
	public List<GsGoods> findList(GsGoods gsGoods) {
		return super.findList(gsGoods);
	}
	
	public List<GsGoods> findAllList(GsGoods gsGoods) {
		return dao.findAllList(gsGoods);
	}
	
	public Page<GsGoods> findPage(Page<GsGoods> page, GsGoods gsGoods) {
		return super.findPage(page, gsGoods);
	}
	
	@Transactional(readOnly = false)
	public void save(GsGoods gsGoods) {
		MultipartFile minImageFile= gsGoods.getImageTemp();
		if(!minImageFile.isEmpty()){
		String filename = FileUtils.getFileName(minImageFile);
		String basePath = Global.getConfig("wy.file.http.path");
		try {
			FileOutputStream fileOutputStream =new FileOutputStream(basePath+filename);
			IOUtils.copy(minImageFile.getInputStream(),fileOutputStream);
			gsGoods.setImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		super.save(gsGoods);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsGoods gsGoods) {
		super.delete(gsGoods);
	}
	
	@Transactional(readOnly = false)
	public void upDownShelf(GsGoods gsGoods) {
		List<String> idList = gsGoods.getIdList();
		gsGoods.setUpdateDate(new Date());
		for(String id:idList){
			gsGoods.setId(id);
			GsGoods elCanteenAdvertising1 = dao.get(gsGoods);
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
	public void updateSort(GsGoods gsGoods) {
		dao.updateSort(gsGoods);
	}
	
}
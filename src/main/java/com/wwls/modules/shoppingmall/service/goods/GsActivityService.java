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
import com.wwls.modules.shoppingmall.dao.goods.GsActivityDao;

/**
 * 导航广告管理Service
 * @author leixiaoming
 * @version 2019-03-29
 */
@Service
@Transactional(readOnly = true)
public class GsActivityService extends CrudService<GsActivityDao, GsActivity> {

	public GsActivity get(String id) {
		return super.get(id);
	}
	
	
	public List<GsActivity> findList(GsActivity gsActivity) {
		return super.findList(gsActivity);
	}
	
	public List<GsActivity> findAllList(GsActivity gsActivity) {
		return dao.findAllList(gsActivity);
	}
	
	public Page<GsActivity> findPage(Page<GsActivity> page, GsActivity gsActivity) {
		return super.findPage(page, gsActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(GsActivity gsActivity) {
		MultipartFile minImageFile= gsActivity.getImageTemp();
		if(!minImageFile.isEmpty()){
		String filename = FileUtils.getFileName(minImageFile);
		String basePath = Global.getConfig("wy.file.http.path");
		try {
			FileOutputStream fileOutputStream =new FileOutputStream(basePath+filename);
			IOUtils.copy(minImageFile.getInputStream(),fileOutputStream);
			gsActivity.setImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		super.save(gsActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsActivity gsActivity) {
		super.delete(gsActivity);
	}
	
	@Transactional(readOnly = false)
	public void upDownShelf(GsActivity gsActivity) {
		List<String> idList = gsActivity.getIdList();
		gsActivity.setUpdateDate(new Date());
		for(String id:idList){
			gsActivity.setId(id);
			GsActivity elCanteenAdvertising1 = dao.get(gsActivity);
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
	public void updateSort(GsActivity gsActivity) {
		dao.updateSort(gsActivity);
	}
	
}
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
import com.wwls.modules.shoppingmall.entity.goods.GsNavigation;
import com.wwls.modules.shoppingmall.dao.goods.GsNavigationDao;

/**
 * 导航广告管理Service
 * @author leixiaoming
 * @version 2019-03-29
 */
@Service
@Transactional(readOnly = true)
public class GsNavigationService extends CrudService<GsNavigationDao, GsNavigation> {

	public GsNavigation get(String id) {
		return super.get(id);
	}
	public GsNavigation forms(GsNavigation gsNavigation) {
		return dao.forms(gsNavigation);
	}
	public List<GsNavigation> findList(GsNavigation gsNavigation) {
		return super.findList(gsNavigation);
	}
	public List<GsNavigation> findAllList(GsNavigation gsNavigation) {
		return dao.findAllList(gsNavigation);
	}
	
	public Page<GsNavigation> findPage(Page<GsNavigation> page, GsNavigation gsNavigation) {
		return super.findPage(page, gsNavigation);
	}
	
	@Transactional(readOnly = false)
	public void save(GsNavigation gsNavigation) {
		MultipartFile minImageFile= gsNavigation.getImageTemp();
		if(!minImageFile.isEmpty()){
		String filename = FileUtils.getFileName(minImageFile);
		String basePath = Global.getConfig("wy.file.http.path");
		try {
			FileOutputStream fileOutputStream =new FileOutputStream(basePath+filename);
			IOUtils.copy(minImageFile.getInputStream(),fileOutputStream);
			gsNavigation.setImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		super.save(gsNavigation);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsNavigation gsNavigation) {
		super.delete(gsNavigation);
	}
	
	@Transactional(readOnly = false)
	public void upDownShelf(GsNavigation gsNavigation) {
		List<String> idList = gsNavigation.getIdList();
		gsNavigation.setUpdateDate(new Date());
		for(String id:idList){
			gsNavigation.setId(id);
			GsNavigation elCanteenAdvertising1 = dao.get(gsNavigation);
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
	public void updateSort(GsNavigation gsNavigation) {
		dao.updateSort(gsNavigation);
	}
	
	
}
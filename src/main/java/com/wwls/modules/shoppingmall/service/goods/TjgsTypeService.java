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
import com.wwls.modules.shoppingmall.entity.goods.TjgsType;
import com.wwls.modules.shoppingmall.dao.goods.TjgsTypeDao;

/**
 * 特价商品类型管理Service
 * @author leixiaoming
 * @version 2019-04-01
 */
@Service
@Transactional(readOnly = true)
public class TjgsTypeService extends CrudService<TjgsTypeDao, TjgsType> {

	public TjgsType get(String id) {
		return super.get(id);
	}
	
	public List<TjgsType> findList(TjgsType tjgsType) {
		return super.findList(tjgsType);
	}
	
	public List<TjgsType> findAllList(TjgsType tjgsType) {
		return dao.findAllList(tjgsType);
	}
	
	public Page<TjgsType> findPage(Page<TjgsType> page, TjgsType tjgsType) {
		return super.findPage(page, tjgsType);
	}
	
	@Transactional(readOnly = false)
	public void save(TjgsType tjgsType) {
		MultipartFile minImageFile= tjgsType.getImageTemp();
		if(!minImageFile.isEmpty()){
		String filename = FileUtils.getFileName(minImageFile);
		String basePath = Global.getConfig("wy.file.http.path");
		try {
			FileOutputStream fileOutputStream =new FileOutputStream(basePath+filename);
			IOUtils.copy(minImageFile.getInputStream(),fileOutputStream);
			tjgsType.setImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		super.save(tjgsType);
	}
	
	@Transactional(readOnly = false)
	public void delete(TjgsType tjgsType) {
		super.delete(tjgsType);
	}
	@Transactional(readOnly = false)
	public void upDownShelf(TjgsType tjgsType) {
		List<String> idList = tjgsType.getIdList();
		tjgsType.setUpdateDate(new Date());
		for(String id:idList){
			tjgsType.setId(id);
			TjgsType elCanteenAdvertising1 = dao.get(tjgsType);
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
	public void updateSort(TjgsType tjgsType) {
		dao.updateSort(tjgsType);
	}
}
package com.wwls.modules.shoppingmall.service.goods;

import java.io.FileOutputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.common.config.Global;
import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.common.utils.FileUtils;
import com.wwls.modules.shoppingmall.entity.goods.GsUser;
import com.wwls.modules.shoppingmall.dao.goods.GsUserDao;

/**
 * 用户管理Service
 * @author leixiaoming
 * @version 2019-04-10
 */
@Service
@Transactional(readOnly = true)
public class GsUserService extends CrudService<GsUserDao, GsUser> {

	public GsUser get(String id) {
		return super.get(id);
	}
	
	public List<GsUser> findList(GsUser gsUser) {
		return super.findList(gsUser);
	}
	
	public Page<GsUser> findPage(Page<GsUser> page, GsUser gsUser) {
		return super.findPage(page, gsUser);
	}
	
	@Transactional(readOnly = false)
	public void upPassword(GsUser gsUser) {
		 dao.upPassword(gsUser);
	}
	@Transactional(readOnly = false)
	public void answerSave(GsUser gsUser) {
		 dao.answerSave(gsUser);
	}
	
	@Transactional(readOnly = false)
	public void save(GsUser gsUser) {
		MultipartFile minImageFile= gsUser.getImageTemp();
		if(!minImageFile.isEmpty()){
		String filename = FileUtils.getFileName(minImageFile);
		String basePath = Global.getConfig("wy.file.http.path");
		try {
			FileOutputStream fileOutputStream =new FileOutputStream(basePath+filename);
			IOUtils.copy(minImageFile.getInputStream(),fileOutputStream);
			gsUser.setImage(filename);
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
		super.save(gsUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(GsUser gsUser) {
		super.delete(gsUser);
	}
	
}
package com.wwls.modules.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.persistence.Page;
import com.wwls.common.service.CrudService;
import com.wwls.modules.application.entity.CommonAppInfo;
import com.wwls.modules.application.dao.CommonAppInfoDao;

/**
 * 应用信息Service
 * @author mengyanan
 * @version 2016-06-24
 */
@Service
@Transactional(readOnly = true)
public class CommonAppInfoService extends CrudService<CommonAppInfoDao, CommonAppInfo> {

	public CommonAppInfo get(String id) {
		return super.get(id);
	}
	
	public List<CommonAppInfo> findList(CommonAppInfo commonAppInfo) {
		return super.findList(commonAppInfo);
	}
	
	public Page<CommonAppInfo> findPage(Page<CommonAppInfo> page, CommonAppInfo commonAppInfo) {
		return super.findPage(page, commonAppInfo);
	}
	
	@Transactional(readOnly = false)
	public void save(CommonAppInfo commonAppInfo) {
		super.save(commonAppInfo);
	}
	
	@Transactional(readOnly = false)
	public void delete(CommonAppInfo commonAppInfo) {
		super.delete(commonAppInfo);
	}
	
}
package com.wwls.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wwls.common.service.TreeService;
import com.wwls.modules.sys.dao.AreaDao;
import com.wwls.modules.sys.entity.Area;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 区域Service
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class AreaService extends TreeService<AreaDao, Area> {

	public List<Area> findAll(){
		return UserUtils.getAreaList();
	}
	
	public List<Area> findPhoneAll(Area area){
		return dao.findPhoneAll(area);
	}
	
	@Transactional(readOnly = false)
	public void save(Area area) {
		super.save(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Area area) {
		super.delete(area);
		UserUtils.removeCache(UserUtils.CACHE_AREA_LIST);
	}
	
}

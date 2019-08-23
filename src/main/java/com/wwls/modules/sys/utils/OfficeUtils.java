package com.wwls.modules.sys.utils;

import java.util.List;

import com.wwls.common.utils.SpringContextHolder;
import com.wwls.modules.sys.dao.OfficeDao;
import com.wwls.modules.sys.entity.Office;

/***
 * 工具类
 * */
public class OfficeUtils {
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);
    
	/**
     * @author xudongdong
     * @version 2016-06-13
     * @desc 获取某个公司下面的子公司
     */
	public static List<Office> getCompanyList(String id){
	 Office office  = new Office();	
	 Office parent = new Office();
	 parent.setId(id);
	 office.setParent(parent);;
		return officeDao.getOfficeList(office);
	}
	
	
	 /**
     * @author xudongdong
     * @version 2016-06-13
     * @desc 获取某个公司下面的部门列表
     */
	public static List<Office> getDeparmentList(){
	 Office office  = new Office();	
 	 office.setParent(UserUtils.getUser().getOffice());
		return officeDao.getOfficeList(office);
	}

}

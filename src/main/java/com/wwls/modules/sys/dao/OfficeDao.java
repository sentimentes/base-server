package com.wwls.modules.sys.dao;

import java.util.List;

import com.wwls.common.persistence.TreeDao;
import com.wwls.common.persistence.annotation.MyBatisDao;
import com.wwls.modules.sys.entity.Office;

/**
 * 机构DAO接口
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {
	
	/***
	 * 只取当前机构信息
	 * ***/
	public Office getById(String id);
	
	public List<Office> getOfficeList(Office office);
	
	/**
	 * 插入数据
	 * @param entity
	 * @return
	 */
	public int insertInfo(Office office);
	
	/**
	 * 内容提供商列表
	 * @param office
	 * @return
	 */
	public List<Office> findResultList(Office office);
	
	/**
	 * 内容提供商列表
	 * @param office
	 * @return
	 */
	public List<Office> findCoList(Office office);
	
	
	/**
	 * 获取code的最大值
	 * @author hugang
	 * @version 2016-09-23 AM 9:47
	 */
	public Office getMaxCode(Office office);
	
	
	/**
	 * 新增获取office集合，用于资讯和商品存入缓存
	 * @return
	 */
	public List<Office> findOfficeList();
	
	
	/**
	 * 根据机构名称获取机构信息
	 * @param id
	 * @return
	 */
	public Office getByName(Office office);
	
	
	/**
	 * 获取第二级部门后的数据
	 * @return
	 */
	public List<Office> pbDepartmentList(Office office);
	
	/**
	 * 根据机构id获取其绑定的部门
	 * @return
	 */
	public List<Office> findAllName(Office office);
	
	
	/**
	 * 根据id物理删除部门id
	 * @param office
	 * @return
	 */
	public int physicsDelete(Office office);
	
	/**
	 * 获取最大的排序数字
	 */
	public  List<Office> getMaxSort(Office office);
	
	/**
	 * 获取第二级部门后的数据
	 * @return
	 */
	public List<Office> getByOfficeList(Office office);
	
	public List<Office> findAllOfficeList(Office office);
	
}

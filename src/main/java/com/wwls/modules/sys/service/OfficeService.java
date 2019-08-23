package com.wwls.modules.sys.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.wwls.common.persistence.Page;
import com.wwls.common.service.TreeService;
import com.wwls.modules.sys.dao.OfficeDao;
import com.wwls.modules.sys.entity.Office;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {
	
	
	public Office getById(String id){
		
		return dao.getById(id);
	}

	
	public List<Office> findAll(){
		return UserUtils.getOfficeList();
	}

	public List<Office> findList(Boolean isAll){
		if (isAll != null && isAll){
			return UserUtils.getOfficeAllList();
		}else{
			return UserUtils.getOfficeList();
		}
	}
	
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		logger.debug("获取的机构信息是=="+office);
		
		if(office != null){
			logger.debug("获取的机构详情是==="+(office != null));
			office.setParentIds(office.getParentIds()+"%");
			List<Office> officeList = dao.findByParentIdsLike(office);
			logger.debug("最终获取的列表是=="+officeList);
			return officeList;
		}
		return  new ArrayList<Office>();
	}
	
	@Transactional(readOnly = false)
	public void save(Office office) {
		super.save(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	@Transactional(readOnly = false)
	public void delete(Office office) {
		super.delete(office);
		UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}
	
	/**
	 * 内容提供商列表
	 */
	public Page<Office> findPage(Page<Office> page, Office office) {
		office.setPage(page);
		// 执行分页查询
		page.setList(dao.findResultList(office));
		return page;
	}
	
	/**
	 * 内容提供商列表
	 */
	public Page<Office> findCoPage(Page<Office> page, Office office) {
		office.setPage(page);
		// 执行分页查询
		page.setList(dao.findCoList(office));
		return page;
	}
	/**
	 * 新增获取office集合，用于资讯和商品存入缓存
	 * @return
	 */
	public List<Office> findOfficeList(){
		return dao.findOfficeList();
	}
	
	/**
	 * 根据机构名称获取机构信息
	 * @param id
	 * @return
	 */
	public Office getByName(Office office) {
		return dao.getByName(office);
	}
	
	
	/**
	 * 根据机构id获取到机构所对应的父级id，根据父级id查询出来所有的自己id
	 * @return
	 */
	public List<Office> getThreeList(Office office){
		office.setId(office.getId());
		Office office1 = dao.get(office);
		Office office2 = new Office();
		office2.setParent(new Office(office1.getParentId()));
		return dao.getOfficeList(office2);
	}
	
	
	/**
	 * 获取第二级部门后的数据
	 * @return
	 */
	public List<Office> pbDepartmentList(Office office){
		if(office != null){
			logger.debug("获取的机构详情是==="+(office != null));
			office.setParent(UserUtils.getUser().getOffice());
			office.setParentIds(UserUtils.getUser().getOffice().getId()+",");
			List<Office> officeList = dao.pbDepartmentList(office);
			logger.debug("最终获取的列表是=="+officeList);
			return officeList;
		}
		return  new ArrayList<Office>();
	}
	
	
	/**
	 * 根据机构id获取其绑定的部门
	 * @return
	 */
	public List<Office> findAllName(Office office){
		return dao.findAllName(office);
	}
	
	
	/**
	 * 获取最大的排序数字
	 */
	public Office getMaxSort(Office office) {
		return dao.getMaxSort(office).get(0);
	}
	
	
	public List<Office> findOfficeList(Office office){
		
		List<Office> list = new ArrayList<Office>();
		List<Office> list1 = dao.findAllOfficeList(office);
		String str1="";
		String str2=",";
		int i=0;
		if(list1!=null && list1.size()>0){
			for(Office category:list1){
				str1=category.getParentIds();
				i=str1.length()-str1.replace(str2, "").length();
				if((i/str2.length())==3){
					list.add(category);
				}
			}
		}
		return list;
	}
	
}

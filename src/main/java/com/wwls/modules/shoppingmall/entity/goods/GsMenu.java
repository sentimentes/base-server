package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 商品菜单管理Entity
 * @author leixiaoming
 * @version 2019-03-15
 */
public class GsMenu extends DataEntity<GsMenu> {
	
	private static final long serialVersionUID = 1L;
	private String spType1;		// 一级分类
	private String spType2;		// 二级分类
	private Office office;		// 机构id
	private String sort;		// 排序
	private String upDownShelf;		// 上下架
	private List<String> idList;
	
	
	
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public GsMenu() {
		super();
	}

	public GsMenu(String id){
		super(id);
	}

	@Length(min=0, max=255, message="一级分类长度必须介于 0 和 255 之间")
	public String getSpType1() {
		return spType1;
	}

	public void setSpType1(String spType1) {
		this.spType1 = spType1;
	}
	
	@Length(min=0, max=255, message="二级分类长度必须介于 0 和 255 之间")
	public String getSpType2() {
		return spType2;
	}

	public void setSpType2(String spType2) {
		this.spType2 = spType2;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=10, message="上下架长度必须介于 0 和 10 之间")
	public String getUpDownShelf() {
		return upDownShelf;
	}

	public void setUpDownShelf(String upDownShelf) {
		this.upDownShelf = upDownShelf;
	}
	
}
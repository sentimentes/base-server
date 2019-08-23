package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 商品分类细分值管理Entity
 * @author leixiaoming
 * @version 2019-03-15
 */
public class GsMenuValue extends DataEntity<GsMenuValue> {
	
	private static final long serialVersionUID = 1L;
	private String gsMenuSmallId;	
	private String gsMenuSmallName;
	private String gsMenuId;// 细分表id
	private String gsMenuName;// 细分表id
	private String name;		// 名字
	private Office office;		// 机构id
	private String sort;		// 排序
	private String upDownShelf;		// 上下架
	private List<String> idList;
	private String gsId;		// 排序
	public String getGsId() {
		return gsId;
	}

	public void setGsId(String gsId) {
		this.gsId = gsId;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getGsMenuSmallName() {
		return gsMenuSmallName;
	}

	public void setGsMenuSmallName(String gsMenuSmallName) {
		this.gsMenuSmallName = gsMenuSmallName;
	}

	public String getGsMenuName() {
		return gsMenuName;
	}

	public void setGsMenuName(String gsMenuName) {
		this.gsMenuName = gsMenuName;
	}

	public String getGsMenuId() {
		return gsMenuId;
	}

	public void setGsMenuId(String gsMenuId) {
		this.gsMenuId = gsMenuId;
	}

	public GsMenuValue() {
		super();
	}

	public GsMenuValue(String id){
		super(id);
	}

	@Length(min=0, max=255, message="细分表id长度必须介于 0 和 255 之间")
	public String getGsMenuSmallId() {
		return gsMenuSmallId;
	}

	public void setGsMenuSmallId(String gsMenuSmallId) {
		this.gsMenuSmallId = gsMenuSmallId;
	}
	
	@Length(min=0, max=255, message="名字长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	@Length(min=0, max=255, message="上下架长度必须介于 0 和 255 之间")
	public String getUpDownShelf() {
		return upDownShelf;
	}

	public void setUpDownShelf(String upDownShelf) {
		this.upDownShelf = upDownShelf;
	}
	
}
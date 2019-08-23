package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 会员地址管理Entity
 * @author leixiaoming
 * @version 2019-03-15
 */
public class GsAddress extends DataEntity<GsAddress> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 地址
	private Office office;		// 机构id
	private String upDownShelf;		// 
	private String sort;		// 排序
	private String phone;
	private String pepole;
	private List<String> idList;
	
	

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPepole() {
		return pepole;
	}

	public void setPepole(String pepole) {
		this.pepole = pepole;
	}

	public GsAddress() {
		super();
	}

	public GsAddress(String id){
		super(id);
	}

	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="上下架长度必须介于 0 和 255 之间")
	public String getUpDownShelf() {
		return upDownShelf;
	}

	public void setUpDownShelf(String upDownShelf) {
		this.upDownShelf = upDownShelf;
	}
	
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
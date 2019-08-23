package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 购物车管理Entity
 * @author leixiaoming
 * @version 2019-03-15
 */
public class GsShoppingCart extends DataEntity<GsShoppingCart> {
	
	private static final long serialVersionUID = 1L;
	private String gsId;		// 商品id
	private Office office;		// 机构id
	private String upDownShelf;		// 上下架
	private String sort;		// 排序
	private String number;		//数量
	private String parameter;  //参数
	private String  createBys;
	private String gsName;
	private String image;
	private String price;		// 原价
	private String salePrice;		// 促销价
	private List<String> idList;
	
	
	
	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public String getGsName() {
		return gsName;
	}

	public void setGsName(String gsName) {
		this.gsName = gsName;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public String getCreateBys() {
		return createBys;
	}

	public void setCreateBys(String createBys) {
		this.createBys = createBys;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public GsShoppingCart() {
		super();
	}

	public GsShoppingCart(String id){
		super(id);
	}

	@Length(min=0, max=255, message="商品id长度必须介于 0 和 255 之间")
	public String getGsId() {
		return gsId;
	}

	public void setGsId(String gsId) {
		this.gsId = gsId;
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
	
	@Length(min=0, max=255, message="排序长度必须介于 0 和 255 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
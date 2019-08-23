package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 订单管理Entity
 * @author leixiaoming
 * @version 2019-03-15
 */
public class GsOrder extends DataEntity<GsOrder> {
	
	private static final long serialVersionUID = 1L;
	private String gsId;		// 商品id
	private String gsName;		// 商品名称
	private String number;       //数量
	private String gsNumber;       //数量
	private String addressName;		// 地址
	private String logisticsName;		// 物流名字
	private String price;		// 价格
	private Office office;		// 机构id
	private String upDownShelf;		// 上下架
	private String sort;		// 排序
	private String phone;
	private String pepole;
	private String addressId;
	private List<GsShoppingCart> gsShoppingCartList;
	private String parameter;  //参数
	private String orderId;
	private String tradeNo;
	private String image;
	
	
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public List<GsShoppingCart> getGsShoppingCartList() {
		return gsShoppingCartList;
	}

	public void setGsShoppingCartList(List<GsShoppingCart> gsShoppingCartList) {
		this.gsShoppingCartList = gsShoppingCartList;
	}

	public String getGsNumber() {
		return gsNumber;
	}

	public void setGsNumber(String gsNumber) {
		this.gsNumber = gsNumber;
	}

	public String getAddressId() {
		return addressId;
	}

	public void setAddressId(String addressId) {
		this.addressId = addressId;
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

	public String getGsName() {
		return gsName;
	}

	public void setGsName(String gsName) {
		this.gsName = gsName;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public GsOrder() {
		super();
	}

	public GsOrder(String id){
		super(id);
	}

	@Length(min=0, max=255, message="商品id长度必须介于 0 和 255 之间")
	public String getGsId() {
		return gsId;
	}

	public void setGsId(String gsId) {
		this.gsId = gsId;
	}
	
	@Length(min=0, max=255, message="地址长度必须介于 0 和 255 之间")
	public String getAddressName() {
		return addressName;
	}

	public void setAddressName(String addressName) {
		this.addressName = addressName;
	}
	
	@Length(min=0, max=255, message="物流名字长度必须介于 0 和 255 之间")
	public String getLogisticsName() {
		return logisticsName;
	}

	public void setLogisticsName(String logisticsName) {
		this.logisticsName = logisticsName;
	}
	
	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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
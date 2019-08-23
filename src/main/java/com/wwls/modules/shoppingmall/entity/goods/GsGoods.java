package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 商品管理Entity
 * @author leixiaoming
 * @version 2019-03-15
 */
public class GsGoods extends DataEntity<GsGoods> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名字
	private String image;		// 图片
	private String price;		// 原价
	private String salePrice;		// 促销价
	private String prefeentialInfo;		// 优惠信息
	private String salesVolume;		// 销量
	private String number;		// 数量
	private String details;		// 详情
	private Office office;		// 机构id
	private String upDownShelf;		// 上下架
	private String sort;		// 排序
	private MultipartFile imageTemp;//图片的临时字段
	private List<String> idList;
	private String menuId;
	private String menuName;
	
	
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public MultipartFile getImageTemp() {
		return imageTemp;
	}

	public void setImageTemp(MultipartFile imageTemp) {
		this.imageTemp = imageTemp;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public GsGoods() {
		super();
	}

	public GsGoods(String id){
		super(id);
	}

	@Length(min=0, max=255, message="名字长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="优惠信息长度必须介于 0 和 255 之间")
	public String getPrefeentialInfo() {
		return prefeentialInfo;
	}

	public void setPrefeentialInfo(String prefeentialInfo) {
		this.prefeentialInfo = prefeentialInfo;
	}
	
	@Length(min=0, max=255, message="销量长度必须介于 0 和 255 之间")
	public String getSalesVolume() {
		return salesVolume;
	}

	public void setSalesVolume(String salesVolume) {
		this.salesVolume = salesVolume;
	}
	
	@Length(min=0, max=255, message="数量长度必须介于 0 和 255 之间")
	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	@Length(min=0, max=20480, message="详情长度必须介于 0 和 20480 之间")
	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
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
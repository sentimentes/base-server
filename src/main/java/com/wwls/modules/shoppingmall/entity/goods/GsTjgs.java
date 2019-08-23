package com.wwls.modules.shoppingmall.entity.goods;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 特价商品绑定Entity
 * @author leixiaoming
 * @version 2019-04-01
 */
public class GsTjgs extends DataEntity<GsTjgs> {
	
	private static final long serialVersionUID = 1L;
	private String gsId;		// gs_id
	private String gsName;		// gs_id
	private String image;
	private String price;
	private String salePrice;
	private String tjgsId;		// tjgs_id
	private Office office;		// office_id
	private String gsMenuId;
	
	
	public String getGsMenuId() {
		return gsMenuId;
	}

	public void setGsMenuId(String gsMenuId) {
		this.gsMenuId = gsMenuId;
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

	public String getGsName() {
		return gsName;
	}

	public void setGsName(String gsName) {
		this.gsName = gsName;
	}

	public GsTjgs() {
		super();
	}

	public GsTjgs(String id){
		super(id);
	}

	@Length(min=0, max=255, message="gs_id长度必须介于 0 和 255 之间")
	public String getGsId() {
		return gsId;
	}

	public void setGsId(String gsId) {
		this.gsId = gsId;
	}
	
	@Length(min=0, max=255, message="tjgs_id长度必须介于 0 和 255 之间")
	public String getTjgsId() {
		return tjgsId;
	}

	public void setTjgsId(String tjgsId) {
		this.tjgsId = tjgsId;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}
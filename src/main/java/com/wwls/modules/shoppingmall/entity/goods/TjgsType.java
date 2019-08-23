package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 特价商品类型管理Entity
 * @author leixiaoming
 * @version 2019-04-01
 */
public class TjgsType extends DataEntity<TjgsType> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// name
	private Office office;		// office_id
	private String upDownShelf;		// up_down_shelf
	private String sort;		// sort
	private List<String> idList;
	private String gsMenuId;
	private MultipartFile imageTemp;//图片的临时字段
	private String image;		// 图片
	
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public MultipartFile getImageTemp() {
		return imageTemp;
	}

	public void setImageTemp(MultipartFile imageTemp) {
		this.imageTemp = imageTemp;
	}

	public String getGsMenuId() {
		return gsMenuId;
	}

	public void setGsMenuId(String gsMenuId) {
		this.gsMenuId = gsMenuId;
	}

	public List<String> getIdList() {
		return idList;
	}

	public void setIdList(List<String> idList) {
		this.idList = idList;
	}

	public TjgsType() {
		super();
	}

	public TjgsType(String id){
		super(id);
	}

	@Length(min=0, max=255, message="name长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="up_down_shelf长度必须介于 0 和 255 之间")
	public String getUpDownShelf() {
		return upDownShelf;
	}

	public void setUpDownShelf(String upDownShelf) {
		this.upDownShelf = upDownShelf;
	}
	
	@Length(min=0, max=255, message="sort长度必须介于 0 和 255 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
}
package com.wwls.modules.shoppingmall.entity.goods;

import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 导航广告管理Entity
 * @author leixiaoming
 * @version 2019-03-29
 */
public class GsNavigation extends DataEntity<GsNavigation> {
	
	private static final long serialVersionUID = 1L;
	private String image;		// 图片
	private String gsContent;		// 详情
	private String name;		// 名称
	private Office office;		// 机构id
	private String upDownShelf;		// 上下架
	private String sort;		// 排序
	private String gsofficeId;		// 商城id
	private String gsofficeName;		// 商城id
	private MultipartFile imageTemp;//图片的临时字段
	private List<String> idList;
	
	
	
	public String getGsofficeName() {
		return gsofficeName;
	}

	public void setGsofficeName(String gsofficeName) {
		this.gsofficeName = gsofficeName;
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

	public GsNavigation() {
		super();
	}

	public GsNavigation(String id){
		super(id);
	}

	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Length(min=0, max=20000, message="详情长度必须介于 0 和 20000之间")
	public String getGsContent() {
		return gsContent;
	}

	public void setGsContent(String gsContent) {
		this.gsContent = gsContent;
	}
	
	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
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
	
	@Length(min=0, max=255, message="商城id长度必须介于 0 和 255 之间")
	public String getGsofficeId() {
		return gsofficeId;
	}

	public void setGsofficeId(String gsofficeId) {
		this.gsofficeId = gsofficeId;
	}
	
}
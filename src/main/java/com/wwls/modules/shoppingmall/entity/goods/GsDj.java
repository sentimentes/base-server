package com.wwls.modules.shoppingmall.entity.goods;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 积分等级管理Entity
 * @author 雷小明
 * @version 2019-05-06
 */
public class GsDj extends DataEntity<GsDj> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 等级名称
	private Integer start;		// 开始积分
	private Integer end;		// 结束积分
	private Office office;		// office_id
	private String upDownShelf;		// up_down_shelf
	private String zk;		// 折扣
	
	public GsDj() { 
		super();
	}

	public GsDj(String id){
		super(id);
	}

	@Length(min=0, max=255, message="等级名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="开始积分长度必须介于 0 和 255 之间")
	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	@Length(min=0, max=255, message="结束积分长度必须介于 0 和 255 之间")
	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
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
	
	@Length(min=0, max=255, message="折扣长度必须介于 0 和 255 之间")
	public String getZk() {
		return zk;
	}

	public void setZk(String zk) {
		this.zk = zk;
	}
	
}
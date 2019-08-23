package com.wwls.common.persistence;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wwls.common.utils.IdGen;
import com.wwls.common.utils.excel.annotation.ExcelField;
import com.wwls.modules.sys.entity.User;
import com.wwls.modules.sys.utils.JiaoYanUtils;
import com.wwls.modules.sys.utils.UserUtils;

/**
 * 数据Entity类
 * @version 2014-05-16
 */
public abstract class DataEntity<T> extends BaseEntity<T> {

	private static final long serialVersionUID = 1L;
	
	protected String remarks;	// 备注
	protected User createBy;	// 创建者
	protected Date createDate;	// 创建日期
	protected User updateBy;	// 更新者
	protected Date updateDate;	// 更新日期
	protected String delFlag; 	// 删除标记（0：正常；1：删除；2：审核）
	protected String _id;//MongoDB需要的id键值，不能重复
	public DataEntity() {
		super();
		this.delFlag = DEL_FLAG_NORMAL;
	}
	
	public DataEntity(String id) {
		super(id);
	}
	
	/**
	 * 插入之前执行方法，需要手动调用
	 */
	@Override
	public void preInsert(){
		// 不限制ID为UUID，调用setIsNewRecord()使用自定义ID
		if (!this.isNewRecord){
			setId(IdGen.uuid());
		}
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
			this.createBy = user;
		}
		this.updateDate = new Date();
		this.createDate = this.updateDate;
	}
	
	/**
	 * 更新之前执行方法，需要手动调用
	 */
	@Override
	public void preUpdate(){
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getId())){
			this.updateBy = user;
		}
		this.updateDate = new Date();
	}
	
	@Length(min=0, max=1000)
	public String getRemarks() {
		return remarks;
	}
	
    @JsonIgnore
	public void setRemarks(String remarks) {
		this.remarks = JiaoYanUtils.stringFilter(remarks);
	}
	
	
	public User getCreateBy() {
		return createBy;
	}
	 
	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="添加时间", align=2, sort=60)
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@JsonIgnore
	public User getUpdateBy() {
		return updateBy;
	}
	
	public void setUpdateBy(User updateBy) {
		this.updateBy = updateBy;
	}
	
	//@JsonFormat(pattern = "yyyy-MM-dd")
	public Date getUpdateDate() {
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Length(min=1, max=1)
	public String getDelFlag() {
		return delFlag;
	}
	
	public void setDelFlag(String delFlag) {
		this.delFlag = JiaoYanUtils.numFilter(delFlag);
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

}

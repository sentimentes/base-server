/**
 *
 */
package com.wwls.modules.application.entity.activation;

import org.hibernate.validator.constraints.Length;

import com.wwls.modules.sys.entity.Office;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wwls.common.persistence.DataEntity;

/**
 * 激活码管理Entity
 * @author hugang
 * @version 2016-09-22
 */
public class CommonActivationCode extends DataEntity<CommonActivationCode> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 批次名称
	private Office office;		// 机构ID
	private String userId;		// 用户主表的id
	private String categoryId;		// 商品分类id
	private String categoryName;		// 商品分类名称
	private String term;		// 期限
	private String isEffective;		// 是否激活：0未激活；1：已激活
	private Date validityPeriod;		// 有效期截止时间
	private String activitiCode;		// 激活码
	private String productId;		// 商品id
	private int batch;		// 生成批次,从1开始
	private Date activationDate;		// 激活时间
	private String officeId;//机构id
	private int number;//没批生成的激活码数量
	private Integer useTimes;//可使用次数
	private Integer alUseTimes;//已使用次数
	private String remarks;//备注 线下商户填写订单号
	private String phone;//手机号
	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getUseTimes() {
		return useTimes;
	}

	public Integer getAlUseTimes() {
		return alUseTimes;
	}

	public void setUseTimes(Integer useTimes) {
		this.useTimes = useTimes;
	}

	public void setAlUseTimes(Integer alUseTimes) {
		this.alUseTimes = alUseTimes;
	}

	public int getBatch() {
		return batch;
	}

	public void setBatch(int batch) {
		this.batch = batch;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public CommonActivationCode() {
		super();
	}

	public CommonActivationCode(String id){
		super(id);
	}

	@Length(min=0, max=100, message="批次名称长度必须介于 0 和 100 之间")
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
	
	@Length(min=0, max=64, message="用户主表的id长度必须介于 0 和 64 之间")
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Length(min=0, max=64, message="商品分类id长度必须介于 0 和 64 之间")
	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	@Length(min=0, max=150, message="商品分类名称长度必须介于 0 和 150 之间")
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	
	@Length(min=0, max=10, message="期限长度必须介于 0 和 10 之间")
	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}
	
	@Length(min=0, max=1, message="是否激活：0未激活；1：已激活长度必须介于 0 和 1 之间")
	public String getIsEffective() {
		return isEffective;
	}

	public void setIsEffective(String isEffective) {
		this.isEffective = isEffective;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(Date validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	
	@Length(min=0, max=50, message="激活码长度必须介于 0 和 50 之间")
	public String getActivitiCode() {
		return activitiCode;
	}

	public void setActivitiCode(String activitiCode) {
		this.activitiCode = activitiCode;
	}
	
	@Length(min=0, max=64, message="商品id长度必须介于 0 和 64 之间")
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getActivationDate() {
		return activationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}
	
}
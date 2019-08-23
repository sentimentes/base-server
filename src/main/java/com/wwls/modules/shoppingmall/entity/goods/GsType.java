package com.wwls.modules.shoppingmall.entity.goods;

import org.hibernate.validator.constraints.Length;
import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 商品分类管理Entity
 * @author leixiaoming
 * @version 2019-03-27
 */
public class GsType extends DataEntity<GsType> {
	
	private static final long serialVersionUID = 1L;
	private String gsId;		// gs_id
	private String gsName;
	private String vaId;		// va_id
	private String vaName;		// va_id
	private Office office;		// office_id
	private String smName;
	
	
	public String getSmName() {
		return smName;
	}

	public void setSmName(String smName) {
		this.smName = smName;
	}

	public String getVaName() {
		return vaName;
	}

	public void setVaName(String vaName) {
		this.vaName = vaName;
	}

	public String getGsName() {
		return gsName;
	}

	public void setGsName(String gsName) {
		this.gsName = gsName;
	}

	public GsType() {
		super();
	}

	public GsType(String id){
		super(id);
	}

	@Length(min=0, max=255, message="gs_id长度必须介于 0 和 255 之间")
	public String getGsId() {
		return gsId;
	}

	public void setGsId(String gsId) {
		this.gsId = gsId;
	}
	
	@Length(min=0, max=255, message="va_id长度必须介于 0 和 255 之间")
	public String getVaId() {
		return vaId;
	}

	public void setVaId(String vaId) {
		this.vaId = vaId;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}
package com.wwls.modules.application.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.modules.sys.entity.Office;
import com.wwls.common.persistence.DataEntity;

/**
 * app版本管理Entity
 * @author fanbo
 * @version 2016-07-01
 */
public class CommonAppVersion extends DataEntity<CommonAppVersion> {
	
	private static final long serialVersionUID = 1L;
	private String version;		// 版本号
	private String sort;		// 排序
	private String downUrl;		// app下载地址
	private String info;		// 描述信息
	private String appType;		// app类型（1：Android2：ios）
	private Office office;		// 机构id
	private String appId;		// appId
	private String appName;//app名称
	private MultipartFile downUrlTemp;//上传地址临时字段
	
	
	
	public MultipartFile getDownUrlTemp() {
		return downUrlTemp;
	}

	public void setDownUrlTemp(MultipartFile downUrlTemp) {
		this.downUrlTemp = downUrlTemp;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public CommonAppVersion() {
		super();
	}

	public CommonAppVersion(String id){
		super(id);
	}

	@Length(min=0, max=50, message="版本号长度必须介于 0 和 50 之间")
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
	
	@Length(min=0, max=10, message="排序长度必须介于 0 和 10 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=255, message="app下载地址长度必须介于 0 和 255 之间")
	public String getDownUrl() {
		return downUrl;
	}

	public void setDownUrl(String downUrl) {
		this.downUrl = downUrl;
	}
	
	@Length(min=0, max=500, message="描述信息长度必须介于 0 和 500 之间")
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
	@Length(min=0, max=1, message="app类型（1：Android2：ios）长度必须介于 0 和 1 之间")
	public String getAppType() {
		return appType;
	}

	public void setAppType(String appType) {
		this.appType = appType;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
	
}
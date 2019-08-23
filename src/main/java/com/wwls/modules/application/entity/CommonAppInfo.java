package com.wwls.modules.application.entity;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.common.persistence.DataEntity;
import com.wwls.modules.sys.entity.Office;

/**
 * 应用信息Entity
 * @author mengyanan
 * @version 2016-06-24
 */
public class CommonAppInfo extends DataEntity<CommonAppInfo> {
	
	private static final long serialVersionUID = 1L;
	private String secretKey;		// 键值
	private String keyVersion;		// 键值版本
	private String name;		// 应用名称
	private String shopId;		// sys_office的id
	private String clientId;		// 客户端的id
	private String appImage;		// app图片
	private String isIntegral;		// 是否启动积分机制（1是0否）
	private String companyName;//公司名称
	private Office office;//机构
	private String officeId;//机构Id
	private String bundleIdentifier;//app唯一标识
	private String msgClientid;//短信Id
	private String msgFirst;//短信前半部分
	private String msgTemplate;//短信模板Id
	private String msgLast;//短信后半部分
	private MultipartFile appTemp;	//图片临时存储files（SpringMVC）
    private String wxAppId;//微信appID 公众号，或者企业号
    private String wxAppSecret;//微信appSecret
    private String wxToken;//微信token缓存
    private String wxAesKey;//微信aesKey
    private String wxType;//微信公众号或者企业号
	private Integer wxAgentid;//微信企业号或者公众号的编号
	 

	public Integer getWxAgentid() {
		return wxAgentid;
	}

	public void setWxAgentid(Integer wxAgentid) {
		this.wxAgentid = wxAgentid;
	}

	public String getWxAppId() {
		return wxAppId;
	}

	public void setWxAppId(String wxAppId) {
		this.wxAppId = wxAppId;
	}

	public String getWxAppSecret() {
		return wxAppSecret;
	}

	public void setWxAppSecret(String wxAppSecret) {
		this.wxAppSecret = wxAppSecret;
	}

	public String getWxToken() {
		return wxToken;
	}

	public void setWxToken(String wxToken) {
		this.wxToken = wxToken;
	}

	public String getWxAesKey() {
		return wxAesKey;
	}

	public void setWxAesKey(String wxAesKey) {
		this.wxAesKey = wxAesKey;
	}

	public String getWxType() {
		return wxType;
	}

	public void setWxType(String wxType) {
		this.wxType = wxType;
	}

	public MultipartFile getAppTemp() {
		return appTemp;
	}

	public void setAppTemp(MultipartFile appTemp) {
		this.appTemp = appTemp;
	}

	public String getBundleIdentifier() {
		return bundleIdentifier;
	}

	public void setBundleIdentifier(String bundleIdentifier) {
		this.bundleIdentifier = bundleIdentifier;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public String getMsgClientid() {
		return msgClientid;
	}

	public void setMsgClientid(String msgClientid) {
		this.msgClientid = msgClientid;
	}

	public String getMsgFirst() {
		return msgFirst;
	}

	public void setMsgFirst(String msgFirst) {
		this.msgFirst = msgFirst;
	}

	public String getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(String msgTemplate) {
		this.msgTemplate = msgTemplate;
	}

	public String getMsgLast() {
		return msgLast;
	}

	public void setMsgLast(String msgLast) {
		this.msgLast = msgLast;
	}


	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public CommonAppInfo() {
		super();
	}

	public CommonAppInfo(String id){
		super(id);
	}

	@Length(min=0, max=64, message="键值长度必须介于 0 和 64 之间")
	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	
	@Length(min=0, max=64, message="键值版本长度必须介于 0 和 64 之间")
	public String getKeyVersion() {
		return keyVersion;
	}

	public void setKeyVersion(String keyVersion) {
		this.keyVersion = keyVersion;
	}
	
	@Length(min=0, max=100, message="应用名称长度必须介于 0 和 100 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=64, message="sys_office的id长度必须介于 0 和 64 之间")
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	
	@Length(min=0, max=64, message="客户端的id长度必须介于 0 和 64 之间")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Length(min=0, max=255, message="app图片长度必须介于 0 和 255 之间")
	public String getAppImage() {
		return appImage;
	}

	public void setAppImage(String appImage) {
		this.appImage = appImage;
	}
	
	@Length(min=0, max=1, message="是否启动积分机制（1是0否）长度必须介于 0 和 1 之间")
	public String getIsIntegral() {
		return isIntegral;
	}

	public void setIsIntegral(String isIntegral) {
		this.isIntegral = isIntegral;
	}
	
}
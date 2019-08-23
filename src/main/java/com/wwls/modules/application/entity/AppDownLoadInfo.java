package com.wwls.modules.application.entity;
/***
 * @author xudongdong
 * @version 2015-12-23
 * @ desc   App下载方式
 * *****/
public class AppDownLoadInfo {
	
	private String appVersion;//app版本
	private String appUrl;//app下载地址
	private String imageUrl;//app icon 地址
	private String bundleIdentifier;//App的唯一识别码
	private String appName ;//App名称
	private String clientId;//客户端标识
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getAppVersion() {
		return appVersion;
	}
	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getBundleIdentifier() {
		return bundleIdentifier;
	}
	public void setBundleIdentifier(String bundleIdentifier) {
		this.bundleIdentifier = bundleIdentifier;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	
	
	

}

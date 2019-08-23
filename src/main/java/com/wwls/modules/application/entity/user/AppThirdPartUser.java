/**

 */
package com.wwls.modules.application.entity.user;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.wwls.common.persistence.DataEntity;

/**
 * 应用第三方用户Entity
 * @author chenbaichuan
 * @version 2016-06-23
 */
public class AppThirdPartUser extends DataEntity<AppThirdPartUser> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String clientId;		// app标识
	private String muserId;		// 主用户great_main_user的id
	private String channelId;		// 渠道id
	private String nickName;		// 昵称
	private String partType;		// 第三方登录方式（1：微信2：qq:3：微博）
	private String openId;		// 授权的open_id
	private String unionids;		// 此字段只有微信使用
 	private String officeId;//公司officeid
	private String headImage;//用户头像
	private String phone;//手机号
	private String valiDateCode;//验证码
	private String name;//姓名
	private String gender;//性别
	private String whiteListId;//白名单id
	private String publicUserId;//企业号用户id
	public String getPublicUserId() {
		return publicUserId;
	}

	public void setPublicUserId(String publicUserId) {
		this.publicUserId = publicUserId;
	}

	public String getWhiteListId() {
		return whiteListId;
	}

	public void setWhiteListId(String whiteListId) {
		this.whiteListId = whiteListId;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getValiDateCode() {
		return valiDateCode;
	}

	public void setValiDateCode(String valiDateCode) {
		this.valiDateCode = valiDateCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public AppThirdPartUser() {
		super();
	}

	public AppThirdPartUser(String id){
		super(id);
	}

	@Length(min=0, max=64, message="app标识长度必须介于 0 和 64 之间")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Length(min=0, max=64, message="主用户great_main_user的id长度必须介于 0 和 64 之间")
	public String getMuserId() {
		return muserId;
	}

	public void setMuserId(String muserId) {
		this.muserId = muserId;
	}
	
	@Length(min=0, max=64, message="渠道id长度必须介于 0 和 64 之间")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@Length(min=0, max=64, message="昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	@Length(min=0, max=1, message="第三方登录方式（1：微信2：qq:3：微博）长度必须介于 0 和 1 之间")
	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}
	
	@Length(min=0, max=64, message="授权的open_id长度必须介于 0 和 64 之间")
	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
	@Length(min=0, max=64, message="此字段只有微信使用长度必须介于 0 和 64 之间")
	public String getUnionids() {
		return unionids;
	}

	public void setUnionids(String unionids) {
		this.unionids = unionids;
	}
	
 
	
}
/**

 */
package com.wwls.modules.application.entity.user;

import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wwls.common.persistence.DataEntity;
import com.wwls.modules.application.entity.activation.CommonActivationCode;

/**
 * 应用用户主表Entity
 * @author chenbaichuan
 * @version 2016-06-23
 */
public class AppMainUser extends DataEntity<AppMainUser> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String headImage;		// 用户头像
	private String clientId;		// app标识
	private String channelId;		// 渠道id
	private String area;		// 用户注册所在区域
	private String email;		// 电子邮箱
	private String qq;		// 用户qq号
	private String wx;		// 用户微信
	private String loginType;		// 登陆方式：1为常规登陆；2为第三方登录
	private String name;		// 用户姓名
	private String age;		// 用户年龄
	private Date birthday;		// 生日
	private String country;		// 用户所在国家
	private String gender;		// 用户性别(0女1男)
	private String isWork;		// 目前是否有工作（0有1没有）
	private String signature;		// 个性化签名
	private String nickName;		// 用户昵称
 	private String accesstoken;		// 令牌
	private String activityCode;		// 激活码 ，此字段用来验证是否政企用户，非政企用户，此字段为null
	private String grade;		// 会员等级排序
	private String gradeName;		// 会员等级名称
	private String officeId;//机构Id
	private BigDecimal myMoney;//我的账户余额
	private String whiteListId;//白名单id
	private CommonActivationCode commonActivationCode;//激活码
	private Map<String,String> map = new HashMap<String,String>();
	private String phone;//手机号
	private BigDecimal woDou;//沃豆 
	private String publicUserId;//公众号用户id
	private String imgStatus;//头像是否被修改过（1是0否）
	
	
	
	public String getImgStatus() {
		return imgStatus;
	}

	public void setImgStatus(String imgStatus) {
		this.imgStatus = imgStatus;
	}

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

	

	
	public BigDecimal getWoDou() {
		return woDou;
	}

	public void setWoDou(BigDecimal woDou) {
		this.woDou = woDou;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Map<String, String> getMap() {
		return map;
	}

	public void setMap(Map<String, String> map) {
		this.map = map;
	}

	public CommonActivationCode getCommonActivationCode() {
		return commonActivationCode;
	}

	public void setCommonActivationCode(CommonActivationCode commonActivationCode) {
		this.commonActivationCode = commonActivationCode;
	}

	public BigDecimal getMyMoney() {
		return myMoney;
	}

	public void setMyMoney(BigDecimal myMoney) {
		this.myMoney = myMoney;
	}

	public String getOfficeId() {
		return officeId;
	}

	public void setOfficeId(String officeId) {
		this.officeId = officeId;
	}

	public AppMainUser() {
		super();
		this.myMoney=new BigDecimal(0);
	}

	public AppMainUser(String id){
		super(id);
	}

	@Length(min=0, max=256, message="用户头像长度必须介于 0 和 256 之间")
	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	@Length(min=0, max=64, message="app标识长度必须介于 0 和 64 之间")
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	@Length(min=0, max=64, message="渠道id长度必须介于 0 和 64 之间")
	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
	@Length(min=0, max=64, message="用户注册所在区域长度必须介于 0 和 64 之间")
	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}
	
	@Length(min=0, max=128, message="电子邮箱长度必须介于 0 和 128 之间")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=30, message="用户qq号长度必须介于 0 和 30 之间")
	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}
	
	@Length(min=0, max=30, message="用户微信长度必须介于 0 和 30 之间")
	public String getWx() {
		return wx;
	}

	public void setWx(String wx) {
		this.wx = wx;
	}
	
	@Length(min=0, max=1, message="登陆方式：1为常规登陆；2为第三方登录长度必须介于 0 和 1 之间")
	public String getLoginType() {
		return loginType;
	}

	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	
	@Length(min=0, max=64, message="用户姓名长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=5, message="用户年龄长度必须介于 0 和 5 之间")
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	@Length(min=0, max=30, message="用户所在国家长度必须介于 0 和 30 之间")
	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	@Length(min=0, max=1, message="用户性别(0男1女)长度必须介于 0 和 1 之间")
	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	@Length(min=0, max=1, message="目前是否有工作（0有1没有）长度必须介于 0 和 1 之间")
	public String getIsWork() {
		return isWork;
	}

	public void setIsWork(String isWork) {
		this.isWork = isWork;
	}
	
	@Length(min=0, max=255, message="个性化签名长度必须介于 0 和 255 之间")
	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	@Length(min=0, max=64, message="用户昵称长度必须介于 0 和 64 之间")
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
 
	
	@Length(min=0, max=64, message="令牌长度必须介于 0 和 64 之间")
	public String getAccesstoken() {
		return accesstoken;
	}

	public void setAccesstoken(String accesstoken) {
		this.accesstoken = accesstoken;
	}
	
	@Length(min=0, max=64, message="激活码 ，此字段用来验证是否政企用户，非政企用户，此字段为null长度必须介于 0 和 64 之间")
	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}
	
	@Length(min=0, max=10, message="会员等级排序长度必须介于 0 和 10 之间")
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}
	
	@Length(min=0, max=100, message="会员等级名称长度必须介于 0 和 100 之间")
	public String getGradeName() {
		return gradeName;
	}

	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	
}
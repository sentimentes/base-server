package com.wwls.modules.shoppingmall.entity.goods;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import com.wwls.modules.sys.entity.Office;

import com.wwls.common.persistence.DataEntity;

/**
 * 用户管理Entity
 * @author leixiaoming
 * @version 2019-04-10
 */
public class GsUser extends DataEntity<GsUser> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名字
	private String password;		// 密码
	private String jiFei;		// 积分
	private String phone;		// 电话号码
	private String sex;		// 性别
	private String beiFei;		// 备份字段
	private Office office;		// 商店id
	private String email;		// 电子邮箱
	private Date birthday;		// 生日
	private String image;
	private MultipartFile imageTemp;//图片的临时字段
	private String passwords;	//修改密码时的新密码
	private String passwordes;	//修改密码时候的重复密码
	private String passwordOne;
	private String passwordTwo;
	private String answerOne;
	private String answerTwo;
	
	
	public String getPasswordOne() {
		return passwordOne;
	}

	public void setPasswordOne(String passwordOne) {
		this.passwordOne = passwordOne;
	}

	public String getPasswordTwo() {
		return passwordTwo;
	}

	public void setPasswordTwo(String passwordTwo) {
		this.passwordTwo = passwordTwo;
	}

	public String getAnswerOne() {
		return answerOne;
	}

	public void setAnswerOne(String answerOne) {
		this.answerOne = answerOne;
	}

	public String getAnswerTwo() {
		return answerTwo;
	}

	public void setAnswerTwo(String answerTwo) {
		this.answerTwo = answerTwo;
	}

	public String getPasswords() {
		return passwords;
	}

	public void setPasswords(String passwords) {
		this.passwords = passwords;
	}

	public String getPasswordes() {
		return passwordes;
	}

	public void setPasswordes(String passwordes) {
		this.passwordes = passwordes;
	}

	public MultipartFile getImageTemp() {
		return imageTemp;
	}

	public void setImageTemp(MultipartFile imageTemp) {
		this.imageTemp = imageTemp;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public GsUser() {
		super();
	}

	public GsUser(String id){
		super(id);
	}

	@Length(min=0, max=255, message="名字长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="密码长度必须介于 0 和 255 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=255, message="积分长度必须介于 0 和 255 之间")
	public String getJiFei() {
		return jiFei;
	}

	public void setJiFei(String jiFei) {
		this.jiFei = jiFei;
	}
	
	@Length(min=0, max=255, message="电话号码长度必须介于 0 和 255 之间")
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	@Length(min=0, max=255, message="性别长度必须介于 0 和 255 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	
	@Length(min=0, max=255, message="备份字段长度必须介于 0 和 255 之间")
	public String getBeiFei() {
		return beiFei;
	}

	public void setBeiFei(String beiFei) {
		this.beiFei = beiFei;
	}
	
	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}
	
}
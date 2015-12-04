package com.piba.user.pojo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import com.piba.common.MessageBean;

@Entity(name = "User")
public class User implements Serializable {

	private final static long serialVersionUID = 1L;

	public User() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "userName", length = 32)
	private String userName;
	
	@Column(name = "userPwd", length = 40)
	private String userPwd;

	@Column(name = "age")
	private Integer age;

	@Column(name = "niceName", length = 32)
	private String niceName;
	
	@Column(name = "sign", length = 40)
	private String sign;
	
	@Column(name = "createdAt")
	private Date createdAt;
	
	@Column(name = "updatedAt")
	private Date updatedAt;
	
	@Transient
	private MessageBean mb;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getNiceName() {
		return niceName;
	}

	public void setNiceName(String niceName) {
		this.niceName = niceName;
	}
	
	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", age=" + age + ", niceName=" + niceName + "]";
	}

	public MessageBean getMb() {
		return mb;
	}

	public void setMb(MessageBean mb) {
		this.mb = mb;
	}
	
}
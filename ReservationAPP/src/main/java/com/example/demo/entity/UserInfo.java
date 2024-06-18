package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public class UserInfo {

	private int userId;
	
	@NotNull
	private String mail;
		
	@NotNull
	private String userName;

	@NotNull
	private String userNameFurigana;

	@NotNull
	private String phone;
	
	@NotNull
	private String userPassword;
	
	@NotNull
	private String postCode;
	
	@NotNull
	private String city;
	
	@NotNull
	private String municipalities;
	
	@NotNull
	private String userAddress;
	
	private String building;
	
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private LocalDateTime createdAt;
	
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private LocalDateTime updatedAt;

	/*
	 *	DB更新・挿入用コンストラクタ
	 *  次の項目はデフォルト値の設定やNULL許容があるので省いています。
	 *  userId,building,createdAt,updatedAt
	 */
	public UserInfo(
			@NotNull String mail,
			@NotNull String userName, 
			@NotNull String userNameFurigana,
			@NotNull String phone, 
			@NotNull String userPassword, 
			@NotNull String postCode,
			@NotNull String city,
			@NotNull String municipalities,
			@NotNull String userAddress,
			String building, 
			LocalDateTime updatedAt) {
		this.userId = userId;
		this.mail = mail;
		this.userName = userName;
		this.userNameFurigana = userNameFurigana;
		this.phone = phone;
		this.userPassword = userPassword;
		this.postCode = postCode;
		this.city = city;
		this.municipalities = municipalities;
		this.userAddress = userAddress;
		this.building = building != null ? building : null ;
		this.updatedAt = updatedAt;	
	}	
	
//	DBデータ取得用コンストラクタ
	public UserInfo(
			@NotNull int userId,
			@NotNull String mail,
			@NotNull String userName, 
			@NotNull String userNameFurigana,
			@NotNull String phone, 
			@NotNull String userPassword, 
			@NotNull String postCode,
			@NotNull String city,
			@NotNull String municipalities,
			@NotNull String userAddress,
			String building, 
			LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.userId = userId;
		this.mail = mail;
		this.userName = userName;
		this.userNameFurigana = userNameFurigana;
		this.phone = phone;
		this.userPassword = userPassword;
		this.postCode = postCode;
		this.city = city;
		this.municipalities = municipalities;
		this.userAddress = userAddress;
		this.building = building != null ? building : null ;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;	
	}	

	public int getUserId() {
		return userId;
	}

	public String getMail() {
		return mail;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserNameFurigana() {
		return userNameFurigana;
	}

	public String getPhone() {
		return phone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getPostCode() {
		return postCode;
	}
	public String getCity() {
		return city;
	}
	public String getMunicipalities() {
		return municipalities;
	}

	public String getUserAddress() {
		return userAddress;
	}

	public String getBuilding() {
		return building;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserNameFurigana(String userNameFurigana) {
		this.userNameFurigana = userNameFurigana;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setMunicipalities(String municipalities) {
		this.municipalities = municipalities;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getPostCodeAddHyphen() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(postCode.substring(0,3));
		sb.append("-");
		sb.append(postCode.substring(3,7));
		
		return sb.toString();
	}
	
	public String getPhoneAddHyphen() {	
		if(phone.length() == 11) {
			StringBuilder sb = new StringBuilder();
			sb.append(phone.substring(0,3));
			sb.append("-");
			sb.append(phone.substring(3,7));
			sb.append("-");
			sb.append(phone.substring(7,11));
			
			return sb.toString();
		}
		
		if(phone.substring(0,2).equals("03")) {
			StringBuilder sb = new StringBuilder();
			sb.append(phone.substring(0,2));
			sb.append("-");
			sb.append(phone.substring(2,6));
			sb.append("-");
			sb.append(phone.substring(6,10));
			
			return sb.toString();
			
		}else{
			StringBuilder sb = new StringBuilder();
			sb.append(phone.substring(0,4));
			sb.append("-");
			sb.append(phone.substring(4,6));
			sb.append("-");
			sb.append(phone.substring(6,10));
			
			return sb.toString();
		}
	}
}

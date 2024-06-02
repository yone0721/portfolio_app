package com.example.demo.entity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;

public class StoreInfo {
	
	@NotNull
	private int storeId;

	@NotNull
	private String storeName;
	
	@NotNull
	private String postCode;
	
	@NotNull
	private String city;
	
	@NotNull
	private String municipalities;

	@NotNull
	private String streetAddress;

	private String building;
	
	@NotNull
	private String mail;
	
	@NotNull
	private String phone;
	
	@NotNull
	private String storePassword;
	
	@NotNull
	private String isOpened;
	
	@NotNull
	private String isClosed;
	
	@NotNull
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private LocalDateTime createdAt;
	
	@DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
	private LocalDateTime updatedAt;

//	DB挿入・更新用コンストラクタ
	public StoreInfo(
			@NotNull String storeName, 
			@NotNull String postCode, 
			@NotNull String city,
			@NotNull String municipalities, 
			@NotNull String streetAddress, 
			String building, 
			@NotNull String mail,
			@NotNull String phone,
			@NotNull String storePassword,
			@NotNull String isOpened,
			@NotNull String isClosed,
			LocalDateTime updatedAt) {
		this.storeName = storeName;
		this.postCode = postCode;
		this.city = city;
		this.municipalities = municipalities;
		this.streetAddress = streetAddress;
		this.building = building;
		this.mail = mail;
		this.phone = phone;
		this.storePassword = storePassword;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		this.updatedAt = updatedAt;
	}
	
//	DBデータ取得用コンストラクタ
	public StoreInfo(
			@NotNull int storeId, @NotNull
			String storeName, @NotNull
			String postCode,
			@NotNull String city,
			@NotNull String municipalities,
			@NotNull String streetAddress, String building,
			@NotNull String mail,
			@NotNull String phone,
			@NotNull String storePassword,
			@NotNull String isOpened,
			@NotNull String isClosed,
			@NotNull LocalDateTime createdAt,
			LocalDateTime updatedAt) {
		this.storeId = storeId;
		this.storeName = storeName;
		this.postCode = postCode;
		this.city = city;
		this.municipalities = municipalities;
		this.streetAddress = streetAddress;
		this.building = building;
		this.mail = mail;
		this.phone = phone;
		this.storePassword = storePassword;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public int getStoreId() {
		return storeId;
	}
	
	public String getStoreName() {
		return storeName;
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
	
	public String getStreetAddress() {
		return streetAddress;
	}
	
	public String getBuilding() {
		return building;
	}
	
	public String getMail() {
		return mail;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getStorePassword() {
		return storePassword;
	}
	
	public String getIsOpened() {
		return isOpened;
	}
	
	public String getIsClosed() {
		return isClosed;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	
	
}
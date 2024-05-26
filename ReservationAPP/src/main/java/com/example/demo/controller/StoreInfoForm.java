package com.example.demo.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class StoreInfoForm {
	

	@NotNull(message="店名が未入力です。")
	@Size(min=1,max=30,message="店名は30字以内でご入力ください。")
	private String storeName;
	
	@NotNull(message="郵便番号が未入力です。")
	private String postCode;

	@NotNull(message="都道府県が未入力です。")
	private String city;

	@NotNull(message="市区町村が未入力です。")
	@Size(min=1,max=10,message="市区町村は10字以内でご入力ください。")
	private String municipalities;

	@NotNull(message="市区町村以降の住所が未入力です。")
	@Size(min=1,max=20,message="市区町村以降の住所は、20字以内でご入力ください。")
	private String streetAddress;
	
	@Size(min=0,max=20,message="建物名は20次以内でご入力ください。")
	private String building;
	
	@NotNull(message="メールアドレスが未入力です。")
	@Email(message="入力されたメールアドレスの形式は不正です。")
	private String mail;
	
	@NotNull(message="電話番号が未入力です。")
	private String phone;
	
	@NotNull(message="パスワードが未入力です。")
	@Size(min=8,max=32,message="パスワードは8字以上、32字以内でご入力ください。")
	private String storePassword;
	
	@NotNull(message="開店時間が未入力です。")
	private String isOpend;
	
	@NotNull(message="閉店時間が未入力です。")
	private String isClosed;
	public void setStoreName(String storeName) {
		this.storeName = storeName;
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
	
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	
	public void setBuilding(String building) {
		this.building = building;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public void setStorePassword(String storePassword) {
		this.storePassword = storePassword;
	}
	
	public void setIsOpend(String isOpend) {
		this.isOpend = isOpend;
	}
	
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}
}

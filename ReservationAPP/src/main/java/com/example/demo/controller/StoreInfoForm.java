package com.example.demo.controller;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class StoreInfoForm {

	@NotEmpty(message="店名が未入力です。")
	@Size(min=1,max=30,message="店名は30字以内でご入力ください。")
	private String storeName;
	
	@NotEmpty(message="郵便番号が未入力です。")
	private String postCode;

	@NotEmpty(message="都道府県が未入力です。")
	private String city;

	@NotEmpty(message="市区町村が未入力です。")
	@Size(min=1,max=10,message="市区町村は10字以内でご入力ください。")
	private String municipalities;

	@NotEmpty(message="市区町村以降の住所が未入力です。")
	@Size(min=1,max=20,message="市区町村以降の住所は、20字以内でご入力ください。")
	private String streetAddress;
	
	@Size(min=0,max=20,message="建物名は20次以内でご入力ください。")
	private String building;
	
	@NotEmpty(message="メールアドレスが未入力です。")
	@Email(message="入力されたメールアドレスの形式は不正です。")
	private String mail;
	
	@NotEmpty(message="電話番号が未入力です。")
	@Pattern(regexp="[0-9]{10,11}",message="電話番号は半角数字で入力してください。")
	private String phone;
	
	@NotEmpty(message="パスワードが未入力です。")
	@Pattern(regexp="^[a-zA-Z0-9]{8,32}+$",message="パスワードは8字以上32字以内、半角英数字でご入力ください。")
	private String storePassword;
	
	/*
	 * storeReservationLimit:
	 * DB上ではIntegerだが、NULLを格納することも考慮すると
	 * Javaのintでは格納できないので、Stringで定義
	 */
	
	@Nullable
	@Pattern(regexp="^[0-9]{1,3}",message="半角数字で入力してください。")
	private String storeReservationLimit;
	
	@NotEmpty(message="開店時間が未入力です。")
	@Pattern(regexp="[0-9]{2}:[0-9]{2}",message="形式に誤りがあります。")
	private String isOpened;
	
	@NotEmpty(message="閉店時間が未入力です。")
	@Pattern(regexp="[0-9]{2}:[0-9]{2}",message="形式に誤りがあります。")
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
	
	public void setIsOpened(String isOpened) {
		this.isOpened = isOpened;
	}
	
	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
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

	public String getStoreReservationLimit() {
		return storeReservationLimit;
	}

	public void setStoreReservationLimit(String storeReservationLimit) {
		this.storeReservationLimit = storeReservationLimit;
	}
}

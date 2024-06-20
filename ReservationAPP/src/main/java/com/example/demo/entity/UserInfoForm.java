package com.example.demo.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UserInfoForm {
	
	public UserInfoForm() {
		
	}
	
	@NotEmpty(message="お名前が未入力です。")
	@Size(min=1,max=30,message="お名前は30字以内でご入力ください。")
	private String userName;

	@NotEmpty(message="お名前の振り仮名が未入力です。")
	@Size(min=1,max=30,message="お名前の振り仮名は30字以内でご入力ください。")
	private String userNameFurigana;

	@NotEmpty(message="メールアドレスが未入力です。")
	@Email(message="入力されたメールアドレスの形式は不正です。")
	private String mail;
	
	@NotEmpty(message="パスワードが未入力です。")
	@Pattern(regexp="^[a-zA-Z0-9]{8,32}+$",message="パスワードは8字以上32字以内、半角英数字でご入力ください。")
	private String userPassword;
	
	@Pattern(regexp="[0-9]{10,11}",message="電話番号は半角数字で入力してください。")
	@NotEmpty(message="電話番号が未入力です。")
	private String phone;
	
	@NotEmpty(message="郵便番号が未入力です。")
	private String zipCode;

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

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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

	public String getZipCode() {
		return zipCode;
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
	
	public String getZipCodeAddHyphen() {
		
		StringBuilder sb = new StringBuilder();
		sb.append(zipCode.substring(0,3));
		sb.append("-");
		sb.append(zipCode.substring(3,7));
		
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

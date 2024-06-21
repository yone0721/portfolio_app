package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.example.demo.factory.StringFormatUtil;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/*
 * 利用者がマイページで予約を確認する時に
 * 予約情報を格納するエンティティクラス
 */

public class UserReservationInfomation {
	
	@Nullable
	private int reservationId;
	
	@NotEmpty
	private int userId;
		
	@NotEmpty
	private int storeId;
	
	@NotEmpty
	private String storeName;
	
	@NotNull
	private String zipCode;
	
	@NotNull
	private String city;
	
	@NotNull
	private String municipalities;

	@NotNull
	private String streetAddress;

	@Nullable
	private String building;
	
	@NotNull
	private String mail;
	
	@NotNull
	private String phone;
	
	@NotEmpty
	private LocalDate reservedAt;
	
	@NotEmpty
	@Min(1)
	private int numOfPeople;
	
	@NotEmpty
	private LocalDateTime createdAt;

//	DBデータ取得コンストラクタ
	public UserReservationInfomation(
			int reservationId,
			@NotEmpty int userId,
			@NotEmpty int storeId,
			@NotEmpty String storeName,
			@NotNull String zipCode, 
			@NotNull String city, 
			@NotNull String municipalities,
			@NotNull String streetAddress, 
			@Nullable String building, 
			@NotNull String mail, 
			@NotNull String phone,
			@NotEmpty LocalDate reservedAt,
			@NotEmpty @Min(1) int numOfPeople, 
			@NotEmpty LocalDateTime createdAt) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.storeId = storeId;
		this.storeName = storeName;
		this.zipCode = StringFormatUtil.formatZipCodeWithHyphens(zipCode);
		this.city = city;
		this.municipalities = municipalities;
		this.streetAddress = streetAddress;
		this.building = building;
		this.mail = mail;
		this.phone = StringFormatUtil.formatPhoneNumberWithHyphens(phone);
		this.reservedAt = reservedAt;
		this.numOfPeople = numOfPeople;
		this.createdAt = createdAt;
	}

	public int getReservationId() {
		return reservationId;
	}

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = StringFormatUtil.formatZipCodeWithHyphens(zipCode);
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getMunicipalities() {
		return municipalities;
	}

	public void setMunicipalities(String municipalities) {
		this.municipalities = municipalities;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = StringFormatUtil.formatPhoneNumberWithHyphens(phone);
	}

	public LocalDate getReservedAt() {
		return reservedAt;
	}

	public void setReservedAt(LocalDate reservedAt) {
		this.reservedAt = reservedAt;
	}

	public int getNumOfPeople() {
		return numOfPeople;
	}

	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
}	

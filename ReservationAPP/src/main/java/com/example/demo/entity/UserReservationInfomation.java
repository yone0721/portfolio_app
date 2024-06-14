package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
	private String postCode;
	
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
	private LocalDate atReservationDate;
	
	@NotEmpty
	@Min(1)
	private int numOfPeople;
	
	@NotEmpty
	private LocalDateTime atCreated;

//	DBデータ取得・更新用のコンストラクタ
	public UserReservationInfomation(
			int reservationId,
			@NotEmpty int userId,
			@NotEmpty int storeId,
			@NotEmpty String storeName,
			@NotNull String postCode, 
			@NotNull String city, 
			@NotNull String municipalities,
			@NotNull String streetAddress, 
			@Nullable String building, 
			@NotNull String mail, 
			@NotNull String phone,
			@NotEmpty LocalDate atReservationDate,
			@NotEmpty @Min(1) int numOfPeople, 
			@NotEmpty LocalDateTime atCreated) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.storeId = storeId;
		this.storeName = storeName;
		this.postCode = postCode;
		this.city = city;
		this.municipalities = municipalities;
		this.streetAddress = streetAddress;
		this.building = building;
		this.mail = mail;
		this.phone = phone;
		this.atReservationDate = atReservationDate;
		this.numOfPeople = numOfPeople;
		this.atCreated = atCreated;
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

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
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
		this.phone = phone;
	}

	public LocalDate getAtReservationDate() {
		return atReservationDate;
	}

	public void setAtReservationDate(LocalDate atReservationDate) {
		this.atReservationDate = atReservationDate;
	}

	public int getNumOfPeople() {
		return numOfPeople;
	}

	public void setNumOfPeople(int numOfPeople) {
		this.numOfPeople = numOfPeople;
	}

	public LocalDateTime getAtCreated() {
		return atCreated;
	}

	public void setAtCreated(LocalDateTime atCreated) {
		this.atCreated = atCreated;
	}
}	

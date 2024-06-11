package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;

public class Reservation {
	
	@Nullable
	private int reservationId;
	
	@NotEmpty
	private int userId;
		
	@NotEmpty
	private int storeId;
	
	private String storeName;
	
	@NotEmpty
	private LocalDateTime atReservationDate;
	
	@NotEmpty
	private int NumOfPeople;
	
	@NotEmpty
	private LocalDateTime atCreated;
	
	private boolean isDeleted;
	
//	DBデータ取得・更新用のコンストラクタ
	public Reservation(
			int reservationId,
			int userId,
			int storeId,
			String storeName,
			LocalDateTime atReservationDate,
			int NumOfPeople,
			LocalDateTime atCreated,
			boolean isDeleted
			) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.storeId = storeId;
		this.storeName = storeName;
		this.atReservationDate = atReservationDate;
		this.NumOfPeople = NumOfPeople;
		this.atCreated = atCreated;
		this.isDeleted = isDeleted;
	}
	
//	DBの挿入用
	public Reservation(
			int userId,
			int storeId,
			LocalDateTime atReservationDate,
			int NumOfPeople
			) {
		this.userId = userId;
		this.storeId = storeId;
		this.atReservationDate = atReservationDate;
		this.NumOfPeople = NumOfPeople;
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
	public LocalDateTime getAtReservationDate() {
		return atReservationDate;
	}
	public void setAtReservationDate(LocalDateTime atReservationDate) {
		this.atReservationDate = atReservationDate;
	}
	public int getNumOfPeople() {
		return NumOfPeople;
	}
	public void setNumOfPeople(int numOfPeople) {
		NumOfPeople = numOfPeople;
	}
	public LocalDateTime getAtCreated() {
		return atCreated;
	}
	public void setAtCreated(LocalDateTime atCreated) {
		this.atCreated = atCreated;
	}
	public boolean isDelted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}	
	
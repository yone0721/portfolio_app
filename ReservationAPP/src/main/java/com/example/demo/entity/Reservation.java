package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

public class Reservation {
	
	@Nullable
	private int reservationId;
	
	@NotEmpty
	private int userId;
		
	@NotEmpty
	private int storeId;
	
	@Nullable
	private String storeName;
	
	@NotEmpty
	private LocalDate reservedAt;
	
	@NotEmpty
	@Min(1)
	private int numOfPeople;
	
	@NotEmpty
	private LocalDateTime createdAt;
	
	private boolean isDeleted;
	
//	DBデータ取得・更新用のコンストラクタ
	public Reservation(
			int reservationId,
			int userId,
			int storeId,
			String storeName,
			LocalDate reservedAt,
			int NumOfPeople,
			LocalDateTime createdAt,
			boolean isDeleted
			) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.storeId = storeId;
		this.storeName = storeName;
		this.reservedAt = reservedAt;
		this.numOfPeople = numOfPeople;
		this.createdAt = createdAt;
		this.isDeleted = isDeleted;
	}
	
//	DBの挿入用
	public Reservation(
			int userId,
			int storeId,
			LocalDate reservedAt,
			int numOfPeople
			) {
		this.userId = userId;
		this.storeId = storeId;
		this.reservedAt = reservedAt;
		this.numOfPeople = numOfPeople;
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
		numOfPeople = numOfPeople;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public boolean isDelted() {
		return isDeleted;
	}
	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
}	
	
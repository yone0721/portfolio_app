package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotNull;

public class ReservationEntity {
	
	@NotNull
	private int reservationId;
	
	@NotNull
	private int userId;
	
	@NotNull
	private String userName;
	
	@NotNull
	private int storeId;
	
	@NotNull
	private String storeName;
	
	@NotNull
	private String servicePlan;
	
	@NotNull
	private LocalDateTime reservationDateAt;

	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public void setServicePlan(String servicePlan) {
		this.servicePlan = servicePlan;
	}

	public void setReservationDateAt(LocalDateTime reservationDateAt) {
		this.reservationDateAt = reservationDateAt;
	}
}

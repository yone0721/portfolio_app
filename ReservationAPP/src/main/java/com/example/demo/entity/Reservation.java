package com.example.demo.entity;

import java.time.LocalDateTime;

public class Reservation {
	private int reservationId;
	private int userId;
	private String userName;
	private String userMail;
	private String userPhone;
	private int storeId;
	private String storeName;
	private int serviceTypeId;
	private String serviceTypeName;
	private int servicePlanId;
	private String planName;	
	private LocalDateTime atCreated;
	private LocalDateTime atReservationDate;
	
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


	public int getServiceTypeId() {
		return serviceTypeId;
	}


	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}


	public int getServicePlanId() {
		return servicePlanId;
	}


	public void setServicePlanId(int servicePlanId) {
		this.servicePlanId = servicePlanId;
	}
	


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUserMail() {
		return userMail;
	}


	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}


	public String getUserPhone() {
		return userPhone;
	}


	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}


	public String getStoreName() {
		return storeName;
	}


	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}


	public String getServiceTypeName() {
		return serviceTypeName;
	}


	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}


	public String getPlanName() {
		return planName;
	}


	public void setPlanName(String planName) {
		this.planName = planName;
	}
	
	public LocalDateTime getAtCreated() {
		return atCreated;
	}

	public LocalDateTime getAtReservationDate() {
		return atReservationDate;
	}


	public void setAtDateTime(LocalDateTime atReservationDate) {
		this.atReservationDate = atReservationDate;
	}

	public Reservation(String userName, int userId,String userMail, String userPhone, int storeId, String storeName,int serviceTypeId, String serviceTypeName,
			 int servicePlanId,String planName,LocalDateTime atReservationDate) {
		this.userId = userId;
		this.userName = userName;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.storeId = storeId;
		this.storeName = storeName;
		this.serviceTypeId = serviceTypeId;
		this.serviceTypeName = serviceTypeName;
		this.servicePlanId = servicePlanId;
		this.planName = planName;
		this.atReservationDate = atReservationDate;
	}

	public Reservation(int reservationId, int userId, String userName, String userMail, String userPhone, int storeId,
			String storeName, int serviceTypeId, String serviceTypeName, int servicePlanId, String planName,
			LocalDateTime atCreated, LocalDateTime atReservationDate) {
		this.reservationId = reservationId;
		this.userId = userId;
		this.userName = userName;
		this.userMail = userMail;
		this.userPhone = userPhone;
		this.storeId = storeId;
		this.storeName = storeName;
		this.serviceTypeId = serviceTypeId;
		this.serviceTypeName = serviceTypeName;
		this.servicePlanId = servicePlanId;
		this.planName = planName;
		this.atCreated = atCreated;
		this.atReservationDate = atReservationDate;
	}
}

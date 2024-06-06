package com.example.demo.entity;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public class ViewStore {
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
	
	@Nullable
	private String storeReservationLimit;
	
	@NotNull
	private String isOpened;
	
	@NotNull
	private String isClosed;
	
	private int[] holidays;
	
	

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

	public String getStoreReservationLimit() {
		return storeReservationLimit;
	}

	public String getIsOpened() {
		return isOpened;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public int[] getHolidays() {
		return holidays;
	}

	public ViewStore(@NotNull int storeId, 
			@NotNull String storeName, 
			@NotNull String postCode, 
			@NotNull String city,
			@NotNull String municipalities, 
			@NotNull String streetAddress, String building, 
			@NotNull String mail,
			@NotNull String phone, 
			String storeReservationLimit, 
			@NotNull String isOpened, 
			@NotNull String isClosed,
			String holidays) {
		super();
		this.storeId = storeId;
		this.storeName = storeName;
		this.postCode = postCode;
		this.city = city;
		this.municipalities = municipalities;
		this.streetAddress = streetAddress;
		this.building = building;
		this.mail = mail;
		this.phone = phone;
		this.storeReservationLimit = storeReservationLimit;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		
		String[] holidayId = holidays.split(",");
		for(String holiday:holidayId) {
			this.holidays = Integer.parseInt(holiday);
		}	
	}
	
	
}

package com.example.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;

public class StoreView {
//	メンバ変数
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
	
	/*
	 * storeReservationLimit:
	 * DB上ではIntegerだが、NULLを格納することも考慮すると
	 * Javaのintでは格納できないので、Stringで定義
	 */
	
	@Nullable
	private String storeReservationLimit;
	
	@NotNull
	private String isOpened;
	
	@NotNull
	private String isClosed;
	
	private List<String> holidays = new ArrayList<>();

//	getter
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

	public List<String> getHolidays() {
		return holidays;
	}

//	コンストラクタ
	public StoreView(@NotNull int storeId, 
			@NotNull String storeName, 
			@NotNull String postCode, 
			@NotNull String city,
			@NotNull String municipalities, 
			@NotNull String streetAddress, String building, 
			@NotNull String mail,
			@NotNull String phone, 
			@Nullable String storeReservationLimit, 
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
		
		String[] holidaysId = holidays.split(",");
		
		for(String holidayId:holidaysId) {
			switch(holidayId) {
				case "0" -> { this.holidays.add("日");}
				case "1" -> { this.holidays.add("月");}
				case "2" -> { this.holidays.add("火");}
				case "3" -> { this.holidays.add("水");}
				case "4" -> { this.holidays.add("木");}
				case "5" -> { this.holidays.add("金");}
				case "6" -> { this.holidays.add("土");}
			}
		}	
	}
	
	
}

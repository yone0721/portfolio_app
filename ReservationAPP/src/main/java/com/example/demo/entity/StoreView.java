package com.example.demo.entity;

import java.time.DayOfWeek;
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
	private Integer storeReservationLimit;
	
	@NotNull
	private String isOpened;
	
	@NotNull
	private String isClosed;
	
	private List<DayOfWeek> holidays = new ArrayList<>();

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

	public Integer getStoreReservationLimit() {
		return storeReservationLimit;
	}

	public String getIsOpened() {
		return isOpened;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public List<DayOfWeek> getHolidays() {
		return holidays;
	}

public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

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

	public void setStoreReservationLimit(Integer storeReservationLimit) {
		this.storeReservationLimit = storeReservationLimit;
	}

	public void setIsOpened(String isOpened) {
		this.isOpened = isOpened;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}

	public void setHolidays(List<DayOfWeek> holidays) {
		this.holidays.clear();
		for(DayOfWeek holiday:holidays) {
			this.holidays.add(holiday);			
		}
	}
	
	public StoreView() {
		
	}

	//	DB取得用コンストラクタ
	public StoreView(@NotNull int storeId, 
			@NotNull String storeName, 
			@NotNull String postCode, 
			@NotNull String city,
			@NotNull String municipalities, 
			@NotNull String streetAddress,
			String building, 
			@NotNull String mail,
			@NotNull String phone, 
			@Nullable Integer storeReservationLimit, 
			@NotNull String isOpened, 
			@NotNull String isClosed,
			String holidays) {

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
		
		
		String[] requestHolidays = holidays.split(",");
		
		for(String requestHoliday:requestHolidays) {
			
			/*
			 * Javaの列挙型DayOfWeek型から
			 * 合致するint型の曜日を取得
			 * 
			 * DayOfWeek型のint振り分け
			 * 1:月曜日 2:火曜日 ... 7:日曜日
			 * 
			 * DayOfWeekのString型の振り分け
			 * MONDAY, THUSEDAY, WEDNESDAY, ... SUNDAY
			 */
		
				int holidayParseInt = Integer.parseInt(requestHoliday);
				this.holidays.add(DayOfWeek.of(holidayParseInt));
		
		}
	}
	
	//	選択した店舗の詳細格納用コンストラクタ
	public StoreView(@NotNull int storeId, 
			@NotNull String storeName, 
			@NotNull String postCode, 
			@NotNull String city,
			@NotNull String municipalities, 
			@NotNull String streetAddress,
			String building, 
			@NotNull String mail,
			@NotNull String phone, 
			@Nullable Integer storeReservationLimit, 
			@NotNull String isOpened, 
			@NotNull String isClosed,
			List<DayOfWeek> holidays) {
		
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
		
		for(DayOfWeek holiday:holidays) {
//			int dayOfWeekId = holiday.getValue();
//			this.holidays.add(DayOfWeek.of(dayOfWeekId));
			this.holidays.add(holiday);
		}
	}

	/*
	 * DayOfWeekの文字列を漢字一字表記に変えるメソッド
	 */
	
	public String getJPDayOfWeek(DayOfWeek dayOfWeek) {
		int dayOfWeekId = dayOfWeek.getValue();
		
		return switch(dayOfWeekId){
			case 1 -> "月";		// DayOfWeek.valueOf()：MONDAY
			case 2 -> "火";		// DayOfWeek.valueOf()：TUSEDAY
			case 3 -> "水"; 	// DayOfWeek.valueOf()：WEDNESDAY
			case 4 -> "木"; 	// DayOfWeek.valueOf()：THURSEDAY
			case 5 -> "金"; 	// DayOfWeek.valueOf()：FRIDAY
			case 6 -> "土"; 	// DayOfWeek.valueOf()：SATURDAY
			case 7 -> "日"; 	// DayOfWeek.valueOf()：SUNDAY
			default -> null;
		};
	}
	
	public String getAddHyphenPhone(String phone) {
		
		if(phone.length() == 10) {
			return String.format("[0-9]{3}-[0-9]{3}-[0-9]{4}", phone);
			
		}else {
			return String.format("[0-9]{3}-[0-9]{4}-[0-9]{4}", phone);
		}
		
	}
	
	/*
	 * valueOfIntegerReservationLimit	予約上限数に数値がある場合、String型からint型に変換するメソッド
	 */
//	
//	public int valueOfIntegerReservationLimit(String storeReservationLimit) {
//		return storeReservationLimit != null ? Integer.parseInt(storeReservationLimit): null;
//	}
//	
	
}

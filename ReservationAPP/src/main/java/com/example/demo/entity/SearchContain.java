package com.example.demo.entity;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.annotation.Nullable;

/*
 * 検索条件を格納する為のクラス
 * @param keword 		検索欄に入れるフリーワード
 * @param cities 		選択した都道府県
 * @param isOpened 		最短開店時間
 * @param isClosed 		最遅閉店時間
 * @param dayOfWeek 	空いている曜日
 *
 */

public class SearchContain {
	@Nullable
	private List<String> keyword;
	
	@Nullable
	private List<String> cities;
	
	@Nullable
	private String isOpened;
	
	@Nullable
	private String isClosed;
	
	@Nullable
	private List<DayOfWeek> dayOfWeeks;

	public SearchContain(
			String keyword, 
			List<String> cities,
			String isOpened,
			String isClosed,
			Integer... dayOfWeeks) {
		this.setKeyword(keyword);
		this.cities = cities;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		this.setDayOfWeeks(dayOfWeeks);
	}
	
//	public SearchContain(
//			String keyword, 
//			List<String> cities,
//			String isOpened,
//			String isClosed,
//			List<DayOfWeek> dayOfWeeks) {
//		this.keyword = keyword;
//		this.cities = cities;
//		this.isOpened = isOpened;
//		this.isClosed = isClosed;
//		this.dayOfWeeks = dayOfWeeks;
//	}
	

	public List<String> getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		if(!(keyword == null)) {
			String[] keywords = keyword.split("\s");
			
			for(String extractKeyword:keywords) {
				this.keyword.add(extractKeyword);			
			}
		}
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

	public String getIsOpened() {
		return isOpened;
	}

	public void setIsOpened(String isOpened) {
		this.isOpened = isOpened;
	}

	public String getIsClosed() {
		return isClosed;
	}

	public void setIsClosed(String isClosed) {
		this.isClosed = isClosed;
	}

	public List<DayOfWeek> getDayOfWeeks() {
		return dayOfWeeks;
	}

	public void setDayOfWeeks(List<DayOfWeek> dayOfWeeks) {
		this.dayOfWeeks = dayOfWeeks;
	}
	
	/*
	 * @param dayOfWeeks(int) 	DayOfWeek.of()メソッドに渡す引数　1.月曜日 2.火曜日 ... 7.日曜日
	 */
	public void setDayOfWeeks(Integer... dayOfWeeks) {
		
		if(dayOfWeeks != null) {
			for(Integer dayOfWeek:dayOfWeeks) {
				this.dayOfWeeks.add(DayOfWeek.of(dayOfWeek));
			}
		}
	}
	
	public List<StoreView> extractSearchingStores(List<StoreView> storeViewList){
		List<StoreView> extractStoresList = new ArrayList<>();
		
		LocalTime tgtTimeIsOpened = Time.valueOf(this.isOpened).toLocalTime();
		LocalTime tgtTimeIsClosed = Time.valueOf(this.isClosed).toLocalTime();
		
		for(StoreView storeView:storeViewList) {
			if(!(this.cities == null) 
					&& this.cities.contains(storeView.getCity())) {
				extractStoresList.add(storeView);
				continue;
			}
			
			if(!(tgtTimeIsOpened == null)
						&& tgtTimeIsOpened.isAfter(Time.valueOf(storeView.getIsOpened()).toLocalTime())) {
				extractStoresList.add(storeView);
				continue;				
			}
			
			if(!(tgtTimeIsClosed != null)
					&& tgtTimeIsClosed.isBefore(Time.valueOf(storeView.getIsClosed()).toLocalTime())) {	
				extractStoresList.add(storeView);
				continue;				
			}
			
			if(!(this.dayOfWeeks == null) ) {
				for(DayOfWeek holiday:storeView.getHolidays()) {
					
				}
			}
				
		}
		
		
	}
	
}

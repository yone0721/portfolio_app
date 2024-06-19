package com.example.demo.entity;

import java.time.DayOfWeek;
import java.util.List;

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
	private String keyword;
	
	private List<String> cities;
	
	private String isOpened;
	
	private String isClosed;
	
	private List<DayOfWeek> dayOfWeeks;

	public SearchContain(
			String keyword, 
			List<String> cities,
			String isOpened,
			String isClosed,
			List<DayOfWeek> dayOfWeeks) {
		this.keyword = keyword;
		this.cities = cities;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		this.dayOfWeeks = dayOfWeeks;
	}
	
	public SearchContain(
			String keyword, 
			List<String> cities,
			String isOpened,
			String isClosed,
			int... dayOfWeeks) {
		this.keyword = keyword;
		this.cities = cities;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		this.setDayOfWeeks(dayOfWeeks);
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
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
	public void setDayOfWeeks(int... dayOfWeeks) {
		
		for(int dayOfWeek:dayOfWeeks) {
			this.dayOfWeeks.add(DayOfWeek.of(dayOfWeek));			
		}
	}
	
}

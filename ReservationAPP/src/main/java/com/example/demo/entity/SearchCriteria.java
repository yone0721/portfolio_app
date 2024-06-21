package com.example.demo.entity;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
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

public class SearchCriteria {
	@Nullable
	private List<String> keyword  = new ArrayList<>();
	
	@Nullable
	private List<String> cities  = new ArrayList<>();
	
	@Nullable
	private String isOpened;
	
	@Nullable
	private String isClosed;
	
	@Nullable
	private List<DayOfWeek> dayOfWeeks = new ArrayList<>();

	public SearchCriteria(
			@Nullable String keyword, 
			@Nullable List<String> cities,
			@Nullable String isOpened,
			@Nullable String isClosed,
			@Nullable Integer... dayOfWeeks) {
		setKeyword(keyword);
		this.cities = cities;
		this.isOpened = isOpened;
		this.isClosed = isClosed;
		setDayOfWeeks(dayOfWeeks);
	}
	
//	public SearchCriteria(
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
		if(!(keyword.isEmpty())) {
			String[] keywords = keyword.split(" +");
			
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
	public void setDayOfWeeks(Integer[] dayOfWeeks) {
		
		System.out.println(dayOfWeeks.length);
		System.out.println(Arrays.toString(dayOfWeeks));
		
		if(!(dayOfWeeks == null) && dayOfWeeks.length > 0) {
			for(Integer dayOfWeek:dayOfWeeks) {
				this.dayOfWeeks.add(DayOfWeek.of(dayOfWeek));
			}
		}
	}
	
	/*
	 * 指定した検索条件のどれか1つに該当する店舗情報を絞り込み、
	 * リストに格納して戻すメソッド
	 */
	
	public List<StoreView> extractSearchingStores(List<StoreView> storeViewList){
		List<StoreView> extractStoresList = new ArrayList<>();
		
		for(StoreView storeView:storeViewList) {
			if(matchingKeywordInStoresList(storeView)
				|| matchingCity(storeView)
				|| matchingBusinessHours(storeView)
				|| matchingWorkingDays(storeView)) {
				
				extractStoresList.add(storeView);
				continue;
			}			
		}
		return extractStoresList;
				
	}

	/*
	 * 該当キーワードが店舗名、都道府県以外の住所と合致するか判定するメソッド
	 * （今回カテゴリは作っていない為、カテゴリ除く）
	 */
	
	public boolean matchingKeywordInStoresList(StoreView storeView) {
		boolean isMatch = false;
		
		isMatch = this.keyword.contains(storeView.getStoreName()) 
				|| this.keyword.contains(storeView.getMunicipalities())
				|| this.keyword.contains(storeView.getStreetAddress())
				|| this.keyword.contains(storeView.getBuilding());

		return isMatch;	
	}
	
	/*
	 * 選択した都道府県に合致しているかどうかを調べるメソッド
	 */
	
	public boolean matchingCity(StoreView storeView) {
		
		return this.cities.contains(storeView.getCity());
	}
	
	
	/*
	 * matchingBusinessHours	指定した開店時間と閉店時間に該当するかどうか判定するメソッド
	 */
	public boolean matchingBusinessHours(StoreView storeView) {
		LocalTime tgtOpened = Time.valueOf(this.isOpened).toLocalTime();
		LocalTime tgtClosed = Time.valueOf(this.isClosed).toLocalTime();
		LocalTime storeIsOpened = Time.valueOf(storeView.getIsOpened()).toLocalTime();
		LocalTime storeIsClosed = Time.valueOf(storeView.getIsClosed()).toLocalTime();

		boolean isMatch = false;
		
		if(!(tgtOpened == null)) {
			isMatch = tgtOpened.equals(storeIsOpened) || tgtOpened.isAfter(storeIsOpened);
		}
		if(!(tgtClosed == null)) {
			isMatch = tgtClosed.equals(storeIsClosed) || tgtOpened.isBefore(storeIsClosed);
		}
		return isMatch;
	}
	
	/*
	 * 指定した曜日に、店舗が稼働している曜日があるかどうか判定するメソッド
	 */
	
	public boolean matchingWorkingDays(StoreView storeView) {
		boolean isMatch = false;
		
		for(DayOfWeek holiday:storeView.getHolidays()) {
			isMatch = !(this.dayOfWeeks.contains(holiday));
			
			if(isMatch == true) {
				break;
			}
		}
		return isMatch;
	}
}


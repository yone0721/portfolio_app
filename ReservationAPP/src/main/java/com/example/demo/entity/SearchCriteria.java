package com.example.demo.entity;

import java.time.DayOfWeek;
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

public class SearchCriteria{
	@Nullable
	private List<String> keywords  = new ArrayList<>();
	
	@Nullable
	private List<String> cities  = new ArrayList<>();
//	
	@Nullable
	private List<DayOfWeek> dayOfWeeks = new ArrayList<>();

	public SearchCriteria(
			@Nullable String keyword, 
			@Nullable List<String> cities,
			@Nullable String... dayOfWeeks) {
		setKeyword(keyword);
		this.cities = cities;
		setDayOfWeeksFromStrings(dayOfWeeks);
	}
	

	public List<String> getKeywords() {
		return keywords;
	}

	public void setKeyword(String keyword) {
		if(!(keyword.isEmpty())) {
			String[] splitKeywords = keyword.split(" +");
			
			for(String extractKeyword:splitKeywords) {
				this.keywords.add(extractKeyword.trim());			
			}
		}
	}

	public List<String> getCities() {
		return cities;
	}

	public void setCities(List<String> cities) {
		this.cities = cities;
	}

	public List<DayOfWeek> getDayOfWeeks() {
		return dayOfWeeks;
	}

	public void setDayOfWeeks(String[] dayOfWeeks) {
		this.setDayOfWeeksFromStrings(dayOfWeeks);
	}
	
	/*
	 * @param dayOfWeeks(int) 	DayOfWeek.of()メソッドに渡す引数　1.月曜日 2.火曜日 ... 7.日曜日
	 */
	public void setDayOfWeeksFromStrings(String[] dayOfWeeks) {
		
		if(!(dayOfWeeks == null) && dayOfWeeks.length > 0) {
			for(String dayOfWeek:dayOfWeeks) {
				int dayOfWeekValue = Integer.parseInt(dayOfWeek);
				
				this.dayOfWeeks.add(DayOfWeek.of(dayOfWeekValue));
			}
		}
	}
	
	/*
	 * OR検索で該当キーワードが店舗名、都道府県以外の住所と合致するか判定するメソッド
	 * （今回カテゴリは作っていない為、カテゴリ除く）
	 */
	
	public boolean orMatchingKeywordInStore(StoreView storeView) {
		boolean isMatched = false;
		
		if(this.keywords == null) { return isMatched; }
		
		for(String keyword:keywords) {
			isMatched = storeView.getStoreName().contains(keyword)
					|| storeView.getCity().contains(keyword)
					|| storeView.getMunicipalities().contains(keyword)
					|| storeView.getStreetAddress().contains(keyword)
					|| storeView.getBuilding().contains(keyword);
			if(isMatched == true) { break;}
		}
		return isMatched;	
	}
	public boolean andMatchingKeywordInStore(StoreView storeView) {
		boolean isMatch = false;
		
		if(this.keywords == null) { return isMatch; }
		
		for(String keyword:keywords) {
			isMatch = storeView.getStoreName().contains(keyword)
					|| storeView.getCity().contains(keyword)
					|| storeView.getMunicipalities().contains(keyword)
					|| storeView.getStreetAddress().contains(keyword)
					|| storeView.getBuilding().contains(keyword);
			
			if(isMatch == false) { return isMatch; }
		}
		return isMatch;	
	}

	
	/*
	 * 選択した都道府県に合致しているかどうかを調べるメソッド
	 */
	
	public boolean matchingCity(StoreView storeView) {
		boolean isMatched = false;
		
		if(this.cities == null) { return false; }
		
		for(String city:this.cities) {
			
			isMatched = storeView.getCity().contains(city);
			
			if(isMatched == true) { return isMatched; }
		}
		
		return isMatched;
	}
	
	
	/*
	 * 指定した曜日に、店舗が稼働している曜日があるかどうか判定するメソッド
	 */
	
	public boolean matchingWorkingDays(StoreView storeView) {
		
		if(this.dayOfWeeks.isEmpty()) {return false;}
		
		if(this.dayOfWeeks.containsAll(storeView.getHolidays())) {
			return false;
		};
		
		return true;
	}
}


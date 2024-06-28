package com.example.demo.entity;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.factory.StringFormatUtil;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/*
 * 検索条件を格納する為のクラス
 * @param howToSearch 	検索方法を指定する値	0:OR検索、1:AND検索
 * @param keword 		検索欄に入れるフリーワード
 * @param cities 		選択した都道府県
 * @param isOpened 		最短開店時間
 * @param isClosed 		最遅閉店時間
 * @param dayOfWeek 	空いている曜日
 *
 */

public class SearchCondition{
	@NotNull
	private int howToSearch;
	
	@Nullable
	private List<String> keywords  = new ArrayList<>();
	
	@Nullable
	private List<String> cities  = new ArrayList<>();
//	
	@Nullable
	private List<DayOfWeek> dayOfWeeks = new ArrayList<>();
	
	public SearchCondition() {
		
	}
	
	public SearchCondition(
			@NotEmpty String howToSearch,
			@Nullable String keywords, 
			@Nullable List<String> cities,
			@Nullable String... dayOfWeeks) {
		this.howToSearch = Integer.parseInt(howToSearch);
		setKeywords(keywords);
		this.cities = cities;
		setDayOfWeeksFromStrings(dayOfWeeks);
	}
	

	public List<String> getKeywords() {
		return keywords;
	}
	public String getCombinedKeywords() {
		String displayKeywords = StringFormatUtil.listToStrings(keywords);
		return displayKeywords;
	}

	public void setKeywords(String keywords) {
		if(!(keywords.isEmpty())) {
//			下記の箇所で文字列を分割にして配列に挿入する
//			'\\s'(半角スペース) または '　+'(全角スペース)
			String[] splitKeywords = keywords.split("\\s|　+");
			
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
	 * 該当キーワードが店舗名、都道府県以外の住所と合致するか判定するメソッド
	 * （今回カテゴリは作っていない為、カテゴリ除く）
	 */
	
	public boolean containsSearchKeyword(StoreView storeView) {
		boolean isMatched = false;
	
		if(this.keywords == null || this.keywords.isEmpty()) { return false; }
		
		for(String keyword:keywords) {
			isMatched = storeView.getStoreName().contains(keyword)
					|| storeView.getCity().contains(keyword)
					|| storeView.getMunicipalities().contains(keyword)
					|| storeView.getStreetAddress().contains(keyword)
					|| storeView.getBuilding().contains(keyword);
			
//			検索方法に合わせて判定を変える　0 : OR検索	1 : AND検索
			if(this.howToSearch == 0 && isMatched == true) { return isMatched; }
			if(this.howToSearch == 1 && isMatched == false) { return isMatched; }
		}
		return isMatched;	
	}
	
	/*
	 * OR検索時に選択した都道府県に合致しているかどうかを調べるメソッド
	 */
	
	public boolean containsSearchCity(StoreView storeView) {
		boolean isMatched = false;
		
//		都道府県を指定していない場合、都道府県は全対象なのでtrueを返す
		if(this.cities == null || this.cities.isEmpty()) { return true; }
			
			isMatched = this.cities.stream().anyMatch(city -> city.contains(storeView.getCity()));
//			選択した都道府県が1つでも当てはまればtrueを返す

		return isMatched;
	}	
	
	/*
	 * 指定した曜日に、店舗が稼働している曜日があるかどうか判定するメソッド
	 */
	
	public boolean containsSearchWorkingDays(StoreView storeView) {
		boolean isMatched = false;
		
//		曜日を選択していなければ、曜日はいつでも良いということなのでtrueを返す
		if(this.dayOfWeeks == null || this.dayOfWeeks.isEmpty()) {return true;}
		
//		指定した曜日が、1つでも定休日と一致しなければtrueを返す
		for(DayOfWeek holiday:storeView.getHolidays()) {
			isMatched = !(this.dayOfWeeks.stream().anyMatch(dayOfWeek -> dayOfWeek.equals(holiday)));
			
			if(isMatched == true) { break;}
		}
		
		return isMatched;
	}

	/*
	 * すべての条件に一致するか判定するメソッド
	 */
	
	public boolean checkAllSearchCriteria(StoreView storeView) {
		boolean fullfillTheConditions;
		
//		キーワードが入力されているか、検索範囲に含まれているかを判定
		fullfillTheConditions = containsSearchKeyword(storeView);
		
//		キーワードが含まれていた場合、絞り込みの検索条件が当てはまっているかどうかを確認
		if(fullfillTheConditions == true) {
			fullfillTheConditions = containsSearchCity(storeView) && containsSearchWorkingDays(storeView);			
		}
		
		return fullfillTheConditions;
	}
	
	/*
	 * 各検索条件をMapへ格納後、リストに集約して戻すメソッド
	 */
//	
//	public List<Map<String,Object>> getSearchConditions(){
//		List<Map<String,Object>> searchConditionsList = new ArrayList<>();
//				
//		
//	}
}


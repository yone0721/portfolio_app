package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.SearchCondition;
import com.example.demo.entity.StoreView;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedToGetSearchConditionsHistoryException;
import com.example.demo.exception.FailedToGetStoresViewException;
import com.example.demo.exception.StoreInfoNotFoundException;
import com.example.demo.repository.StoresListViewDao;

@Service
public class StoresListViewServiceImpl implements StoresListViewService {
	
	private StoresListViewDao dao;
	
	public StoresListViewServiceImpl(StoresListViewDao dao) {
		this.dao = dao;
	}
	
	/*
	 * StoreView	定休日を含めて、一覧で表示する店舗情報を格納するエンティティ
	 * return 		StoreViewのリストを戻す
	 */
	
	@Override
	public List<StoreView> getStoresList() {
		
		
		try {
			List<StoreView> storesViewList = new ArrayList<>();
			List<Map<String,Object>> storesMapList = dao.findAllStores();
			
			for(Map storeMap:storesMapList) {				
				StoreView storeView = new StoreView(
						(int)storeMap.get("store_id"),
						(String)storeMap.get("store_name"),
						(String)storeMap.get("zip_code"),
						(String)storeMap.get("city"),
						(String)storeMap.get("municipalities"),
						(String)storeMap.get("street_address"),
						(String)storeMap.get("building") != null ?
								(String)storeMap.get("building"):"" ,
						(String)storeMap.get("mail"),
						(String)storeMap.get("phone"),
						storeMap.get("store_reservation_Limit") == null ?
								null: (int)((long)storeMap.get("store_reservation_Limit")),
						(String)storeMap.get("is_opened"),
						(String)storeMap.get("is_closed"),
						(String)storeMap.get("holidays")					
						);
				
				storesViewList.add(storeView);
			}
			
			return storesViewList;
			
		}catch(FailedToGetStoresViewException e) {
			throw new FailedToGetStoresViewException("データ取得に失敗しました。");
		}
	}
	@Override
	public List<StoreView> getStoresListByKeyWord(String keyWord) {
		
		List<StoreView> storeViewList = new ArrayList<>();

		try {
			List<Map<String,Object>> getStoreViewList = dao.findAllStoresByKeyWord(keyWord);
		
			for(Map storeMap:getStoreViewList) {
				StoreView storeView = new StoreView(
						(int)storeMap.get("store_id"),
						(String)storeMap.get("store_name"),
						(String)storeMap.get("zip_code"),
						(String)storeMap.get("city"),
						(String)storeMap.get("municipalities"),
						(String)storeMap.get("street_address"),
						(String)storeMap.get("building") != null ?
						(String)storeMap.get("building"):"" ,
						(String)storeMap.get("mail"),
						(String)storeMap.get("phone"),
								storeMap.get("store_reservation_Limit") == null ?
										null: (int)((long)storeMap.get("store_reservation_Limit")),
						(String)storeMap.get("is_opened"),
						(String)storeMap.get("is_closed"),
						(String)storeMap.get("holidays")					
						);
				
				storeViewList.add(storeView);
			}
			
		}catch(DataAccessException e) {
			throw new StoreInfoNotFoundException("");
		}
		
		return storeViewList;
	}
	
	/*
	 * ユーザーIDを元に検索条件の履歴を全取得するメソッド
	 */
	
	@Override
	public List<SearchCondition> getSearchConditionsById(final int userId) throws FailedToGetSearchConditionsHistoryException{
		List<Map<String,Object>> getSearchHistoriesList = new ArrayList<>();
		
		try{
			getSearchHistoriesList = dao.findSearchHistoriesById(userId);
		}catch(FailedToGetSearchConditionsHistoryException e) {
			throw new FailedToGetSearchConditionsHistoryException("");
		}
		
		if(getSearchHistoriesList == null || getSearchHistoriesList.isEmpty()) { return null; }
		
		List<SearchCondition> searchConditionHistories = new ArrayList<>();
		
		/*
		 * 抽出する検索条件
		 * howToSearch 		0.OR検索指定 1.AND検索指定
		 * keywords 		検索で指定したキーワード
		 * cities 			条件を絞る時に選択した都道府県
		 * dayOfWeeks 		稼働曜日を指定した場合の値　1.月曜日 2.火曜日 ... 7.日曜日
		 *
		 * tgt 　検索条件を履歴ごとにまとめる際にグループに分けるための日時
		 */
		
		int howToSearch = 0;
		List<String> keywords = new ArrayList<>();
		List<String> cities = new ArrayList<>();
		List<Integer> dayOfWeeks = new ArrayList<>();
		
		LocalDateTime tgt = null;
		
		/*
		 * Map historyの中身 
		 */
		
//		最後の要素を判定するためのイテレーター
		Iterator<Map<String,Object>> historyItr = getSearchHistoriesList.iterator();
		
		for(Map<String,Object> history:getSearchHistoriesList) {
	
			if(historyItr.hasNext()) historyItr.next();
			
			if(history.containsValue(null)) { continue; }
			
//			tgtに何もなければ、取得したupdated_atの日付を参照する
			tgt = tgt == null ?
					((LocalDateTime)history.get("updated_at")):tgt;
			
			/*
			 * updated_atの日付が変わった場合の処理
			 * 1.検索条件クラス（SearchCondition）に抽出した検索条件を格納する
			 * 2.1をコントローラーに渡すSearchConditionのリストに格納する
			 * 3.抽出した情報を格納するリストの中身を全削除する
			 * 4.tgtの日付を変える
			 */
			if(!(tgt.equals(((LocalDateTime)history.get("updated_at"))))) {
				SearchCondition searchConditionHistory = new SearchCondition(
						howToSearch,keywords,cities,dayOfWeeks,tgt,tgt);
				
				searchConditionHistories.add(searchConditionHistory);
				
				keywords = new ArrayList<>();
				cities= new ArrayList<>();
				dayOfWeeks= new ArrayList<>();
				
				tgt = ((LocalDateTime)history.get("updated_at"));
			}
			/*
			 * condition_idとswitchを使用してcondition_valueを該当するリストに格納していく
			 */
			
			switch((int)history.get("conditions_id")) {
				case 1 ->{ howToSearch = Integer.parseInt((String)history.get("condition_value"));}
				case 2 ->{ keywords.add((String)history.get("condition_value"));}
				case 3 ->{ cities.add((String)history.get("condition_value"));}
				case 4 ->{ dayOfWeeks.add(Integer.parseInt((String)history.get("condition_value")));}
			}
			
			
			
			if(!(historyItr.hasNext())) {
				SearchCondition searchConditionHistory = new SearchCondition(
						howToSearch,keywords,cities,dayOfWeeks,tgt,tgt);
				
				searchConditionHistories.add(searchConditionHistory);
			}
		}
		return searchConditionHistories;			
	}
	
	
	/*
	 * 検索条件を指定して検索した場合に検索条件を履歴として保存するサービスメソッド
	 */
	
	public boolean saveSearchConditions(final int userId,SearchCondition searchCondition) throws FailedToGetSearchConditionsHistoryException {
		
		/*
		 * リポジトリで処理する為にリストにhowToSearchを格納
		 */
		
		List<Integer> howToSearchInList = new ArrayList<>();
				howToSearchInList.add(searchCondition.getHowToSearch());
		
//		created_atとupdated_atに挿入する現時刻を取得
				
		LocalDateTime now = LocalDateTime.now();
		
		int insertHistoryResult = dao.insertSearchConditionToHistory(userId, now);
		
		if(insertHistoryResult < 1) throw new FailedInsertSQLException("ユーザー情報のデータ挿入に失敗しました。");
		
		Map<String,List<? extends Object>> searchConditionMap = new HashMap<>();
		
		searchConditionMap.put("howToSearch",howToSearchInList);
		searchConditionMap.put("keywords",searchCondition.getKeywords());
		searchConditionMap.put("cities",searchCondition.getCities());
		searchConditionMap.put("dayOfWeeks",searchCondition.getDayOfWeekIntValues());
		
		int[] insertConditionResult = dao.combinedConditionsAndHistories(userId, searchConditionMap, now);
		
		if(Arrays.stream(insertConditionResult).sum() < 1) throw new FailedInsertSQLException("検索条件のデータ挿入に失敗しました。");
		
		return true;
	}
	
	public int removeOldestHistory(final int userId) {
		
		return dao.deleteOldestSearchCondition(userId);
	}

}


package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedToGetStoresViewException;
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
	public UserInfo getUserByMail(String mail) {
		
		return null;
	}

}

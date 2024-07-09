package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.SearchCondition;
import com.example.demo.entity.StoreView;

public interface StoresListViewService {
	List<StoreView> getStoresList();
	List<StoreView> getStoresListByKeyWord(String keyWord);
	List<SearchCondition> getSearchConditionsById(final int userId);
	boolean saveSearchConditions(final int userId,SearchCondition searchCondition);
	int removeOldestHistory(final int userId);
}

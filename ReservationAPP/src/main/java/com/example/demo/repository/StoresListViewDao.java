package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.UserInfo;

public interface StoresListViewDao {
	List<Map<String,Object>> findAllStores();
	List<Map<String,Object>> findAllStoresByKeyWord(String keyWord);
	UserInfo findUserByMail(String mail);
	List<Map<String,Object>> findSearchHistoriesById(final int userId);
	int insertSearchConditionToHistory(final int userId,LocalDateTime now);
	int[] combinedConditionsAndHistories(final int userId, Map<String, List<? extends Object>> searchConditions, LocalDateTime now);
	int deleteOldestSearchCondition(final int userId);
}

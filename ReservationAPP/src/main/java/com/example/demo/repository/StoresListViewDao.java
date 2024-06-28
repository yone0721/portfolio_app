package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.UserInfo;

public interface StoresListViewDao {
	List<Map<String,Object>> findAllStores();
	List<Map<String,Object>> findAllStoresByKeyWord(String keyWord);
	UserInfo findUserByMail(String mail);
	List<Map<String,Object>> findSearchHistoriesById(final int userId);
	int[] insertSearchConditionToHistory(final int userId,final int groupId,List<Map<String,Object>> searchConditions);
	int[] combinedConditionsAndHistories(Map<String, Object> conditions,final int group_id);
}

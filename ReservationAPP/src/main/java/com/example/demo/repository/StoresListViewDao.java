package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.UserInfo;

public interface StoresListViewDao {
	List<Map<String,Object>> findAllStores();
	List<Map<String,Object>> findAllStoresByKeyWord(String keyWord);
	UserInfo findUserByMail(String mail);
}

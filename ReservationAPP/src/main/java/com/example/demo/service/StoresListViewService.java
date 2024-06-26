package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;

public interface StoresListViewService {
	List<StoreView> getStoresList();
	List<StoreView> getStoresListByKeyWord(String keyWord);
	UserInfo getUserByMail(String mail);
}

package com.example.demo.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.example.demo.entity.StoreInfo;

public interface StoreInfoService {
	List<StoreInfo> getAllStore();
	StoreInfo getStoreById(int id);
	void register(StoreInfo storeInfo);
	void save(StoreInfo storeInfo);
	void savePassword(StoreInfo storeInfo);
	void delete(StoreInfo storeInfo);
	StoreInfo checkLoginForm(String mail);
	String hashPass(String password) throws NoSuchAlgorithmException;
}

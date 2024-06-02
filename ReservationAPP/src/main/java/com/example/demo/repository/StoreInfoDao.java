package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.StoreInfo;

public interface StoreInfoDao<T> {
	List<StoreInfo> findAll();
	Optional<StoreInfo> findById(int id);
	int insert(StoreInfo storeInfo);
	int update(StoreInfo storeInfo);
	int updatePassword(StoreInfo storeInfo);
	int delete(StoreInfo storeInfo);
}

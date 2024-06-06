package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.StoreInfo;
import com.example.demo.entity.UserInfo;

public interface ReservationDao {
	List<StoreInfo> findAllStores();
	UserInfo findUserByMail(String mail);
	
}

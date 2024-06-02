package com.example.demo.service;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import com.example.demo.entity.UserInfo;

public interface UserInfoService {
	List<UserInfo> getAll();
	UserInfo getById(int id);
	void register(UserInfo userInfo);
	void save(UserInfo userInfo);
	void delete(UserInfo userInfo)throws NoSuchAlgorithmException ;
	String hashPass(String password) ;
}

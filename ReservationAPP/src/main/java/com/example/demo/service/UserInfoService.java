package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.UserInfo;

public interface UserInfoService {
	List<UserInfo> getAll();
	Optional<UserInfo> getById(int id);
	void register(UserInfo userInfo);
	void save(UserInfo userInfo);
	void delete(UserInfo userInfo);
}

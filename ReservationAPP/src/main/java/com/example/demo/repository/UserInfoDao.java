package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import com.example.demo.entity.UserInfo;

public interface UserInfoDao {
	List<UserInfo> findAll();
	Optional<UserInfo> findById(int id);
	Optional<UserInfo> findByMail(String mail);
	int insert(UserInfo userInfo);
	int update(UserInfo userInfo);
	int updatePassword(UserInfo userInfo);
	int delete(UserInfo userInfo);
}

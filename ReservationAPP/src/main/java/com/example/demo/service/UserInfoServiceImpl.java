package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.UserInfoNotFoundException;
import com.example.demo.repository.UserInfoDao;

@Service
public class UserInfoServiceImpl implements UserInfoService {
	
	private final UserInfoDao dao;
	
	@Autowired
	public UserInfoServiceImpl(UserInfoDao dao) {
		this.dao = dao;
	}

	
	@Override
	public List getAll() {
		return dao.findAll();
	}
	@Override
	public Optional<UserInfo> getById(int id) {
		
		try {
			return dao.findById(id);
		}catch(EmptyResultDataAccessException e) {
			throw new UserInfoNotFoundException("指定したユーザー情報が存在しません。");
		}
		
	}

	@Override
	public void register(UserInfo userInfo) {
		
	}

	@Override
	public void save(UserInfo userInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void delete(UserInfo userInfo) {
		// TODO 自動生成されたメソッド・スタブ
		
	}	
	
}

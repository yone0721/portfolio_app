package com.example.demo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
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
	public UserInfo getById(int id) {
		Optional<UserInfo> userInfoOpt = dao.findById(id);
		
		return userInfoOpt.isPresent() ? userInfoOpt.get(): null ;
	}

	@Override
	public void register(UserInfo userInfo) {
		 if(dao.insert(userInfo) == 0) {
			 throw new FailedInsertSQLException("新規登録に失敗しました。");
		 }
	}

	@Override
	public void save(UserInfo userInfo) {
		
		 if(dao.update(userInfo) == 0) {
			 throw new FailedUpdateSQLException("情報の更新に失敗しました。");
		 }
	}
	public void savePassword(UserInfo userInfo) {
		 if(dao.insert(userInfo) == 0) {
			 throw new FailedUpdateSQLException("パスワードが変更できませんでした。");
		 }
	}

	@Override
	public void delete(UserInfo userInfo) {
		if(dao.delete(userInfo) == 0) {
			throw new FailedDeleteSQLException("登録情報が削除できませんでした。");			
		}
	}	
	
	public boolean authonicateUser(String inputMail,String inputPassword) {
		List<Map<String,Object>> userList = new ArrayList<>();
		
		String hashPass = this.hashPass(inputPassword);
		String userMail,userPassword;
		
		System.out.println("hashPass:" + hashPass);
		
		for(Map<String,Object> user:userList) {
			userMail = (String)user.get("mail");
			userPassword = (String)user.get("user_password");
			
			System.out.println("userPassword:" + userPassword);
			
			
			if(inputMail.equals(userMail) && hashPass.equals(userPassword)) {
				return true;
			}	
		}
		return false;
	}
	
	public String hashPass(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hashByte = md.digest();
			
			return Base64.getEncoder().encodeToString(hashByte);
		}catch(NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}
}

package com.example.demo.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.StoreInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
import com.example.demo.exception.StoreInfoNotFoundException;
import com.example.demo.repository.StoreInfoDao;

@Service
public class StoreInfoServiceImpl implements StoreInfoService {
	
	private final StoreInfoDao dao;
	
	@Autowired
	public StoreInfoServiceImpl(StoreInfoDao dao) {
		this.dao = dao;
	}
	@Override
	public List<StoreInfo> getAllStore() {
		try {
			return dao.findAll();
		}catch(StoreInfoNotFoundException e) {
			e.getStackTrace();
			throw new StoreInfoNotFoundException("店舗情報が存在しないか、データ取得に失敗しました。");
		}
	}

	@Override
	public StoreInfo getStoreById(int id) {
		
		try {
			Optional<StoreInfo> storeInfoOpt = dao.findById(id);
			
			return storeInfoOpt.isPresent() ? storeInfoOpt.get() : null;
		}catch(StoreInfoNotFoundException e) {
			e.getStackTrace();
			throw new StoreInfoNotFoundException("店舗情報が存在しないか、データ取得に失敗しました。");
		}
	}

	@Override
	public void register(StoreInfo storeInfo) {
		try {
			if((Integer.parseInt(storeInfo.getStoreReservationLimit()) > 0)) {
				dao.insert(storeInfo);
			}else {
				dao.insertStoreReservationLimitIsNull(storeInfo);
			}
		}catch(FailedInsertSQLException e) {
			e.getStackTrace();
			throw new FailedInsertSQLException("店舗情報の登録に失敗しました。");
		}
	}

	@Override
	public void save(StoreInfo storeInfo) {
		try {			
			dao.update(storeInfo);
		}catch(FailedInsertSQLException e) {
			e.getStackTrace();
			throw new FailedUpdateSQLException("店舗情報の更新に失敗しました。");
		}
	}

	@Override
	public void savePassword(StoreInfo storeInfo) {
		try {
			dao.updatePassword(storeInfo);
		}catch(FailedInsertSQLException e) {
			e.getStackTrace();
			throw new FailedUpdateSQLException("パスワードの更新に失敗しました。");
		}		
	}

	@Override
	public void delete(StoreInfo storeInfo) {
		try {
			dao.delete(storeInfo);
		}catch(FailedInsertSQLException e) {
			e.getStackTrace();
			throw new FailedDeleteSQLException("店舗情報の削除に失敗しました。");
		}		
	}
	
	@Override
	public String hashPass(String password) throws NoSuchAlgorithmException {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hashBytes = md.digest();
			return Base64.getEncoder().encodeToString(hashBytes);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new NoSuchAlgorithmException("パスワードのハッシュ化に失敗しました。");
		}
	}
	@Override
	public StoreInfo checkLoginForm(String mail) {
		try {
			Optional<StoreInfo> getStoreInfoOpt = dao.findByMail(mail);
			
			return getStoreInfoOpt.isPresent() ? getStoreInfoOpt.get():null ;
		}catch(StoreInfoNotFoundException e) {
			throw new StoreInfoNotFoundException("該当するメールアドレスが見つかりません。");
		}
	}
}

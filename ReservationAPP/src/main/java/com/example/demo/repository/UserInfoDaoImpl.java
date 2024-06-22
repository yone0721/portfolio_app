package com.example.demo.repository;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
import com.example.demo.exception.UserInfoNotFoundException;

@Repository
public class UserInfoDaoImpl implements UserInfoDao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserInfoDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	
	@Override
	public List findAll() {
		String sql = """
				SELECT
					user_id,
					mail,
					user_name,
					user_name_furigana,
					phone,
					user_password,
					zip_code,
					city,
					municipalities,
					user_address,
					building,
					created_at,
					updated_at
				FROM user_info_tb""";
		
		List<UserInfo> userInfoList = new ArrayList<>();
		List<Map<String, Object>> getUserInfoList = jdbcTemplate.queryForList(sql);
		
		try {
			for(Map getUserInfo:getUserInfoList) {			
		
				UserInfo userInfo = new UserInfo(
						(int)getUserInfo.get("user_id"),
						(String)getUserInfo.get("mail"),
						(String)getUserInfo.get("user_name"),
						(String)getUserInfo.get("user_name_furigana"),
						(String)getUserInfo.get("phone"),
						(String)getUserInfo.get("user_password"),
						(String)getUserInfo.get("zip_code"),
						(String)getUserInfo.get("city"),
						(String)getUserInfo.get("municipalities"),
						(String)getUserInfo.get("user_address"),
						(String)getUserInfo.get("building"),
						((LocalDateTime)getUserInfo.get("created_at")),
						((LocalDateTime)getUserInfo.get("updated_at"))			
						);
	
				userInfoList.add(userInfo);		}
			return userInfoList;
		}catch(EmptyResultDataAccessException e) {
			throw new UserInfoNotFoundException("ユーザー情報が存在しないか、データ取得に失敗しました。");
		}
	}
	
	@Override
	public Optional<UserInfo> findById(int id) {
		String sql ="""
				SELECT
					user_id,
					mail,
					user_name,
					user_name_furigana,
					phone,
					user_password,
					zip_code,
					city,
					municipalities,
					user_address,
					building,
					created_at,
					updated_at
				FROM user_info_tb WHERE user_id = ?""";
		Map<String,Object> result = jdbcTemplate.queryForMap(sql,id);
		
		try {
			UserInfo userInfo = new UserInfo(
					(int)result.get("user_id"),
					(String)result.get("mail"),
					(String)result.get("user_name"),
					(String)result.get("user_name_furigana"),
					(String)result.get("phone"),
					(String)result.get("user_password"),
					(String)result.get("zip_code"),
					(String)result.get("city"),
					(String)result.get("municipalities"),
					(String)result.get("user_address"),
					(String)result.get("building"),
					((LocalDateTime)result.get("created_at")),
					((LocalDateTime)result.get("updated_at"))		
					);
			
			Optional<UserInfo> userInfoOpt = Optional.ofNullable(userInfo);
			return userInfoOpt;
		}catch(EmptyResultDataAccessException e) {
			throw new UserInfoNotFoundException("ユーザー情報が存在しないか、データ取得に失敗しました。");
		}
	}
	
	@Override
	public Optional<UserInfo> findByMail(String mail) {
		String sql ="""
				SELECT
					user_id,
					mail,
					user_name,
					user_name_furigana,
					phone,
					user_password,
					zip_code,
					city,
					municipalities,
					user_address,
					building,
					created_at,
					updated_at
				FROM user_info_tb WHERE mail = ?""";
		
		
		try {
			Map<String,Object> result = jdbcTemplate.queryForMap(sql,mail);
			UserInfo userInfo = new UserInfo(
					(int)result.get("user_id"),
					(String)result.get("mail"),
					(String)result.get("user_name"),
					(String)result.get("user_name_furigana"),
					(String)result.get("phone"),
					(String)result.get("user_password"),
					(String)result.get("zip_code"),
					(String)result.get("city"),
					(String)result.get("municipalities"),
					(String)result.get("user_address"),
					(String)result.get("building"),
					((LocalDateTime)result.get("created_at")),
					((LocalDateTime)result.get("updated_at"))		
					);
			
			Optional<UserInfo> userInfoOpt = Optional.ofNullable(userInfo);
			return userInfoOpt;
			
		}catch(EmptyResultDataAccessException e) {
			throw new UserInfoNotFoundException("ユーザー情報が存在しないか、データ取得に失敗しました。");
		}
	}

	@Override
	public int insert(UserInfo userInfo) {
		String sql =  """
				INSERT INTO user_info_tb (
					mail,
					user_name,
					user_name_furigana,
					phone,
					user_password,
					zip_code,
					city,
					municipalities,
					user_address,
					building,
					updated_at) 
				VALUES (?,?,?,?,?,?,?,?,?,?,?)""";
		
		try {
			return jdbcTemplate.update(sql,
					userInfo.getMail(),
					userInfo.getUserName(),
					userInfo.getUserNameFurigana(),
					userInfo.getPhone(),
					hashPass(userInfo.getUserPassword()),
					userInfo.getZipCode(),
					userInfo.getCity(),
					userInfo.getMunicipalities(),
					userInfo.getUserAddress(),
					userInfo.getBuilding(),
					LocalDateTime.now());
		
		}catch(Exception e){
			throw new FailedInsertSQLException("ユーザー情報の新規登録に失敗しました。");
		}
	}

	@Override
	public int update(UserInfo userInfo) {
		String sql =  """
				UPDATE user_info_tb 
				SET 
					mail = ?,
					user_name = ?,
					user_name_furigana = ?,
					phone = ?,
					zip_code = ?,
					city = ?,
					municipalities = ?,
					usr_address = ?,
					building = ?,
					updated_at = ?
				WHERE user_id = ?""";
		
		try {

			return jdbcTemplate.update(sql,
					userInfo.getMail(),
					userInfo.getUserName(),
					userInfo.getUserNameFurigana(),
					userInfo.getPhone(),
					userInfo.getZipCode(),
					userInfo.getCity(),
					userInfo.getMunicipalities(),
					userInfo.getUserAddress(),
					userInfo.getBuilding(),
					LocalDateTime.now(),
					userInfo.getUserId());
		}catch(Exception e){
			throw new FailedUpdateSQLException("ユーザー情報の更新に失敗しました。");
		}
	}

	@Override
	public int delete(UserInfo userInfo) {
		String sql = "DELETE FROM user_info_tb WHERE user_id=?";
		
		try {
			return jdbcTemplate.update(sql,userInfo.getUserId());
		}catch(Exception e) {
			throw new FailedDeleteSQLException("ユーザー情報の削除に失敗しました。");
		}
	}


	@Override
	public int updatePassword(UserInfo userInfo) {
		String sql = "UPDATE user_info_tb SET user_password = ? WHERE = ?";
		
		String hashPass = hashPass(userInfo.getUserPassword());
						
		return jdbcTemplate.update(sql,hashPass,userInfo.getUserId());

	}
	
	public String hashPass(String password) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes());
			byte[] hashBytes = md.digest();
			return Base64.getEncoder().encodeToString(hashBytes);
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return password;
	}
}

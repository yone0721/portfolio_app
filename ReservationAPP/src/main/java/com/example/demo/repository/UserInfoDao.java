package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
import com.example.demo.exception.UserInfoNotFoundException;

@Repository
public class UserInfoDao implements Dao {
	
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public UserInfoDao(JdbcTemplate jdbcTemplate) {
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
					post_code,
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
						(String)getUserInfo.get("post_code"),
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
					post_code,
					city,
					municipalities,
					user_address,
					building,
					created_at,
					updated_at
				FROM user_info_tb WEHERE = ?""";
		Map<String,Object> result = jdbcTemplate.queryForMap(sql,id);
		
		try {
			UserInfo userInfo = new UserInfo(
					(int)result.get("user_id"),
					(String)result.get("mail"),
					(String)result.get("user_name"),
					(String)result.get("user_name_furigana"),
					(String)result.get("phone"),
					(String)result.get("user_password"),
					(String)result.get("post_code"),
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
	public int insert(Object entity) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		UserInfo userInfo = (UserInfo)entity;
		
		String sql =  """
				Insert into user_info_tb (
					mail,
					user_name,
					user_name_furigana,
					phone,
					user_password,
					post_code,
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
						bcpe.encode(userInfo.getUserPassword()),
						userInfo.getPostCode(),
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
	public int update(Object entity) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		UserInfo userInfo = (UserInfo)entity;
		
		String sql =  """
				UPDATE user_info_tb 
				SET 
					mail = ?,
					user_name = ?,
					user_name_furigana = ?,
					phone = ?,
					user_password = ?,
					post_code = ?,
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
					bcpe.encode(userInfo.getUserPassword()),
					userInfo.getPostCode(),
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
	public int delete(Object entity) {
		UserInfo userInfo = (UserInfo)entity;
		
		String sql = "DELETE FROM user_info_tb WHERE user_id=?";
		
		try {
			return jdbcTemplate.update(sql,userInfo.getUserId());
		}catch(Exception e) {
			throw new FailedDeleteSQLException("ユーザー情報の削除に失敗しました。");
		}
	}
}

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

import com.example.demo.entity.StoreInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
import com.example.demo.exception.StoreInfoNotFoundException;

@Repository
public class StoreInfoDao implements Dao {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public StoreInfoDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List findAll() {
		String sql = """
				SELECT 
					store_id,
					store_name,
					post_code,
					city,
					municipalities,
					street_address,
					bulding,
					mail,
					phone,
					store_password,
					opened_on,
					closed_on,
					created_at,
					updated_at
				FROM store_info_tb""";
		
		List<Map<String,Object>> getStoreInfoList = jdbcTemplate.queryForList(sql);
		List<StoreInfo> storeInfoList = new ArrayList<>();
		try {
			for(Map getStoreInfo:getStoreInfoList) {
				StoreInfo storeInfo = new StoreInfo(
						(int)getStoreInfo.get("store_id"),
						(String)getStoreInfo.get("store_name"),
						(String)getStoreInfo.get("post_code"),
						(String)getStoreInfo.get("city"),
						(String)getStoreInfo.get("municipalities"),
						(String)getStoreInfo.get("street_address"),
						(String)getStoreInfo.get("building"),
						(String)getStoreInfo.get("mail"),
						(String)getStoreInfo.get("phone"),
						(String)getStoreInfo.get("store_password"),
						(String)getStoreInfo.get("opened_on"),
						(String)getStoreInfo.get("closed_on"),
						(LocalDateTime)getStoreInfo.get("created_at"),					
						(LocalDateTime)getStoreInfo.get("updated_at")					
						);
				storeInfoList.add(storeInfo);
			}
			
			return storeInfoList;
		}catch(StoreInfoNotFoundException e) {
			throw new StoreInfoNotFoundException("店舗情報が存在しないか、データ取得に失敗しました。");
		}
	}

	@Override
	public Optional findById(int id) {
		String sql = """
				SELECT 					
					store_id,
					store_name,
					post_code,
					city,
					municipalities,
					street_address,
					bulding,
					mail,
					phone,
					store_password,
					opened_on,
					closed_on,
					created_at,
					updated_at
				FROM store_info_tb WHERE store_id = ?""";
		
		try {
			Map<String,Object> getStoreInfo = jdbcTemplate.queryForMap(sql,id);
			StoreInfo storeInfo = new StoreInfo(
					(int)getStoreInfo.get("store_id"),
					(String)getStoreInfo.get("store_name"),
					(String)getStoreInfo.get("post_code"),
					(String)getStoreInfo.get("city"),
					(String)getStoreInfo.get("municipalities"),
					(String)getStoreInfo.get("street_address"),
					(String)getStoreInfo.get("building"),
					(String)getStoreInfo.get("mail"),
					(String)getStoreInfo.get("phone"),
					(String)getStoreInfo.get("store_password"),
					(String)getStoreInfo.get("is_opened"),
					(String)getStoreInfo.get("is_closed"),
					(LocalDateTime)getStoreInfo.get("created_at"),					
					(LocalDateTime)getStoreInfo.get("updated_at")			
				);
			return Optional.ofNullable(storeInfo);
		}catch(EmptyResultDataAccessException e) {
			throw new StoreInfoNotFoundException("店舗情報が存在しないか、データ取得に失敗しました。");
		}
		
	}

	@Override
	public int insert(Object entity) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		StoreInfo storeInfo = (StoreInfo)entity;
		String sql = """
				INSERT INTO store_info_tb(
					store_name,
					post_code,
					city,
					municipalities,
					street_address,
					building,
					mail,
					phone,
					store_password,
					is_opend,
					is_closed,
					update_at
				) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)""";
		try {
			return jdbcTemplate.update(sql,
						storeInfo.getStoreId(),
						storeInfo.getStoreName(),
						storeInfo.getPostCode(),
						storeInfo.getCity(),
						storeInfo.getMunicipalities(),
						storeInfo.getStreetAddress(),
						storeInfo.getBuilding(),
						storeInfo.getMail(),
						storeInfo.getPhone(),
						bcpe.encode(storeInfo.getStorePassword()),
						storeInfo.getIsOpened(),
						storeInfo.getIsClosed(),
						LocalDateTime.now()
					);
		}catch(EmptyResultDataAccessException e) {
			throw new FailedInsertSQLException("店舗情報の登録に失敗しました。");
		}
	}

	@Override
	public int update(Object entity) {
		BCryptPasswordEncoder bcpe = new BCryptPasswordEncoder();
		StoreInfo storeInfo = (StoreInfo)entity;
		String sql = """
				UPDATE store_info_tb
				SET 
					store_name = ?,
					post_code = ?,
					city = ?,
					municpalities = ?,
					street_address = ?, 
					building = ?,
					mail = ?,
					phone = ?,
					store_password = ?,
					is_opened = ?,
					is_closed = ?
				WHERE store_id = ? """;
		try {
			return jdbcTemplate.update(sql,
					storeInfo.getStoreId(),
					storeInfo.getStoreName(),
					storeInfo.getPostCode(),
					storeInfo.getCity(),
					storeInfo.getMunicipalities(),
					storeInfo.getStreetAddress(),
					storeInfo.getBuilding(),
					storeInfo.getMail(),
					storeInfo.getPhone(),
					bcpe.encode(storeInfo.getStorePassword()),
					storeInfo.getIsOpened(),
					storeInfo.getIsClosed(),
					LocalDateTime.now());
		}catch(Exception e) {
			throw new FailedUpdateSQLException("店舗情報の更新に失敗しました。");
		}
	}

	@Override
	public int delete(Object entity) {
		StoreInfo storeInfo = (StoreInfo)entity;
		String sql = "DELETE FROM store_info_tb WHERE store_id = ?";
		
		try {
			return jdbcTemplate.update(sql,storeInfo.getStoreId());
		}catch(Exception e) {
			throw new FailedDeleteSQLException("店舗情報の削除に失敗しました。");
		}
	}
}

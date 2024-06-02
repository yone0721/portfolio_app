package com.example.demo.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.StoreInfo;
import com.example.demo.exception.FailedDeleteSQLException;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedUpdateSQLException;
import com.example.demo.exception.StoreInfoNotFoundException;

@Repository
public class StoreInfoDaoImpl implements StoreInfoDao {
	private final JdbcTemplate jdbcTemplate;
	
	@Autowired
	public StoreInfoDaoImpl(JdbcTemplate jdbcTemplate) {
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
					building,
					mail,
					phone,
					store_password,
					is_opened,
					is_closed,
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
						(String)getStoreInfo.get("isOpened"),
						(String)getStoreInfo.get("isClosed"),
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
	public Optional<StoreInfo> findById(int id) {
		String sql = """
				SELECT 					
					store_id,
					store_name,
					post_code,
					city,
					municipalities,
					street_address,
					building,
					mail,
					phone,
					store_password,
					is_opened,
					is_closed,
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
					(String)getStoreInfo.get("isOpened"),
					(String)getStoreInfo.get("isClosed"),
					(LocalDateTime)getStoreInfo.get("created_at"),					
					(LocalDateTime)getStoreInfo.get("updated_at")			
				);
			return Optional.ofNullable(storeInfo);
		}catch(EmptyResultDataAccessException e) {
			throw new StoreInfoNotFoundException("店舗情報が存在しないか、データ取得に失敗しました。");
		}
		
	}

	@Override
	public int insert(StoreInfo storeInfo) {
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
					is_opened,
					is_closed,
					created_at,
					updated_at
				) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)""";
		try {

			return jdbcTemplate.update(sql,
						storeInfo.getStoreName(),
						storeInfo.getPostCode(),
						storeInfo.getCity(),
						storeInfo.getMunicipalities(),
						storeInfo.getStreetAddress(),
						storeInfo.getBuilding(),
						storeInfo.getMail(),
						storeInfo.getPhone(),
						storeInfo.getStorePassword(),
						storeInfo.getIsOpened(),
						storeInfo.getIsClosed(),
						LocalDateTime.now(),
						LocalDateTime.now()
					);
		}catch(EmptyResultDataAccessException e) {
			throw new FailedInsertSQLException("店舗情報の登録に失敗しました。");
		}
	}

	@Override
	public int update(StoreInfo storeInfo) {
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
					is_opened = ?,
					is_closed = ?,
					updated_at = ?
				WHERE store_id = ? """;
		try {
			return jdbcTemplate.update(sql,
					storeInfo.getStoreName(),
					storeInfo.getPostCode(),
					storeInfo.getCity(),
					storeInfo.getMunicipalities(),
					storeInfo.getStreetAddress(),
					storeInfo.getBuilding(),
					storeInfo.getMail(),
					storeInfo.getPhone(),
					storeInfo.getIsOpened(),
					storeInfo.getIsClosed(),
					LocalDateTime.now(),
					storeInfo.getStoreId());
		}catch(DataAccessException e) {
			throw new FailedUpdateSQLException("店舗情報の更新に失敗しました。");
		}
	}
	
	@Override
	public int updatePassword(StoreInfo storeInfo) {
		String sql = "UPDATE store_info_tb SET store_password = ? WHERE store_id = ?";
		try {			
			return jdbcTemplate.update(sql,storeInfo.getStorePassword(),storeInfo.getStoreId());
		}catch(DataAccessException e) {
			throw new FailedUpdateSQLException("パスワードの更新に失敗しました。");
		}
	}

	@Override
	public int delete(StoreInfo storeInfo) {
		String sql = "DELETE FROM store_info_tb WHERE store_id = ?";
		
		try {
			return jdbcTemplate.update(sql,storeInfo.getStoreId());
		}catch(DataAccessException e) {
			throw new FailedDeleteSQLException("店舗情報の削除に失敗しました。");
		}
	}


}

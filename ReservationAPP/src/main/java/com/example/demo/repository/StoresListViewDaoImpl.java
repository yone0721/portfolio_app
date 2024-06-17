package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedToGetStoresViewException;
import com.example.demo.exception.UserInfoNotFoundException;

@Repository
public class StoresListViewDaoImpl implements StoresListViewDao {
	
	private JdbcTemplate jdbcTemplate;
	
	public StoresListViewDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/*
	 * findAllStores()　			登録されている店舗情報を全取得するメソッド
	 * store_reservation_table		予約上限数から予約済みの件数を引いた数値を格納
	 */
	
	@Override
	public List<Map<String,Object>> findAllStores() {
		String sql = """
			SELECT
			    store.store_id,
			    store.store_name,
			    store.post_code,
			    store.city,
			    store.municipalities,
			    store.street_address,
			    store.building,
			    store.mail,
			    store.phone,
			   
			    store.store_reservation_limit - (
				    SELECT
				        COUNT(reservation_id)
				    FROM reservation_table AS res
				    WHERE res.store_id = store.store_id
			    ) AS store_reservation_limit,
			   
			    store.is_opened,
			    store.is_closed,
			    GROUP_CONCAT(holidays.day_of_week) AS holidays
			FROM store_info_tb AS store
			INNER JOIN store_regular_holidays AS holidays
			ON holidays.store_id = store.store_id
			GROUP BY holidays.store_id
			LIMIT 10;""";
		
		try {
			return jdbcTemplate.queryForList(sql); 
			
		}catch(DataAccessException e) {
			e.getStackTrace();
			throw new FailedToGetStoresViewException("データ取得に失敗しました。");
		}
	}

	/*
	 * ユーザー情報をメールから取得するメソッド
	 * 使うかどうかはわからないので、削除しても良いかも
	 */
	
	@Override
	public UserInfo findUserByMail(String mail) {
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
				FROM user_info_tb WHERE mail = ?""";
		
		try {
			Map<String,Object> getUserInfo = jdbcTemplate.queryForMap(sql,mail);
			
			return new UserInfo(
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
					((Timestamp) getUserInfo.get("created_at")).toLocalDateTime(),
					((Timestamp) getUserInfo.get("updated_at")).toLocalDateTime()
					);
			
		}catch(DataAccessException e){
			throw new UserInfoNotFoundException("ユーザー情報が見つかりませんでした。");
		}
	}

}

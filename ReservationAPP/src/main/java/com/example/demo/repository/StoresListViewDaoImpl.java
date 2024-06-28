package com.example.demo.repository;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedToGetStoresViewException;
import com.example.demo.exception.StoreInfoNotFoundException;
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
			    store.zip_code,
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
			GROUP BY holidays.store_id""";
		
		try {
			return jdbcTemplate.queryForList(sql); 
			
		}catch(DataAccessException e) {
			e.getStackTrace();
			throw new FailedToGetStoresViewException("データ取得に失敗しました。");
		}
	}

	/*
	 * 	検索機能で使用するメソッド
	 * 	@param(keyWord) 	検索する文字列
	 * 	@return 			keyWordを文字列に含む店舗情報を格納したMapList
	 */
	@Override
	public List<Map<String, Object>> findAllStoresByKeyWord(String keyWord) {
		String sql = """
				SELECT
				    store.store_id,
				    store.store_name,
				    store.zip_code,
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
				WHERE MATCH
					(store.store_name,
					 store.city,
					 store.municipalities,
					 store.street_address,
					 store.building)
				AGAINST (? IN NATURAL LANGUAGE MODE)
				GROUP BY holidays.store_id""";
		
		try {
			return jdbcTemplate.queryForList(sql,keyWord);
		}catch(DataAccessException e) {
			throw new StoreInfoNotFoundException("関連店舗が見つかりませんでした。");
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
					zip_code,
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
					(String)getUserInfo.get("zip_code"),
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

	@Override
	public List<Map<String, Object>> findSearchHistoriesById(int userId) {
		String sql = 
				"SELECT"
						+ "his.user_id,"
						+ "con.conditions_id,"
						+ "his.condition_value,"
						+ "his.created_at,"
						+ "his.deleted_at, "
						+ "his.group_id "
				+ "FROM search_conditions AS con"
				+ "LEFT JOIN history_with_search_conditions AS his_with_con "
				+ "ON con.conditions_id = his_with_con.conditions_id "
				+ "LEFT JOIN history_of_search AS his "
				+ "ON his.history_id = his_with_con.history_id "
				+ "WHERE his.user_id = ? "
				+ "ORDER BY his.group_id DESC";
		
		try{
			return jdbcTemplate.queryForList(sql,userId);
			
		}catch(DataAccessException e) {
			throw new FailedToGetStoresViewException("");
		}
	}

	@Override
	public int[] insertSearchConditionToHistory(final int userId,final int groupId,List<Map<String,Object>> searchConditions) {
		String sql = "INSERT INTO "
				+ "history_of_search (user_id,condition_value,group_id,created_at,deleted_at)"
				+ "VALUES(?,?,?,?,?)";
		
		List<Object[]> params = new ArrayList<>();
		
		for(Map<String,Object> condition:searchConditions) {
			
			
			Object[] param = {
					
			};
		}
					
		return ;
	}
	
	/*
	 * 中間テーブルの紐づけメソッド
	 */
	
	@Override
	public int[] combinedConditionsAndHistories(Map<String, Object> conditions,final int group_id) {
		String getHistoryIdSql = "SELECT history_id FROM history_of_search WHERE group_id = ?";
		
		List<Map<String,Object>> historyIdList = jdbcTemplate.queryForList(getHistoryIdSql,group_id);
		
		
		String sql = "INSERT INTO history_with_search_conditions (conditions_id,history_id) VALUES (?,?)";
		
		List<Object[]> params = new ArrayList<>();
		
		for(String key:conditions.keySet()) {
			int history_id;
			
			switch(key) {
				case "howToSearch" -> history_id = 1;
				case "keywords" -> history_id = 2;
				case "cities" -> history_id = 3;
				case "dayOfWeeks" -> history_id = 4;
				default -> history_id = 0;
			}
			
			if(history_id == 0) {continue;}
			
			Object[] param = {Integer.parseInt(key),history_id};
			params.add(param);
		}
		
		return jdbcTemplate.batchUpdate(sql,params);
	}	
	
}

package com.example.demo.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedToGetStoresViewException;
import com.example.demo.exception.StoreInfoNotFoundException;
import com.example.demo.exception.UserInfoNotFoundException;
import com.example.demo.factory.StringByIntTypeCategorizeUtil;

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
	public List<Map<String, Object>> findSearchHistoriesById(final int userId) {
		String sql = 
				"SELECT "
					+ "his.user_id,"
					+ "hwsc.history_id,"
					+ "hwsc.conditions_id,"
					+ "hwsc.condition_value,"
					+ "his.created_at,"
					+ "his.updated_at "
				+ "FROM history_with_search_conditions AS hwsc "
				+ "RIGHT JOIN history_of_search AS his "
				+ "ON hwsc.history_id = his.history_id "
				+ "WHERE his.user_id = ? "
				+ "ORDER BY updated_at DESC";
		try{
			return jdbcTemplate.queryForList(sql,userId);
			
		}catch(DataAccessException e) {
			e.printStackTrace();
			throw new FailedToGetStoresViewException("");
		}
	}
	
	@Override
	public int insertSearchConditionToHistory(final int userId,LocalDateTime now) {
		String sql = "INSERT INTO "
				+ "history_of_search ("
				+ "user_id,created_at,updated_at"
				+ ") VALUES (?,?,?)";
		
		String dateTimeFormatNow = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		try {
			return jdbcTemplate.update(sql,userId,dateTimeFormatNow,dateTimeFormatNow);
			
		}catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/*
	 * 中間テーブルの紐づけメソッド
	 *  Map<String,List<Object>> 	検索条件のカテゴリ毎に値を保存したリストを格納
	 * 	String			検索条件のカテゴリ（現状では検索方法、キーワード、都道府県、稼働曜日のいずれか）
	 * 	List<Object>	検索の値を格納したリスト
	 */
	
	@Override
	public int[] combinedConditionsAndHistories(final int userId,Map<String,List<? extends Object>> searchConditions,final LocalDateTime now) {
		String insertSql = "INSERT INTO history_with_search_conditions "
				+ "(conditions_id,history_id,condition_value) VALUES "
				+ "(?,(SELECT history_id FROM history_of_search WHERE user_id = ? AND created_at = ?),?)";
		
		List<Object[]> params = new ArrayList<>();
		
		/*
		 * カテゴリ毎に値を取り出す
		 * condition = 		Mapのキー
		 * conditionsId = 	キーに該当するID
		 * 
		 * Object[] param 	挿入する値（検索条件カテゴリのID、ユーザーID、検索内容、登録する日時）
		 */
		
		for(Object condition:searchConditions.keySet()) {
			
			if(searchConditions.get(condition) == null || searchConditions.get(condition).isEmpty()) { continue;}
			
			int conditionsId = StringByIntTypeCategorizeUtil.changeKeyToInt((String)condition);
			
			if(conditionsId == 0) continue;
			
			for(Object value:searchConditions.get(condition)) {
				
				Object[] param = {
						conditionsId,
						userId,
						now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
						value
				};		
				params.add(param);
			}
		}
		try {
			return jdbcTemplate.batchUpdate(insertSql,params);
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new FailedInsertSQLException("データの挿入に失敗しました。");
		}
	}

	@Override
	public int deleteOldestSearchCondition(final int userId) {
		String sql = "DELETE FROM history_of_search "
				+ "WHERE user_id = ? AND updated_at = "
				+ "(SELECT min_date FROM "
				+	"(SELECT MIN(updated_at) AS min_date "
			    +   "FROM history_of_search "
			    +   "WHERE user_id = ? "
				+ ") AS sub"
			+ ")";

		return jdbcTemplate.update(sql,userId,userId);
	}	
}

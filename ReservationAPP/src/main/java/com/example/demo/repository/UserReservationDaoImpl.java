package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Reservation;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.exception.FailedToGetReservationException;

@Repository
public class UserReservationDaoImpl implements UserReservationDao {
	private final JdbcTemplate jdbcTemplate;
	
	public UserReservationDaoImpl(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/*
	 * 現在予約している情報をDBから全取得する
	 * 
	 * @param "reservation_id"			予約識別番号
	 * @param "user_id"					利用者識別番号
	 * @param "store_id"				店舗識別番号
	 * @param "store_name"				店舗の名前
	 * @param "at_reservation_date"		来店日
	 * @param "num_of_people"			予約人数
	 * @param "at_created"				予約登録日
	 * @param "is_deleted"				削除フラグ
	 * 
	 * @retrun	取得したMapデータをリストに格納してもどす	
	 * 
	 * ページングは一旦考慮せず、10件までレコードを取得する様に作成
	 */
	
	@Override
	public List<Map<String, Object>> findAllReservationById(int userId) {
		String sql = """
				SELECT 
					reservation.reservation_id,
					reservation.user_id,
					reservation.store_id,
					store.store_name,
					reservation.at_reservation_date,
					reservation.num_of_people,
					reservation.at_created,
					reservation.is_deleted
				FROM reservation_table AS reservation
				INNER JOIN store_info_tb AS store
				ON store.store_id = reservation.store_id
				WHERE reservation.user_id = ?
				AND reservation.is_deleted = 0
				ORDER BY reservation.at_reservation_date DESC
				LIMIT 10	
				""";
		
		try {
			return jdbcTemplate.queryForList(sql, userId);
			
		}catch(DataAccessException e) {
			throw new FailedToGetReservationException("データの取得に失敗しました。");
		}
		
	}
	
	/*
	 * 指定した予約情報をDBから取得する
	 * 
	 * @param("reservation_id")			予約識別番号
	 * @param("user_id")				利用者識別番号
	 * @param("store_id")				店舗識別番号
	 * @param("store_name")				店舗の名前
	 * @param("at_reservation_date")	来店日
	 * @param("num_of_people")			予約人数
	 * @param("at_created")				予約登録日
	 * @param("is_deleted")				削除フラグ
	 * 
	 * @retrun	取得したMapデータをもどす	
	 */

	@Override
	public Map<String, Object> findReservationById(int userId, int reservationId) {
		String sql = """
				SELECT 
					reservation.reservation_id,
					reservation.user_id,
					reservation.store_id,
					store.store_name,
					reservation.at_reservation_date,
					reservation.num_of_people,
					reservation.at_created,
					reservation.is_deleted
				FROM reservation_table AS reservation
				INNER JOIN store_info_tb AS store
				ON store.store_id = reservation.store_id
				WHERE 
					reservation.user_id = ? 
				AND reservation.reservation_id = ?
				AND reservation.is_deleted = 0
				ORDER BY reservation.at_reservation_date DESC
				""";
		
		try {
			return jdbcTemplate.queryForMap(sql, userId,reservationId);
			
		}catch(DataAccessException e) {
			System.out.println("UserReservationDaoImplでエラー：" + e.getStackTrace());
			throw new FailedToGetReservationException("データの取得に失敗しました。");
		}
	}	
	
	@Override
	public Integer calcNumOfEmpty(int store_id,LocalDate tgtDate) {
		String sql ="""
				SELECT
					store.store_reservation_limit -(
						SELECT
							COUNT(reservation_id)
						FROM reservation_table AS res
						WHERE res.store_id = store.store_id
						AND at_reservation_date LIKE ?
					) AS store_reservation_limit
				FROM store_info_tb AS store
				WHERE store.store_id = ?
				""";
		try {		
			
			String formatDate = tgtDate.toString() + "%";
			Map<String,Object> getLimitValue =jdbcTemplate.queryForMap(sql,formatDate,store_id);
			return (Long)getLimitValue.get("store_reservation_limit") != null ?
					(int)((long)getLimitValue.get("store_reservation_limit")): null;
			
		}catch(DataAccessException e) {
			System.out.println("UserReservationDaoImplでエラー：" + e.getStackTrace());			
			throw new FailedToGetReservationException("空き予約数の取得に失敗しました。");
		}
	}

	/*
	 * 新しく予約を登録するメソッド
	 * 
	 * @param("user_id")				利用者識別番号
	 * @param("store_id")				店舗識別番号
	 * @param("at_reservation_date")	来店日
	 * @param("num_of_people")			予約人数
	 * @param("at_created")				予約登録日
	 */
	
	@Override
	@Transactional
	public void insert(Reservation reservation) {
		String sql ="""
				INSERT INTO reservation_table
					(user_id,
					store_id,
					at_reservation_date,
					num_of_people,
					at_created)
					VALUES(?,?,?,?,?)""";
		try {
			int result = jdbcTemplate.update(sql,
					reservation.getUserId(),
					reservation.getStoreId(),
					LocalDateTime.of(reservation.getAtReservationDate(),LocalTime.now()),
					reservation.getNumOfPeople(),
					LocalDateTime.now()
					);
			if(result < 1) {
				throw new FailedInsertSQLException("予約の登録に失敗しました。");
			}			
		}catch(DataAccessException e) {
			System.out.println("UserReservationDaoImplでエラー：" + e.getStackTrace());
			throw new FailedInsertSQLException("予約の登録に失敗しました。");
		}
		
	}

//	現状まだ使わないメソッド
	
	@Override
	public void update(Reservation reservation) {
		// TODO 自動生成されたメソッド・スタブ
		
	}


}

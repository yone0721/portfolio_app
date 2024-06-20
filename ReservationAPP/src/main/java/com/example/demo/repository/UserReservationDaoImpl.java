package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
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
	 * 10件ずつ取得するようにする
	 * 
	 * @param "reservation_id"			予約識別番号
	 * @param "user_id"					利用者識別番号
	 * @param "store_id"				店舗識別番号
	 * @param "store_name"				店舗の名前
	 * @param "reserved_at"		来店日
	 * @param "num_of_people"			予約人数
	 * @param "created_at"				予約登録日
	 * @param "is_deleted"				削除フラグ
	 * 
	 * @retrun	取得したMapデータをリストに格納してもどす	
	 * 
	 * ページネーションは一旦オフセット法を採用、10件ずつ取得
	 */
	
	@Override
	public List<Map<String, Object>> findAllReservationsById(int userId) {
		String sql = """
				SELECT
					res.reservation_id,
					res.user_id,
					res.store_id,
					store.store_name,
					store.zip_code,
					store.city,
					store.municipalities,
					store.street_address,
					store.building,
					store.mail,
					store.phone,
					res.reserved_at,
					res.num_of_people
				FROM reservation_table AS res
				LEFT JOIN store_info_tb AS store
				ON store.store_id = res.store_id
				WHERE res.user_id = ? AND res.is_deleted = 0
				ORDER BY res.reserved_at DESC""";
		
		try {
			return jdbcTemplate.queryForList(sql,userId);
			
		}catch(DataAccessException e) {
			System.out.println("UserReservationDaoImplでエラー：" + e.getStackTrace());
			throw new FailedToGetReservationException("データの取得に失敗しました。");
		}
	}	
	
	@Override
	public List<Map<String, Object>> findAllReservationsById(int userId, int reservationId) {
		String sql = """
				SELECT
					res.reservation_id,
					res.user_id,
					res.store_id,
					store.store_name,
					store.zip_code,
					store.city,
					store.municipalities,
					store.street_address,
					store.building,
					store.mail,
					store.phone,
					res.reserved_at,
					res.num_of_people
				FROM reservation_table AS res
				LEFT JOIN store_info_tb AS store
				ON store.store_id = res.store_id
				WHERE res.user_id = ?
				AND res.is_deleted = 0
				AND res.reservation_id < ?
				ORDER BY res.reserved_at DESC
				LIMIT 10
				""";
		
		try {
			return jdbcTemplate.queryForList(sql,userId,reservationId);
			
		}catch(DataAccessException e) {
			System.out.println("UserReservationDaoImplでエラー：" + e.getStackTrace());
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
	 * @param("reserved_at")	来店日
	 * @param("num_of_people")			予約人数
	 * @param("created_at")				予約登録日
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
					reservation.reserved_at,
					reservation.num_of_people,
					reservation.created_at,
					reservation.is_deleted
				FROM reservation_table AS reservation
				INNER JOIN store_info_tb AS store
				ON store.store_id = reservation.store_id
				WHERE 
					reservation.user_id = ? 
				AND reservation.reservation_id = ?
				AND reservation.is_deleted = 0
				ORDER BY reservation.reserved_at DESC
				""";
		
		try {
			return jdbcTemplate.queryForMap(sql, userId,reservationId);
			
		}catch(DataAccessException e) {
			System.out.println("UserReservationDaoImplでエラー：" + e.getStackTrace());
			throw new FailedToGetReservationException("データの取得に失敗しました。");
		}
	}	
	
	public List<Map<String,Object>> getRemainingReservations(int user_id,int offset) {
		String sql = """
				SELECT count(reservation_id) - ?  AS next_to_reservation,
						? - count(reservation_id)  AS back_to_resevation
				FROM reservation_table
				WHERE user_id = ?
				""";

		try {
			List<Map<String,Object>> RemainingPages = new ArrayList<>();
			
			return jdbcTemplate.queryForList(sql,offset,user_id);
		}catch(DataAccessException  e) {
			throw new FailedToGetReservationException("他の予約情報が見当たりません。");
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
						AND reserved_at LIKE ?
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
	 * @param("reserved_at")	来店日
	 * @param("num_of_people")			予約人数
	 * @param("created_at")				予約登録日
	 */
	
	@Override
	@Transactional
	public void insert(Reservation reservation) {
		String sql ="""
				INSERT INTO reservation_table
					(user_id,
					store_id,
					reserved_at,
					num_of_people,
					created_at)
					VALUES(?,?,?,?,?)""";
		try {
			int result = jdbcTemplate.update(sql,
					reservation.getUserId(),
					reservation.getStoreId(),
					LocalDateTime.of(reservation.getReservedAt(),LocalTime.now()),
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

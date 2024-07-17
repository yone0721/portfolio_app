package com.example.demo.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.Reservation;

public interface UserReservationDao {
	List<Map<String, Object>> findAllReservationsById(int userId);
	List<Map<String, Object>> findAllReservationsById(int userId,int reservationId);
	Map<String,Object> findReservationById(int userId,int reservationId);
	List<Map<String,Object>> getRemainingReservations(int user_id,int offset);
	Integer calcNumOfEmpty(int store_id,LocalDate tgtDate);
	void insert(Reservation reservation);
	void update(Reservation reservation);
//	int delete(); isDeletedフラグがtrueの予約をすべて消すメソッド　バッチ処理で使用する？
}

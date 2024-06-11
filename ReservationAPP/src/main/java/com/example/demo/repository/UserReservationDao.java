package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Reservation;

public interface UserReservationDao {
	List<Map<String,Object>> findAllReservationById(int userId);
	Map<String,Object> findReservationById(int userId,int reservationId);
	void insert(Reservation reservation);
	void update(Reservation reservation);
//	int delete(); isDeletedフラグがtrueの予約をすべて消すメソッド　バッチ処理で使用する？
}

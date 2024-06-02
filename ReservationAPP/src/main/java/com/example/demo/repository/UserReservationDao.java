package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.UserInfo;

public interface UserReservationDao {
	List<Map<String,Object>> findReservationAll(UserInfo userInfo);
	int insert(Reservation reservation);
	int update(Reservation reservation);
	int cancelReservation(Reservation reservation);
} 
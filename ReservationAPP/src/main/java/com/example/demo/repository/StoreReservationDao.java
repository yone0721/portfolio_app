package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreInfo;

public interface StoreReservationDao {
	List<Map<String,Object>> findReservationAll(StoreInfo storeInfo);
	List<Map<String,Object>> findCanceledAll(StoreInfo storeInfo);
	int insert(Reservation reservation);
	int update(Reservation reservation);
	int cancelReservation(Reservation reservation);
	int deleteCanceledReservation(Reservation reservation);
}
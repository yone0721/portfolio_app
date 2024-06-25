package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserReservationInfomation;

public interface UserReservationService {
	List<UserReservationInfomation> getUserReservationListById(UserInfo userInfo);
	List<UserReservationInfomation> getUserReservationListById(UserInfo userInfo,int reservationId) ;
	Map<String, Integer> hasNextPage(UserInfo userInfo,int offset);
	Reservation getReservationInfoById(UserInfo userInfo,Reservation reservation) ;
	Integer getReservationLimitAtDate(StoreView storeView,LocalDate tgtDate);
	void submitReservation(Reservation reservation);
	void updateReservation(Reservation reservation);
	
}

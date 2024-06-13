package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;

public interface UserReservationService {
	List<Reservation> getUserReservationListById(UserInfo userInfo);
	Reservation getReservationInfoById(UserInfo userInfo,Reservation reservation) ;
	int getReservationLimitAtDate(StoreView storeView,LocalDate tgtDate);
	void submitReservation(Reservation reservation);
	void updateReservation(Reservation reservation);
	
}

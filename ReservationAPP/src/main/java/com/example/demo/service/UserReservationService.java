package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.example.demo.entity.PageForm;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserReservationInfomation;

public interface UserReservationService {
	List<UserReservationInfomation> getUserReservationListById(PageForm pageForm);
	List<UserReservationInfomation> getUserReservationListById(PageForm pageForm,int reservationId) ;
	Map<String, Integer> hasNextPage(UserInfo userInfo,int offset);
	Reservation getReservationInfoById(UserInfo userInfo,Reservation reservation) ;
	int getReservationLimitAtDate(StoreView storeView,LocalDate tgtDate);
	void submitReservation(Reservation reservation);
	void updateReservation(Reservation reservation);
	
}

package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.entity.PageForm;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserReservationInfomation;

public interface UserReservationService {
	List<UserReservationInfomation> getUserReservationListByIdAndOffset(PageForm pageForm);
	Reservation getReservationInfoById(UserInfo userInfo,Reservation reservation) ;
	int getReservationLimitAtDate(StoreView storeView,LocalDate tgtDate);
	void submitReservation(Reservation reservation);
	void updateReservation(Reservation reservation);
	
}

package com.example.demo.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedToGetReservationException;
import com.example.demo.repository.UserReservationDao;

@Service
public class UserReservationServiceImpl implements UserReservationService {
	
	private final UserReservationDao dao;
	
	public UserReservationServiceImpl(UserReservationDao dao) {
		this.dao = dao;
	}
	
	/*
	 * 現在予約している情報を取得するメソッド
	 * @param("userInfo") 		利用者情報
	 * intを引数にした際に、誤った数値が入るのを避けるためにUserInfoインスタンスを使用。
	 * 
	 * @return		Mapデータから取り出した予約情報を格納したリスト
	 */
	
	@Override
	public List<Reservation> getUserReservationListById(UserInfo userInfo) {
		
		try {
			List<Reservation> reservationsList = new ArrayList<>();
			
			List<Map<String,Object>> getReservationList = dao.findAllReservationById(userInfo.getUserId());
			
			for(Map getReservation: getReservationList) {
				
				Reservation reservation = new Reservation(
					(int)getReservation.get("reservation_id"),
					(int)getReservation.get("user_id"),
					(int)getReservation.get("store_id"),
					(String)getReservation.get("store_name"),
					((Timestamp)getReservation.get("at_reservation_date")).toLocalDateTime(),
					(int)getReservation.get("num_of_people"),
					((Timestamp)getReservation.get("at_created")).toLocalDateTime(),
					(boolean)getReservation.get("is_deleted")						
						);
				
				reservationsList.add(reservation);
			}
			
			return reservationsList;
			
		}catch(FailedToGetReservationException e) {
			System.out.println("UserReservationServiceImplでエラー：" + e.getStackTrace());
			throw new FailedToGetReservationException("データの取得に失敗しました。");
		}
	}

	@Override
	public Reservation getReservationInfoById(UserInfo userInfo, Reservation reservation) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}

	@Override
	public void submitReservation(Reservation reservation) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

	@Override
	public void updateReservation(Reservation reservation) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

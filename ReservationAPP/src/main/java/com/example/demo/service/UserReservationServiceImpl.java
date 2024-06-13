package com.example.demo.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedInsertSQLException;
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
	 * @param UserInfo  					利用者情報のエンティティクラス
	 * 
	 ＊@param reservationList				Mapから取り出したReservationクラスを格納するリスト
	 * 
	 * @param getReservationList			Reservationクラスで取得したMapデータを格納したリスト
	 * 
	 * @return		Mapデータから取り出した予約情報を格納したReservationクラスのリスト
	 *
	 * @param Reservation 			予約内容格納用のエンティティクラス
	 * @param reservation_id 		予約情報の識別番号
	 * @param userId 				予約を入れた利用者の識別番号
	 * @param storeId				予約先店舗の識別番号
	 * @param storeName				予約先店舗の名前
	 * @param atReservationDate		来店の日程
	 * @param NumOfPeople			予約人数
	 * @param atCreated				予約内容の登録日
	 * @param isDeleted				予約内容のキャンセルフラグ
	 * 
	 * 誤った数値が入るの事を避けるために、
	 * メソッド引数にはint型の代わりにUserInfoインスタンスを使用して
	 * それぞれのIDをRepositoryの引数に渡す。
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
							((Timestamp)getReservation.get("at_reservation_date")).toLocalDateTime().toLocalDate(),
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
	
	/*
	 * 現在予約している情報を取得するメソッド
	 * @param UserInfo  					利用者情報のエンティティクラス
	 * @param Reservation  					予約情報のエンティティクラス
	 * 
	 ＊@param reservationList				Mapから取り出したReservationクラスを格納するリスト
	 * 
	 * @param getReservationList			Reservationクラスで取得したMapデータを格納したリスト
	 * 
	 * @return		Mapデータから取り出した予約情報を格納したReservationクラスのリスト
	 *
	 * @param Reservation 			予約内容格納用のエンティティクラス
	 * @param reservation_id 		予約情報の識別番号
	 * @param userId 				予約を入れた利用者の識別番号
	 * @param storeId				予約先店舗の識別番号
	 * @param storeName				予約先店舗の名前
	 * @param atReservationDate		来店の日程
	 * @param NumOfPeople			予約人数
	 * @param atCreated				予約内容の登録日
	 * @param isDeleted				予約内容のキャンセルフラグ
	 * 
	 * 誤った数値が入るの事を避けるために、
	 * メソッド引数にはint型の代わりにUserInfoとReservationクラスを使用して
	 * それぞれのIDをRepositoryの引数に渡す。
	 */
	
	@Override
	public Reservation getReservationInfoById(UserInfo userInfo, Reservation reservation) {
		
		try {
			Map<String,Object> getReservation = dao.findReservationById(userInfo.getUserId(),reservation.getReservationId());
			
			return new Reservation(
						(int)getReservation.get("reservation_id"),
						(int)getReservation.get("user_id"),
						(int)getReservation.get("store_id"),
						(String)getReservation.get("store_name"),
						((Timestamp)getReservation.get("at_reservation_date")).toLocalDateTime().toLocalDate(),
						(int)getReservation.get("num_of_people"),
						((Timestamp)getReservation.get("at_created")).toLocalDateTime(),
						(boolean)getReservation.get("is_deleted")					
					);
		}catch(FailedToGetReservationException e) {
			System.out.println("UserReservationServiceImplでエラー：" + e.getStackTrace());
			throw new FailedToGetReservationException("データの取得に失敗しました。");
		}		
	}

	/*
	 * Reservationクラスに格納した予約内容をRepositoryクラスに渡す
	 * @param Reservation 						予約内容格納用のエンティティクラス
	 * @param Reservation.userId 				予約を入れた利用者の識別番号
	 * @param Reservation.storeId				予約先店舗の識別番号
	 * @param Reservation.atReservationDate		来店の日程
	 * @param Reservation.NumOfPeople			予約人数
	 * @param Reservation.atCreated				予約内容の登録日
	 *  		
	 * intを引数にした際に、誤った数値が入るのを避けるためにUserInfoインスタンスを使用。
	 * 
	 * @return		Mapデータから取り出した予約情報を格納したリスト
	 */
	
	@Override
	public void submitReservation(Reservation reservation) {
		try {
			dao.insert(reservation);
		}catch(FailedInsertSQLException e) {
			System.out.println("UserReservationServiceImplでエラー：" + e.getStackTrace());
			throw new FailedInsertSQLException("予約の登録に失敗しました。");
		}
	}
	
	@Override
	public int getReservationLimitAtDate(StoreView storeView, LocalDate tgtDate) {
		try {
			return dao.calcNumOfEmpty(storeView.getStoreId(),tgtDate);
		}catch(FailedToGetReservationException e) {
			throw new FailedToGetReservationException("");
		}
	}

//	実装未定　キャンセルや予約内容の変更機能等を作成した時に使うかも？
	@Override
	public void updateReservation(Reservation reservation) {
		// TODO 自動生成されたメソッド・スタブ
		
	}

}

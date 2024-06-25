package com.example.demo.session;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Page;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.entity.UserReservationInfomation;

/*
 * ユーザーログイン後にユーザー情報をセッションで保持するクラス
 */

@Component
@SessionScope
@SuppressWarnings("serial")
public class UserSession implements Serializable {
	private UserInfo userInfo;
	private Reservation reservation;
	private StoreView storeView;
	private List<StoreView> storeViewList;
	private List<UserReservationInfomation> reservationList;
	private Page page;
	
	/*
	 * メソッド
	 * get 		格納されているインスタンスの参照値を取得
	 * set 		引数で渡されたインスタンスを格納する
	 * clear 	格納されているインスタンスを削除する
	 */
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	public Reservation getReservation() {
		return reservation;
	}
	
	public StoreView getStoreView() {
		return storeView;
	}
	
	
	public List<UserReservationInfomation> getReservationList(){
		return this.reservationList;
	}

	public List<StoreView> getStoreViewList(){
		return this.storeViewList;
	}
	public Page getPage() {
		return page;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	public void setStoreView(StoreView storeView) {
		this.storeView = storeView;
	}
	
	public void setReservationList(List<UserReservationInfomation> reservationList) {
		this.reservationList = reservationList;
	}
	public void setStoreViewList(List<StoreView> storeViewList) {
		this.storeViewList = storeViewList;
	}
	
	public void setPage(Page page) {
		this.page = page;
	}
	
	public void clearUserData() {
		userInfo = null;
	}
		
	public void clearReservationData() {
		reservation = null;
	}
	public void clearStoreViewData() {
		storeView = null;
	}
	public void clearReservationListData() {
		this.reservationList = null;
	}
	
	public void clearStoreViewListData() {
		this.storeViewList = null;
	}
	
	public void clearPageData() {
		this.page = null;
	}
}


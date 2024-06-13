package com.example.demo.session;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;

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

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}
	
	public void setStoreView(StoreView storeView) {
		this.storeView = storeView;
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
}


package com.example.demo.session;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

import com.example.demo.entity.UserInfo;

/*
 * ユーザーログイン後にユーザー情報をセッションで保持するクラス
 */

@Component
@SessionScope
@SuppressWarnings("serial")
public class UserSession implements Serializable {
	private UserInfo userInfo;
	
	public UserInfo getUserInfo() {
		return userInfo;
	}
	
	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void clearData() {
		userInfo = null;
	}
}


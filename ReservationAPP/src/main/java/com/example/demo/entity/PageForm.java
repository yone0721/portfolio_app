package com.example.demo.entity;

//ページネーションを行う時に使うエンティティクラス

public class PageForm {
	private int userId;
	private int offset;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public PageForm(int userId, int offset) {
		this.userId = userId;
		this.offset = offset;
	}
	
}

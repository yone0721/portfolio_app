package com.example.demo.entity;

/*
 * 予約確認画面（マイページ）の予約表示数を制御するクラス
 * 参考記事：https://talosta.hatenablog.com/entry/paging#%E3%83%9A%E3%83%BC%E3%82%B8%E3%83%B3%E3%82%B0%E3%81%A8%E3%81%AF
 */

public class Page {

//	1ページの表示件数：10件
	public static final int OUTPUT_NUM = 10;
	
//	全レコードの数
	private long recordNum;
	
//	現在のページ
	private int currentPage = 1;
	
//	全レコードの件数から算出されるページ数（recordsNum/OUTPUT_NUM）
	private long maxPage;
	

	public void config(Long recordsNum) {
		this.recordNum = recordsNum;
		this.maxPage = this.recordNum / OUTPUT_NUM;
		
//		レコード総数÷表示件数で余りが出る場合、または、レコード総数が10件未満の場合
//		最大ページ数を＋1する。
		if(this.recordNum % OUTPUT_NUM != 0 || this.recordNum == 0) {
			++this.maxPage;
		}
	}
	
//	前ページボタンを表示するか、現在のページ数が1以上あるかどうかの判定
	public boolean shouldDisplayPreviousPageButton() {
		return currentPage > 1;
	}
//	次ページボタンを表示するか、現在のページ数が最大ページ数より少ないかどうかの判定
	public boolean shouldDisplayNextPageButton() {
		return currentPage < maxPage;
	}
	
//	トップページに戻る
	public void goToFirstPage() {
		currentPage = 1;
	}
	
//	最後のページへ飛ぶ
	public void goToLastPage() {
		currentPage = (int)maxPage;
	}
	
//	前ページへ飛ぶ
	public void goToPreviousPage() {
		if(shouldDisplayPreviousPageButton()) {
			currentPage--;
		}
	}
	
//	次ページへ飛ぶ
	public void goToNextPage() {
		if(shouldDisplayNextPageButton()) {
			currentPage++;
		}
	}
	
//	取得するレコードのインデックスを計算する
	public Integer offset() {
		return (currentPage-1) * OUTPUT_NUM;
	}

	public long getRecordNum() {
		return recordNum;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public long getMaxPage() {
		return maxPage;
	}
	
}

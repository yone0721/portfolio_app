package com.example.demo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Page;
import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserReservationInfomation;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.service.UserReservationService;
import com.example.demo.session.UserSession;

@Controller
@RequestMapping("/reservation/reserve")
public class UserReservationController {
	
	private final UserReservationService userReservationService;
	private UserSession userSession;
	
	public UserReservationController(
			UserReservationService userReservationService,UserSession userSession) {
		this.userReservationService = userReservationService;
		this.userSession = userSession;
	}

	/*
	 * StoresListViewControllerのtoReservationControllerメソッドから
	 * 利用者情報と選択した店舗情報を受け取るGetメソッド
	 * 利用者情報はuserSession内に格納する 
	 *
	 * 空き予約画面への遷移メソッド
	 * @param("storeView")	一覧から選択した店舗の情報を格納するエンティティ
	 * @param("userInfo")	セッションで保持しているユーザー情報
	 * @return			空き予約確認画面への遷移
	 */
	
	@GetMapping("/store-available-days")
	public String storeAvailableDays(
			@ModelAttribute("useSession") UserSession userSessionInfo,
			@ModelAttribute("storeView") StoreView storeView,
			Model model) {
		
		if(this.userSession == null) {
			this.userSession = userSession;
		}
		
		if(userSession.getStoreView() != null) {
			userSession.clearStoreViewData();
		}
		
		userSession.setStoreView(storeView);
		
		model.addAttribute("storeView",userSession.getStoreView());
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/reservation-input";
	}
	
	@PostMapping("/store-available-days")
	public String storeAvailableDays(
			@RequestParam("reservedAt") String reservedAt,
			@RequestParam("numOfPeople") int numOfPeople,
			Model model) {

		model.addAttribute("storeView",userSession.getStoreView());
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/reservation-input";
	}
	/*
	 * 予約確認への遷移メソッド
	 * 失敗すると空き予約画面に遷移して、エラーを表示する
	 * 
	 * @param("storeView")	一覧から選択した店舗の情報を格納するエンティティ
	 * @param("userInfo")	セッションで保持しているユーザー情報
	 * @return				予約確認画面への遷移
	 */
	
	@PostMapping("/reserve-confirm")
	public String checkAvailableDays(
			@RequestParam("reservedAt") String reservedAt,
			@RequestParam("numOfPeople") int numOfPeople,
			@ModelAttribute StoreView storeView,
			Model model
			) {
		
		/*
		 * 	次の3項目をメソッドで判定して、エラーがあればMapに格納
		 *	・選択した日付が本日またはそれ以前か、または定休日であるか
		 *	・選択した日付の空き予約数が0ではないかどうか
		 *	・予約人数は0以下かどうか
		 */
		Map<String,String> errors = checkReservationDateAndLimit(
				storeView,LocalDate.parse(reservedAt),numOfPeople);
		
//		エラーが無ければ確認画面へ遷移
		if(errors.isEmpty()) {
			model.addAttribute("reservedAt",reservedAt);
			model.addAttribute("numOfPeople",numOfPeople);
			model.addAttribute("userInfo",userSession.getUserInfo());
			model.addAttribute("storeView",userSession.getStoreView());				
			return "view/reservation-confirm";
		}
		
		model.addAttribute("reservedAt",reservedAt);
		model.addAttribute("numOfPeople",numOfPeople);
		model.addAttribute("errors",errors);
		model.addAttribute("userInfo",userSession.getUserInfo());
		model.addAttribute("storeView",userSession.getStoreView());
		return "view/reservation-input";
	}	
	
	/*
	 * 予約の空きを判定するメソッド
	 * param dateError			選択した日付が翌日以前だった場合にエラーメッセージを格納
	 * param limitError			選択した日付の予約数が上限に達していた場合にエラーメッセージを格納
	 */

	@PostMapping("make-a-reservation")
	public String makeAReservation(
			@RequestParam("reservedAt") String reservedAt,	
			@RequestParam("numOfPeople") String numOfPeople,	
			RedirectAttributes redirect,
			Model model
			) {
		
		try {
//			予約を送信する前に、もう一度予約が空いてるかを確認
//			nullの場合は予約数の上限なし
			
			Integer checkLimitResult = userReservationService.getReservationLimitAtDate(
					userSession.getStoreView(), LocalDate.parse(reservedAt));
			
			if((checkLimitResult == null)
					|| checkLimitResult > 0) {
				
//			予約情報をインスタンスへ格納して登録
				Reservation reservation = new Reservation(
						userSession.getUserInfo().getUserId(),
						userSession.getStoreView().getStoreId(),
						LocalDate.parse(reservedAt),
						Integer.parseInt(numOfPeople)
						);
				
				userReservationService.submitReservation(reservation);
				userSession.setReservation(reservation);
				
				return "redirect:/reservation/reserve/reservation-complete";
			}
			throw new FailedInsertSQLException("予約数が上限に達してしまったため、他の日程で予約を入れてください。");
						
		}catch(FailedInsertSQLException e) {
			
			model.addAttribute("reservationError","予約の登録ができませんでした。");
			model.addAttribute("exception:",e.getMessage());
			return "view/reservation-input";
		}		
	}
	
	@GetMapping("/reservation-complete")
	public String reservationComplete(
			Model model				
			) {
		
		model.addAttribute("complete","以下の内容で予約の登録が完了しました。");
		model.addAttribute("userInfo",userSession.getUserInfo());	
		model.addAttribute("storeView",userSession.getStoreView());	
		model.addAttribute("reservation",userSession.getReservation());	
		return "view/reservation-complete";
	}
	
	@PostMapping("/to-store-list")
	public String goToStoreList(
			RedirectAttributes redirect) {
		
		userSession.clearStoreViewData();
		userSession.clearReservationData();
		
		redirect.addFlashAttribute("userInfo",userSession.getUserInfo());
		return "redirect:/reservation/views/open-store-list";
	}
	
	
	/*
	 * マイページへ遷移する時に使う一番初めのロジック
	 *  
	 * @param reservationList				DBからユーザーの予約情報を全取得して格納したリスト
	 * @param Page							マイページに一覧を表示する時に、ページの遷移を判定するクラス
	 * @param displayReservationList		一覧に表示する予約情報のリスト　最大10件まで格納する
	 * 
	 * @return 
	 */
	
	@GetMapping("/user-mypage")
	public String goToUserMyPage(
			@ModelAttribute("userSession") UserSession userSession,
			Model model) {
			
		if(this.userSession == null) {
			this.userSession = userSession;
		}

		List<UserReservationInfomation> reservationList 
			= userReservationService.getUserReservationListById(userSession.getUserInfo());
		userSession.setReservationList(reservationList);
		
		
		Page page = new Page();
		page.config((long)reservationList.size());
		
		userSession.setPage(page);
		
//		表示する10件を格納する
		List<UserReservationInfomation> displayReservationList =
				getDisplayReservationList(page);
		
		
		model.addAttribute("page",userSession.getPage());
		model.addAttribute("displayReservationList",displayReservationList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		model.addAttribute("reservationList",userSession.getReservationList());
		return "view/user-mypage";
	}		
	
	@PostMapping("/user-mypage")
	public String reservationInfomationToUserMyPage(
			Model model) {
		
//		表示する10件を格納する
		List<UserReservationInfomation> displayReservationList =
				getDisplayReservationList(userSession.getPage());
		
		
		model.addAttribute("page",userSession.getPage());
		model.addAttribute("displayReservationList",displayReservationList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		model.addAttribute("reservationList",userSession.getReservationList());
		return "view/user-mypage";
	}		

//	前の一覧10件を表示するメソッド
	@GetMapping("/prev-user-mypage")
	public String displayPrevUserMyPage(
			Model model) {
		
//		現在のページをひとつ前に戻す
		userSession.getPage().goToPreviousPage();
		
//		表示する10件を格納する
		List<UserReservationInfomation> displayReservationList =
				getDisplayReservationList(userSession.getPage());
		
		
		model.addAttribute("page",userSession.getPage());
		model.addAttribute("displayReservationList",displayReservationList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		model.addAttribute("reservationList",userSession.getReservationList());
		return "view/user-mypage";
	}		
	
//	次の一覧10件を表示するメソッド
	@GetMapping("/next-user-mypage")
	public String displayNextUserMyPage(
			Model model) {
		
//		現在のページを次に進める
		userSession.getPage().goToNextPage();
		
//		表示する10件を格納する
		List<UserReservationInfomation> displayReservationList =
				getDisplayReservationList(userSession.getPage());
		
		
		model.addAttribute("page",userSession.getPage());
		model.addAttribute("displayReservationList",displayReservationList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		model.addAttribute("reservationList",userSession.getReservationList());
		return "view/user-mypage";
	}		
	
	@PostMapping("/reservation-infomation")
	public String displayReservationInfomation(
			@ModelAttribute UserReservationInfomation userReservationInfomation,
			Model model) {
		
		
		model.addAttribute("userReservatioInfomation",userReservationInfomation);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/reservation-info";
	}
	
	
	
//	定休日・予約の空き・人数のバリデーションメソッド
	
	public Map<String,String> checkReservationDateAndLimit(StoreView storeView,LocalDate reservationDate,int numOfPeople){
		Map<String,String> errors = new HashMap<>(); 
		
//		System.out.println("Today :" + LocalDateTime.now().toLocalDate());
//		System.out.println("reservationDate :" + reservationDate);
		
		for(DayOfWeek holiday:storeView.getHolidays()) {
			if(reservationDate.getDayOfWeek() == holiday) {
				errors.put("reservationDateError","定休日の為、選択ができない日付です。");
				break;
			}
		}
		
		if(reservationDate.isBefore(LocalDate.now().plusDays(1))
				|| reservationDate == null) {
			errors.put("reservationDateError","本日以降の日付を選択してください。");			
		}
		try {
			if(userReservationService.getReservationLimitAtDate(storeView, reservationDate) < 1){
				errors.put("reservationLimitError","選択した日程は予約上限に達しました。他の日付でお探しください。");			
			}			
		}catch(NullPointerException e) {
			
		}
		
		if(numOfPeople < 1){
			errors.put("NumOfPeople","人数は1人以上で入力してください。");			
		}
		return errors;
	}
	
	
//	一覧に表示する予約情報を取得するメソッド
	
	public List<UserReservationInfomation> getDisplayReservationList(Page page){
//	セッション内に保存してある予約情報リストを取得する
		List<UserReservationInfomation> reservationList = userSession.getReservationList();
		
		List<UserReservationInfomation> displayReservationList = new ArrayList<>();
		
		try {
			for(int i = page.offset();i < page.offset() + Page.OUTPUT_NUM ;i++) {
				displayReservationList.add(reservationList.get(i));
			}
		}catch(IndexOutOfBoundsException e){
			e.printStackTrace();
		}
		
		return displayReservationList;
		
	}
}

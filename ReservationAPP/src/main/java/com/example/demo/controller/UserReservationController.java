package com.example.demo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Reservation;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.service.UserReservationService;
import com.example.demo.session.UserSession;

@Controller
@RequestMapping("/reservation/reserve")
public class UserReservationController {
	
	private final UserReservationService userReservationService;
	private final UserSession userSession;
	
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
			@ModelAttribute("userInfo") UserInfo userInfo,
			@ModelAttribute("storeView") StoreView storeView,
			Model model) {
		
		if(userSession.getStoreView() != null) {
			userSession.clearStoreViewData();
		}
		
		userSession.setUserInfo(userInfo);
		userSession.setStoreView(storeView);
		
		model.addAttribute("storeView",userSession.getStoreView());
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/reservation-input";
	}
	
	@PostMapping("/store-available-days")
	public String storeAvailableDays(
			@RequestParam("selectDate") String selectDate,
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
			@RequestParam("atReservationDate") String selectDate,
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
				storeView,LocalDate.parse(selectDate),numOfPeople);
		
//		エラーが無ければ確認画面へ遷移
		if(errors.isEmpty()) {
			model.addAttribute("selectDate",selectDate);
			model.addAttribute("numOfPeople",numOfPeople);
			model.addAttribute("userInfo",userSession.getUserInfo());
			model.addAttribute("storeView",userSession.getStoreView());				
			return "view/reservation-confirm";
		}
		
		model.addAttribute("selectDate",selectDate);
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
			@RequestParam("atReservationDate") String atReservationDate,	
			@RequestParam("numOfPeople") String numOfPeople,	
			RedirectAttributes redirect,
			Model model
			) {
		
		try {
//			予約を送信する前に、もう一度予約が空いてるかを確認
			if(userReservationService.getReservationLimitAtDate(
					userSession.getStoreView(), LocalDate.parse(atReservationDate)) < 1) {
				
				throw new FailedInsertSQLException("予約数が上限に達してしまったため、他の日程で予約を入れてください。");
			}
			
//			予約情報をインスタンスへ格納して登録
			Reservation reservation = new Reservation(
					userSession.getUserInfo().getUserId(),
					userSession.getStoreView().getStoreId(),
					LocalDate.parse(atReservationDate),
					Integer.parseInt(numOfPeople)
				);
			
			userReservationService.submitReservation(reservation);
			userSession.setReservation(reservation);
		
			return "redirect:/reservation/reserve/reservation-complete";
			
		}catch(FailedInsertSQLException e) {
			e.getStackTrace();
			
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
	public String toStoreList(
			RedirectAttributes redirect) {
		
		userSession.clearStoreViewData();
		userSession.clearReservationData();
		
		redirect.addFlashAttribute("userInfo",userSession.getUserInfo());
		return "redirect:/reservation/views/open-store-list";
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
		
		if(userReservationService.getReservationLimitAtDate(storeView, reservationDate) < 1){
			errors.put("reservationLimitError","選択した日程は予約上限に達しました。他の日付でお探しください。");			
		}
		
		if(numOfPeople < 1){
			errors.put("NumOfPeople","人数は1人以上で入力してください。");			
		}
		return errors;
	}
	
	
}

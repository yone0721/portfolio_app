package com.example.demo.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserReservationService;
import com.example.demo.session.UserSession;

@Controller
@RequestMapping("/reservation/reserve")
public class ReservationController {
	
	private final UserReservationService userReservationService;
	private final UserSession userSession;
	
	@Autowired
	public ReservationController(UserReservationService userReservationService,UserSession userSession) {
		this.userReservationService = userReservationService;
		this.userSession = userSession;
	}
	
	/*
	 * 空き予約画面への遷移メソッド
	 * @param("storeView")	一覧から選択した店舗の情報を格納するエンティティ
	 * @param("userInfo")	セッションで保持しているユーザー情報
	 * * @return			空き予約確認画面への遷移
	 */
	
	@GetMapping("/store-available-days")
	public String storeAvailableDays(
			@ModelAttribute("userInfo") UserInfo userInfo,
			@ModelAttribute("storeView") StoreView storeView,			
			Model model) {
		userSession.setUserInfo(userInfo);
		
		model.addAttribute("storeView",storeView);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/reservation-input";
	}
	
	@PostMapping("/reserve-confirm")
	public String checkAvailableDays(
			@RequestParam("reservationDate") LocalDateTime reservationDate,
			@RequestParam("numOfPeople") int numOfPeople,
			@ModelAttribute StoreView storeView,
			Model model,
			RedirectAttributes redirect
			) {
		
		
		
		
		model.addAttribute("userInfo",userSession.getUserInfo());
		model.addAttribute("storeView",storeView);
		return "view/reservation-input";
	}	
}

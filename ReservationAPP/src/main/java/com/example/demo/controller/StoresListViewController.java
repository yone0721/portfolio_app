package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.SearchCriteria;
import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.StoresListViewService;
import com.example.demo.session.UserSession;

@Controller
@RequestMapping("/reservation/views")
public class StoresListViewController {
	
	private final StoresListViewService storesListViewService;
	private final UserSession userSession;

	public StoresListViewController(StoresListViewService storesListViewService,
			UserSession userSession) {
		this.storesListViewService = storesListViewService;
		this.userSession = userSession;
	}
	
	/*
	 * ユーザーログイン成功時に、店舗画面一覧が初期画面として表示される
	 * return 利用者情報(storeInfo)と取得した店舗一覧情報(storeViewList)をビューに送る
	 */
	
	@GetMapping("/user-login-complete")
	public String userLoginComplete(
				@ModelAttribute("userInfo") UserInfo userInfo,
				RedirectAttributes redirect
			) {
		
		userSession.setUserInfo(userInfo);
		redirect.addFlashAttribute(userInfo);
		return "redirect:/reservation/views/store-list";
	}
		
	/*
	 * 店舗一覧画面への遷移メソッド
	 * @param("storeViewList")	DBから取得した店舗データ10件分までのリストを格納
	 * @param("userInfo")		セッションで保持しているユーザー情報
	 * * @return				店舗一覧画面への遷移
	 */
	
	@GetMapping("/store-list")
	public String storesListView(
			Model model) {
		
		List<StoreView> storeViewList = storesListViewService.getStoresList();
		
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/stores-index";
	}
	
	@GetMapping("/search-store-list")
	public String searchesStoresListView(
			SearchCriteria searchCriteria,
			Model model) {
		
		List<StoreView> storeViewList = storesListViewService.getStoresList();
		
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/stores-index";
	}

	/*
	 * UserReservationControllerへ遷移する時のメソッド
	 * param storeView 		一覧から選択した店舗の情報が格納されているエンティティクラス
	 * param userInfo 		利用者の情報のエンティティクラス
	 */
	
	@PostMapping("/to-reservation-controller")
	public String toReservationController(		
			@ModelAttribute StoreView storeView,
			RedirectAttributes redirect) {
		
		redirect.addFlashAttribute("storeView",storeView);
		redirect.addFlashAttribute("userInfo",userSession.getUserInfo());
		return "redirect:/reservation/reserve/store-available-days";
	}	
	
	/*
	 * UserReservationControllerからUserInfoを受け取り
	 * 店舗情報一覧画面に戻すメソッド
	 */
	@GetMapping("/open-store-list")
	public String toStoreList(
			@ModelAttribute("userInfo") UserInfo userInfo,
			RedirectAttributes redirect
			) {
		
		System.out.println("open-store-list:"+ userInfo);
		
		userSession.setUserInfo(userInfo);
		return "redirect:/reservation/views/store-list";
	}

	@PostMapping("/user-mypage")
	public String toUserMyPage(@ModelAttribute UserInfo userInfo,
			RedirectAttributes redirect) {
		
		
		redirect.addFlashAttribute("userInfo",userSession.getUserInfo());
		return "redirect:/reservation/reserve/user-mypage";
	}

}

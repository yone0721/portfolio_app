package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		
		UserInfo userInfo = userSession.getUserInfo();
		
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userInfo);
		return "view/stores-index";
	}
	
	@PostMapping("/transition-reserve")
	public String transitionReserve(
			@ModelAttribute StoreView storeView,
			RedirectAttributes redirect
			) {
		
		redirect.addFlashAttribute("storeView",storeView);
		redirect.addFlashAttribute("userInfo",userSession.getUserInfo());
		return "redirect:/reservation/reserve/store-available-days";
	}
	
}

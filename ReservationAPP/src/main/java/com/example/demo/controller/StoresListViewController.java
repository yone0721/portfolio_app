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
	
	public StoresListViewController(StoresListViewService storesListViewService) {
		this.storesListViewService = storesListViewService;
		this.userSession = new UserSession();
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
		System.out.println("セッション：" + userSession.getUserInfo());
		redirect.addFlashAttribute(userInfo);
		return "redirect:/reservation/views/store-list";
	}
	
	
	
	@GetMapping("/store-list")
	public String storesListView(		
			Model model) {
		
		System.out.println("セッション：" + userSession.getUserInfo());
		
		List<StoreView> storeViewList = storesListViewService.getStoresList();
		
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/stores-index";
	}
	
	@PostMapping("/store-available-days")
	public String storeAvailableDays(		
			@ModelAttribute StoreView storeView,			
			Model model) {
		
		System.out.println("ｱｳﾞｪｲﾗﾎﾞｫ");
		
		
		model.addAttribute("storeView",storeView);
		model.addAttribute("userInfo",userSession.getUserInfo());
		return "view/store-available-days";
	}
}

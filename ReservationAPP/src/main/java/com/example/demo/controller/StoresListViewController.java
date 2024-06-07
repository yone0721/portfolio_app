package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.StoreView;
import com.example.demo.entity.UserInfo;
import com.example.demo.service.StoresListViewService;

@Controller
@RequestMapping("/reservation/views")
public class StoresListViewController {
	
	private StoresListViewService storesListViewService;
	
	public StoresListViewController(StoresListViewService storesListViewService) {
		this.storesListViewService = storesListViewService;
	}
	
	@GetMapping("/store-list")
	public String storesListView(
			@ModelAttribute("userInfo") UserInfo userInfo,			
			Model model) {
		
		List<StoreView> storeViewList = storesListViewService.getStoresList();
		
		model.addAttribute("storesViewList",storeViewList);
		model.addAttribute("userInfo",userInfo);
		return "view/stores-index";
	}
}

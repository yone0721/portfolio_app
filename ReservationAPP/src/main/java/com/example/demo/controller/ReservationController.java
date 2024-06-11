package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.UserInfo;
import com.example.demo.service.UserInfoService;

@Controller
@RequestMapping("/reservation/")
public class ReservationController {
	
	private final UserInfoService userInfoService;
	
	@Autowired
	public ReservationController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	@GetMapping("/")
	public String storeListIndex(
			@ModelAttribute("userInfo") UserInfo userInfo,
			Model model
			) {

		return "view/";
		
	}	
}

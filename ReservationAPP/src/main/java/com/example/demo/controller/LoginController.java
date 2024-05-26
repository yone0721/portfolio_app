package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/reservation")
public class LoginController {
	
	@GetMapping("/login")
	public String login() {
		
		return "view/login";
	}
	
	@PostMapping("/register")
	public String resgiter(@RequestParam("position") String position,Model model,
			UserInfoForm userInfoForm) {
		
		model.addAttribute("userInfoForm",userInfoForm);
		model.addAttribute("position",position);
		return "view/register";
	}
	
	@PostMapping("/user-confirm")
	public String registerConfirm(@Validated UserInfoForm userInfoForm,
			BindingResult result,Model model) {
		
		model.addAttribute("position","user");
		
		if(result.hasErrors()) {
			
			model.addAttribute("userInfoForm",userInfoForm);
			return "view/register";
		}
		
		model.addAttribute("userInfoForm",userInfoForm);
		return "view/user-confirm";
	}	
}

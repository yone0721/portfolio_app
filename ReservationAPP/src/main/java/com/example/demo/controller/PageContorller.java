package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.entity.LoginForm;

@Controller
@RequestMapping("/reservation/pages")
public class PageContorller {
	
	@GetMapping("/login-top")
	public String loginTop(LoginForm loginForm,Model model) {
		
		model.addAttribute("loginForm",loginForm);
		model.addAttribute("userchecked","checked");
		return "view/login";
	}
	
	@GetMapping("/register-complete")
	public String regsiterComplete(@ModelAttribute("complete") String complete,Model model) {
		
		model.addAttribute("complete",complete);
		return "view/register-complete";
	}
}

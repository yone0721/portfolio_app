package com.example.demo.controller;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.LoginForm;
import com.example.demo.entity.UserInfo;
import com.example.demo.exception.UserInfoNotFoundException;
import com.example.demo.service.UserInfoService;

@Controller
@RequestMapping("/reservation/user-login")
public class LoginUserController {
	private final UserInfoService userInfoService;

	
	public LoginUserController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	/*
	 * @param mail 			 ログイン時に入力されたメールアドレス
	 * @param password		 ログイン時に入力されたパスワード
	 * 
	 */
	
	@PostMapping("/authonicate")
	public String login(
			@RequestParam("mail") String mail,
			@RequestParam("password") String password,
			Model model,RedirectAttributes redirect) {
			
			UserInfo userInfo;
			try {
				userInfo = authenticateUser(mail,password);
				
				if(!(userInfo == null)) {
//					System.out.println("ユーザーメール："+ userInfo.getMail());
					
					
					redirect.addFlashAttribute("userInfo",userInfo);
					return "redirect:/reservation/views/user-login-complete";
				}
				
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			} catch(UserInfoNotFoundException e) {
				e.printStackTrace();
			}
			
			LoginForm loginForm = new LoginForm(mail,password);
			model.addAttribute("loginError","メールアドレスまたはパスワードに誤りがあります。");
			model.addAttribute("userchecked","checked");
			model.addAttribute("loginForm",loginForm);
			return "view/login";
			
	}
	
	public UserInfo authenticateUser(String inputMail,String inputPassword) throws NoSuchAlgorithmException {
		UserInfo userInfo = userInfoService.checkLoginForm(inputMail);
		
		String hashPass = this.userInfoService.hashPass(inputPassword);
		
			
		if(inputMail.trim().equals(userInfo.getMail().trim()) &&
				hashPass.trim().equals(userInfo.getUserPassword().trim())) {
			return userInfo;
		
		}
		return null;	
	}
	
	@GetMapping("/login-complete")
	public String loginSuccess(
			@ModelAttribute("userInfo") UserInfo userInfo,
			Model model
			) {
		
		return "view/login-complete";
	}
}

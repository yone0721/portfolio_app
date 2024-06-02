package com.example.demo.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;

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
import com.example.demo.service.UserInfoService;

@Controller
@RequestMapping("/reservation/user-login")
public class LoginUserController {
	private final UserInfoService userInfoService;
	
	public LoginUserController(UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	@PostMapping("/authonicate")
	public String login(
			@RequestParam("mail") String mail,
			@RequestParam("password") String password,
			Model model,RedirectAttributes redirect) {
		
			LoginForm loginForm = new LoginForm(mail,password);

			List<UserInfo> userInfoList = userInfoService.getAll();
			int userId;
			
			try {
				userId = authonicateuser(loginForm.getMail(),loginForm.getPassword());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				userId = 0;
			}
			
			if(userId > 0) {
				UserInfo userInfo = userInfoService.getById(userId);
				if(!(userInfo ==null)) {
					redirect.addFlashAttribute("userInfo",userInfo);
					return "redirect:/reservation/user-login/login-complete";
				}
			}
			model.addAttribute("loginError","メールアドレスまたはパスワードに誤りがあります。");
			model.addAttribute("userchecked","checked");
			model.addAttribute("loginForm",loginForm);
			return "view/login";
	}
	
	public int authonicateuser(String inputMail,String inputPassword) throws NoSuchAlgorithmException {
		List<UserInfo> userList = userInfoService.getAll();
		
		String hashPass = this.userInfoService.hashPass(inputPassword);
		
		System.out.println("入力したメール：" + inputMail);
		System.out.println("入力したパスワード：" + hashPass);
		for(UserInfo user:userList) {
			System.out.println("-------------------------");
			System.out.println("DBのメール：" + user.getMail());
			System.out.println("DBのパスワード：" + user.getUserPassword());
			
			if(inputMail.trim().equals(user.getMail().trim()) && hashPass.trim().equals(user.getUserPassword().trim())) {
				return user.getUserId();
			}	
		}
		return 0;	
	}
	
	@GetMapping("/login-complete")
	public String loginSuccess(
			@ModelAttribute("userInfo") UserInfo userInfo,
			Model model
			) {
		
		return "view/login-complete";
	}
}

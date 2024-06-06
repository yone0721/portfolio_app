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
import com.example.demo.entity.StoreInfo;
import com.example.demo.service.StoreInfoService;

@Controller
@RequestMapping("/reservation/store-login")
public class LoginStoreController {
	private final StoreInfoService storeInfoService;
	
	public LoginStoreController(StoreInfoService storeInfoService) {
		this.storeInfoService = storeInfoService;
	}
	
	@PostMapping("/authonicate")
	public String login(
			@RequestParam("mail") String mail,
			@RequestParam("password") String password,
			Model model,RedirectAttributes redirect) {

			StoreInfo storeInfo = storeInfoService.checkLoginForm(mail);
			
			System.out.println("---------------");
			System.out.println("storeId:" + storeInfo.getStoreId());
			
			if(storeInfo == null) {
				LoginForm loginForm = new LoginForm(mail,password);
				model.addAttribute("loginError","メールアドレスまたはパスワードに誤りがあります。");
				model.addAttribute("storechecked","checked");
				model.addAttribute("loginForm",loginForm);
				return "view/login";
			}
			redirect.addFlashAttribute("storeInfo",storeInfo);
			return "redirect:/reservation/store-login/login-complete";
	}
	
	@GetMapping("/login-complete")
	public String loginSuccess(
			@ModelAttribute("storeInfo") StoreInfo storeInfo,
			Model model) {
		
		model.addAttribute("storeInfo",storeInfo);
		return "view/login-complete";
	}
	
	
	public StoreInfo authonicateStore(String inputMail,String inputPassword) throws NoSuchAlgorithmException {
		StoreInfo checkAccount = storeInfoService.checkLoginForm(inputMail);
		
		String hashPass = this.storeInfoService.hashPass(inputPassword);
		
		System.out.println("入力したメール：" + inputMail);
		System.out.println("入力したパスワード：" + hashPass);
		
		System.out.println("-------------------------");
		System.out.println("DBのメール：" + checkAccount.getMail());
		System.out.println("DBのパスワード：" + checkAccount.getStorePassword());
			
		if(inputMail.trim().equals(checkAccount.getMail().trim()) 
					&& hashPass.trim().equals(checkAccount.getStorePassword().trim())) {
			System.out.println("認証成功");
			return checkAccount;
		}
		return null;
	}

	

	
	
}

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
			
			LoginForm loginForm = new LoginForm(mail,password);

			List<StoreInfo> storeInfoList = storeInfoService.getAllStore();
			int storeId;
			try {
				storeId = authonicateStore(loginForm.getMail(),loginForm.getPassword());
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
				storeId = 0;
			}
			
			System.out.println("---------------");
			System.out.println("storeId:" + storeId);
			
			if(storeId > 0) {
				StoreInfo storeInfo = storeInfoService.getStoreById(storeId);
				
				if(!(storeInfo == null)) {
					redirect.addFlashAttribute("storeInfo",storeInfo);
					return "redirect:/reservation/store-login/login-complete";
				}
			}	
			
			model.addAttribute("loginError","メールアドレスまたはパスワードに誤りがあります。");
			model.addAttribute("storechecked","checked");
			model.addAttribute("loginForm",loginForm);
			return "view/login";
	}
	
	@GetMapping("/login-complete")
	public String loginSuccess(
			@ModelAttribute("storeInfo") StoreInfo storeInfo,
			Model model) {
		
		model.addAttribute("storeInfo",storeInfo);
		return "view/login-complete";
	}
	
	
	public int authonicateStore(String inputMail,String inputPassword) throws NoSuchAlgorithmException {
		List<StoreInfo> storeList = storeInfoService.getAllStore();
		
		String hashPass = this.storeInfoService.hashPass(inputPassword);
		
		System.out.println("入力したメール：" + inputMail);
		System.out.println("入力したパスワード：" + hashPass);
		
		for(StoreInfo store:storeList) {
			System.out.println("-------------------------");
			System.out.println("DBのメール：" + store.getMail());
			System.out.println("DBのパスワード：" + store.getStorePassword());
			
			if(inputMail.trim().equals(store.getMail().trim()) && hashPass.trim().equals(store.getStorePassword().trim())) {
				System.out.println("認証成功");
				return store.getStoreId();
			}	
		}
		return 0;
	}

	

	
	
}

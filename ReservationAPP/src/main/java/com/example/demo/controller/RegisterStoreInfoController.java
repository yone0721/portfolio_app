package com.example.demo.controller;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.StoreInfo;
import com.example.demo.entity.StoreInfoForm;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.service.StoreInfoService;

@Controller
@RequestMapping("/reservation/store-register")
public class RegisterStoreInfoController {
	
	private final StoreInfoService storeInfoService;
	@Autowired
	public RegisterStoreInfoController(StoreInfoService storeInfoService) {
		this.storeInfoService = storeInfoService;
	}
	
	@PostMapping("/register")
	public String resgiter(@RequestParam("position") String position,
			StoreInfoForm storeInfoForm,
			Model model) {
		
		if(storeInfoForm != null && position == "store") {
			
			return "error/register-illegality-error";
			
		}
		
		model.addAttribute("storeInfoForm",storeInfoForm);
		model.addAttribute("position",position);
		return "view/register";
	}
	
	@PostMapping("/store-confirm")
	public String registerConfirm(@Validated StoreInfoForm storeInfoForm,
			BindingResult result,Model model) {
		
		
		if(result.hasErrors()) {
			System.out.println("Password：" + storeInfoForm.getStorePassword());
			
			List<FieldError> errors = result.getFieldErrors();
			
			for(FieldError error:errors) {
				System.out.println(error.getField());
				System.out.println(error.getCode());
				System.out.println(error.getDefaultMessage());
			}
			
			model.addAttribute("position","store");
			model.addAttribute("storeInfoForm",storeInfoForm);
			return "view/register";
		}
		
		model.addAttribute("position","store");
		model.addAttribute("storeInfoForm",storeInfoForm);
		return "view/confirm";
	}	

	@PostMapping("/store-complete")
	public String registerComplete(@Validated StoreInfoForm storeInfoForm,
			BindingResult result,Model model,RedirectAttributes redirect) {
		System.out.println("Password：" + storeInfoForm.getStorePassword());
		String hashStorePassword;
		
		try {
			hashStorePassword = storeInfoService.hashPass(storeInfoForm.getStorePassword());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			
			model.addAttribute("storeInfoForm",storeInfoForm);
			model.addAttribute("position","store");
			model.addAttribute("caution","パスワードのハッシュ化に失敗しました。。");
			return "view/register";
		}
		
			StoreInfo storeInfo = new StoreInfo(
					storeInfoForm.getStoreName(),
					storeInfoForm.getPostCode(),
					storeInfoForm.getCity(),
					storeInfoForm.getMunicipalities(),
					storeInfoForm.getStreetAddress(),
					storeInfoForm.getBuilding(),
					storeInfoForm.getMail(),
					storeInfoForm.getPhone(),
					hashStorePassword,
					storeInfoForm.getStoreReservationLimit(),
					storeInfoForm.getIsOpened(),
					storeInfoForm.getIsClosed(),
					LocalDateTime.now()
					);

		try {
			storeInfoService.register(storeInfo);	
			redirect.addFlashAttribute("complete","登録が完了しました。");
			return "redirect:/reservation/pages/register-complete";
			
		}catch(FailedInsertSQLException e) {
			model.addAttribute("storeInfoForm",storeInfoForm);
			model.addAttribute("position","store");
			model.addAttribute("caution","データが既に存在しているか不正です。");
			return "view/register";
		}
}
}

package com.example.demo.controller;

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

import com.example.demo.entity.UserInfo;
import com.example.demo.exception.FailedInsertSQLException;
import com.example.demo.service.UserInfoService;

@Controller
@RequestMapping("/reservation/user-register")
public class ResgisterUserInfoController {
	
	private final UserInfoService userInfoService;
	
	@Autowired
	public ResgisterUserInfoController (UserInfoService userInfoService) {
		this.userInfoService = userInfoService;
	}
	
	@PostMapping("/register")
	public String resgiter(@RequestParam("position") String position,
			UserInfoForm userInfoForm,
			Model model) {
		
		if(userInfoForm != null && position == "store") {
			
			return "error/register-illegality-error";
			
		}
		
		model.addAttribute("userInfoForm",userInfoForm);
		model.addAttribute("position",position);
		return "view/register";
	}
	
	@PostMapping("/user-confirm")
	public String registerConfirm(@Validated UserInfoForm userInfoForm,
			BindingResult result,Model model) {
		
		if(result.hasErrors()) {
			System.out.println("Password：" + userInfoForm.getUserPassword());
			
			List<FieldError> errors = result.getFieldErrors();
			
			for(FieldError error:errors) {
				System.out.println(error.getField());
				System.out.println(error.getCode());
				System.out.println(error.getDefaultMessage());
			}
			
			model.addAttribute("position","user");
			model.addAttribute("userInfoForm",userInfoForm);
			return "view/register";
		}
		
		model.addAttribute("position","user");
		model.addAttribute("userInfoForm",userInfoForm);
		return "view/confirm";		
	}	
	
		@PostMapping("/user-complete")
		public String registerComplete(	@Validated UserInfoForm userInfoForm,
				BindingResult result,Model model,RedirectAttributes redirect) {
			System.out.println("Password：" + userInfoForm.getUserPassword());
			
			UserInfo userInfo = new UserInfo(
					userInfoForm.getMail(),
					userInfoForm.getUserName(),
					userInfoForm.getUserNameFurigana(),
					userInfoForm.getPhone(),
					userInfoForm.getUserPassword(),
					userInfoForm.getPostCode(),
					userInfoForm.getCity(),
					userInfoForm.getMunicipalities(),
					userInfoForm.getStreetAddress(),
					userInfoForm.getBuilding(),
					LocalDateTime.now()
					);
			
			try {
				userInfoService.register(userInfo);	
				redirect.addFlashAttribute("complete","登録が完了しました。");
				return "redirect:/reservation/pages/register-complete";
				
			}catch(FailedInsertSQLException e) {
				model.addAttribute("userInfoForm",userInfoForm);
				model.addAttribute("position","user");
				model.addAttribute("caution","データが既に存在しているか不正です。");
				return "view/register";
			}
		}
	
}

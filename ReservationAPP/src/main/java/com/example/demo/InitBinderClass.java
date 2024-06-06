package com.example.demo;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

public class InitBinderClass {
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// 未入力の値をnullに変換してくれる
		binder.registerCustomEditor(String.class,new StringTrimmerEditor(true));
	}
	
}

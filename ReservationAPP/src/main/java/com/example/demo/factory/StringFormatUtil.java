package com.example.demo.factory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * 整数のみの電話番号や郵便番号にハイフンを加えるクラス
 */

public class StringFormatUtil {
	
	/*
	 * 郵便番号にハイフンを追加するメソッド
	 */
	
	public static String formatZipCodeWithHyphens(String zipCode) {
		String checkZipCode = "^[0-9]{7}";
		Pattern zipCodePattern = Pattern.compile(checkZipCode);
		Matcher zipCodeMatcher = zipCodePattern.matcher(zipCode);
		
		if(!(zipCodeMatcher.find())) {
			return zipCode;
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(zipCode.substring(0,3));
		sb.append("-");		
		sb.append(zipCode.substring(3,7));
		
		return sb.toString();
	}
	
	/*
	 * 電話番号にハイフンを挿入するメソッド
	 * 今回は簡単に3パターン作成する
	 */
	public static String formatPhoneNumberWithHyphens(String phoneNumber) {
		
		String checkPhoneNumber = "^[0-9]{10,12}";
		Pattern phoneNumberPattern = Pattern.compile(checkPhoneNumber);
		Matcher phoneNumberMatcher = phoneNumberPattern.matcher(phoneNumber);
		
		if(!(phoneNumberMatcher.find())) {
			return phoneNumber;
		}
		
		StringBuilder sb = new StringBuilder();
		
		/*
		 * 携帯電話番号か確認する為のMatcher
		 */
		
		String startsWithMobileNumber = "^(0(7|8|9)0)[0-9]*";
		Pattern mobilePattern = Pattern.compile(startsWithMobileNumber);
		Matcher mobileMatcher = mobilePattern.matcher(phoneNumber);
		
		if(mobileMatcher.find()) {
			sb.append(phoneNumber.substring(0,3));
			sb.append("-");
			sb.append(phoneNumber.substring(3,7));
			sb.append("-");
			sb.append(phoneNumber.substring(7));
			
			return sb.toString();
		}
		
		/*
		 * 東京都23区・狛江市・調布市の一部地域のMatcher
		 */
		
		String startsWithTokyoAreaCode = "^(03)[0-9]*";
		Pattern TokyoAreaPattern = Pattern.compile(startsWithTokyoAreaCode);
		Matcher TokyoAreaMatcher = TokyoAreaPattern.matcher(phoneNumber);
		
		if(TokyoAreaMatcher.find()) {
			sb.append(phoneNumber.substring(0,2));
			sb.append("-");
			sb.append(phoneNumber.substring(2,6));
			sb.append("-");
			sb.append(phoneNumber.substring(6));
			
			return sb.toString();
		}
		
		/*
		 * 東京都23区・狛江市・調布市の一部地域のMatcher
		 */
		
		String startsWithOtherAreaCode = "^(0[0-9]{2,3})[0-9]*";
		Pattern OtherAreaPattern = Pattern.compile(startsWithOtherAreaCode);
		Matcher OtherAreaMatcher = OtherAreaPattern.matcher(phoneNumber);
		
		if(OtherAreaMatcher.find()) {
			sb.append(phoneNumber.substring(0,4));
			sb.append("-");
			sb.append(phoneNumber.substring(4,6));
			sb.append("-");
			sb.append(phoneNumber.substring(6));
			
			return sb.toString();
		}
		return phoneNumber;
	}
	
}

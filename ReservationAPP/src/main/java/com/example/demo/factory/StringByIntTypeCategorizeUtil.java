package com.example.demo.factory;

public class StringByIntTypeCategorizeUtil {
	
	public static int changeKeyToInt(final String key) {
	
		switch(key) {
			case "howToSearch" ->{ return 1;}
			case "keywords" ->{ return 2;}
			case "cities" ->{ return 3;}
			case "dayOfWeeks" ->{ return 4;}
			default -> { return 0;}
		}
	}
	
	public static String changeIntToString(final int key) {
		
		switch(key) {
			case 1 ->{ return "howToSearch";}
			case 2 ->{ return "keywords";}
			case 3 ->{ return "cities";}
			case 4 ->{ return "dayOfWeeks";}
			default -> { return null;}
		}	
	}
}

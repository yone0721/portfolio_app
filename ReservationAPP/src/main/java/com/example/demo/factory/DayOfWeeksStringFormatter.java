package com.example.demo.factory;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

public class DayOfWeeksStringFormatter {
	
	public static List<String> getJPDayOfWeeks(List<DayOfWeek> dayOfWeeks){
		List<String> toJpNamesList = new ArrayList<>();
		
		dayOfWeeks.stream().forEach(dayOfWeek -> toJpNamesList.add(formatStringTodayOfWeek(dayOfWeek)));
		
		return toJpNamesList;
	}
	
	public static String formatStringTodayOfWeek(DayOfWeek dayOfWeek) {
		
		return switch(dayOfWeek.getValue()){
					case 1 -> "月曜";		// DayOfWeek.valueOf()：MONDAY
					case 2 -> "火曜";		// DayOfWeek.valueOf()：TUSEDAY
					case 3 -> "水曜"; 	// DayOfWeek.valueOf()：WEDNESDAY
					case 4 -> "木曜"; 	// DayOfWeek.valueOf()：THURSEDAY
					case 5 -> "金曜"; 	// DayOfWeek.valueOf()：FRIDAY
					case 6 -> "土曜"; 	// DayOfWeek.valueOf()：SATURDAY
					case 7 -> "日曜"; 	// DayOfWeek.valueOf()：SUNDAY
					default -> null;
				};
		
	}
}

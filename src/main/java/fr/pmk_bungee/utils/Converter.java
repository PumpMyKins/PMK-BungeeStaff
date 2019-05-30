package fr.pmk_bungee.utils;

import java.text.SimpleDateFormat;

public class Converter {

	public static String milliToDayHourMinuteSecond(long millis) {
		
		long second = millis / 1000;
		long minutes = second / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		
		String toReturn = days +" jour(s), "+hours % 24+" heure(s), "+minutes % 60+" minutes, "+second % 60+" secondes";
		System.out.println(toReturn);
		return toReturn;
	}
	
	public static String dateConv(java.util.Date date) {
		
		java.util.Date dt = date;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(dt);
	}
}

package fr.pmk_bungee.utils;

public class Converter {

	public static String milliToDayHourMinuteSecond(long millis) {
		
		long second = millis / 1000;
		long minutes = second / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		
		return days +" jour(s), "+hours % 24+" heure(s), "+minutes % 60+" minutes, "+second % 60+" secondes";
	}
}

package fr.pumpmybstaff.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converter {

	public static String milliToDayHourMinuteSecond(long millis) {
		
		long seconds = millis / 1000;
		long minutes = seconds / 60;
		long hours = minutes / 60;
		long days = hours / 24;
		
		String toReturn = "";
		
		if(days != 0)
			toReturn = toReturn.concat(days + " jour(s), ");
		if(hours%24 != 0)
			toReturn = toReturn.concat(hours % 24+" heure(s), ");
		if(minutes%60 != 0)
			toReturn = toReturn.concat(minutes % 60+" minutes, ");
		if(seconds%60 != 0)
			toReturn = toReturn.concat(seconds % 60+" secondes");
		
		return toReturn;
	}
	
	public static String dateConv(Date date) {
		
		Date dt = date;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		return sdf.format(dt);
	}
}

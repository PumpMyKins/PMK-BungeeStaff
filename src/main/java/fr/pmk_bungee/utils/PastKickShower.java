package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;

public class PastKickShower {

	public static List<PastKick> listPastKick(String playername) throws ClassNotFoundException, SQLException, NullPointerException {
		List<PastKick> pastKickList = new ArrayList<PastKick>();
		
		ResultSet rs = Main.getMySQL().getResult("SELECT * FROM PastBungeeKicks WHERE userID = '" + getUserID(playername) + "'");
		while(rs.next()) {
			
			PastKick pastKick = new PastKick();
			pastKick.setKickAt(rs.getDate("kickAt"));
			pastKick.setKickBy(rs.getInt("kickBy"));
			pastKick.setPlayername(getUsername(rs.getInt("userID")));
			pastKick.setKickReason(rs.getString("kickReason"));
			pastKickList.add(pastKick);
			
		}
		return pastKickList;
	}
	
	private static  int getUserID(String playername) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT userID FROM MinecraftPlayer WHERE username = '" + playername + "'");
			if(id.next()) {

				int userID = id.getInt("userID");
				return userID;

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		return -1;
	}

	public static  String getUsername(int userID) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT username FROM MinecraftPlayer WHERE userID = '" 
					+ userID 
					+ "'");

			if(id.next()) {

				String username = id.getString("username");
				return username;
			}

		} catch (SQLException e) {

			e.printStackTrace(); 
		} return "";
	}
	
}

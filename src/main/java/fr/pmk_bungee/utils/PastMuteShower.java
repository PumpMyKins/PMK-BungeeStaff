package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;

public class PastMuteShower {

	public static List<PastMute> listPastMute(String playername) throws ClassNotFoundException, SQLException, NullPointerException {
		List<PastMute> pastMuteList = new ArrayList<PastMute>();
		
		ResultSet rs = Main.getMySQL().getResult("SELECT * FROM PastBungeeMutes WHERE userID = '" + getUserID(playername) +"'");
		while(rs.next()) {
			
			PastMute pastMute = new PastMute();
			pastMute.setMuteAt(rs.getDate("muteAt"));
			pastMute.setMuteBy(rs.getInt("muteBy"));
			pastMute.setPlayername(getUsername(rs.getInt("userID")));
			pastMute.setMuteReason(rs.getString("muteReason"));
			pastMuteList.add(pastMute);
		}
		return pastMuteList;
		
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

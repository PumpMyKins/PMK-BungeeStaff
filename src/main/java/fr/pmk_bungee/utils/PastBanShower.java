package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;

public class PastBanShower {
	
	public static List<PastBan> listPastBan(String playername) throws ClassNotFoundException, SQLException, NullPointerException {
		List<PastBan> pastBanList = new ArrayList<PastBan>();
		
		ResultSet rs = Main.getMySQL().getResult("SELECT * FROM PastBungeeBan WHERE userID = '" + getUserID(playername) + "'");
		while(rs.next()) {
			
			PastBan pastBan = new PastBan();
			pastBan.setPlayername(getUsername(rs.getInt("userID")));
			pastBan.setBanAt(rs.getDate("banAt"));
			pastBan.setBanBy(rs.getInt("banBy"));
			pastBan.setBanReason(rs.getString("banReason"));
			pastBanList.add(pastBan);
			
		}
		return pastBanList;
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

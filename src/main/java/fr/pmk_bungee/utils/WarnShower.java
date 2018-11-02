package fr.pmk_bungee.utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

@SuppressWarnings("unused")
public class WarnShower {

	public static  List<Warn> listWarn(String playername) throws ClassNotFoundException, SQLException, NullPointerException{
		List<Warn> warnList = new ArrayList<Warn>();

		ResultSet rs = Main.getMySQL().getResult("SELECT * FROM BungeeWarn WHERE userID = '" + getUserID(playername) + "'");
		while(rs.next()) {

			Warn warn = new Warn();
			warn.setPlayername(getUsername(rs.getInt("userID")));
			warn.setWarnAt(rs.getDate("warnAt"));
			warn.setWarnBy(rs.getInt("warnBy"));
			warn.setWarnReason(rs.getString("warnReason"));
			warnList.add(warn);
		}
		return warnList;
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

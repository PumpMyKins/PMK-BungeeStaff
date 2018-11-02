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

	private String playername;
	
	private ArrayList<Date> warnAt;
	private ArrayList<Integer> warnBy;
	private ArrayList<String> warnReason;
	private int warnNumber = 0;
	
	public WarnShower(String player) {
		this.playername = player;
		init();
	}
	public void init() {
		
		try {
			
			ResultSet rs = Main.getMySQL().getResult("SELECT * FROM BungeeWarn WHERE userID = '" + getUserID(playername) + "'");
			while(rs.next()) {
				
				this.warnAt.add(rs.getDate("warnAt"));
				this.warnBy.add(rs.getInt("warnBy"));
				this.warnReason.add(rs.getString("warnReason"));
				this.warnNumber+=1;
				
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
		} catch(NullPointerException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private int getUserID(String playername) {
		
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
	
	public String getUsername(int userID) {
		
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
	
	public String getPlayername() {
		return playername;
	}
	
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	
	public ArrayList<Date> getWarnAt() {
		return warnAt;
	}
	
	public void setWarnAt(ArrayList<Date> warnAt) {
		this.warnAt = warnAt;
	}
	
	public ArrayList<Integer> getWarnBy() {
		return warnBy;
	}
	
	public void setWarnBy(ArrayList<Integer> warnBy) {
		this.warnBy = warnBy;
	}
	
	public ArrayList<String> getWarnReason() {
		return warnReason;
	}
	
	public void setWarnReason(ArrayList<String> warnReason) {
		this.warnReason = warnReason;
	}
	
	public int getWarnNumber() {
		return warnNumber;
	}
	
	public void setWarnNumber(int warnNumber) {
		this.warnNumber = warnNumber;
	}

}

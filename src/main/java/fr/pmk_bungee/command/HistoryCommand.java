package fr.pmk_bungee.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class HistoryCommand extends Command {

	public HistoryCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
		sender.sendMessage(getHistoryList(getUserID(args[0])) +" ");
	}
private int getUserID(String playerName) {
		
		try {
			ResultSet id = Main.getMySQL().getResult("SELECT * FROM MinecraftPlayer WHERE username = '" 
					+ playerName 
					+ "'");
			
			if(id.next()) {
				
				int userID = id.getInt("userID");
				return userID;
			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return -1;
	}
	private String getUsername(int userID) {
		
		try {
			ResultSet id = Main.getMySQL().getResult("SELECT * FROM MinecraftPlayer WHERE userID = '" 
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
	@SuppressWarnings("null")
	private int getHistoryList(int userID) {
		
		List<String> banHistory = null;
		try {
			
			ResultSet rsHistory = Main.getMySQL().getResult("SELECT * FROM PastBungeeBan WHERE userID ='" 
					+ userID
					+ "'");
			while(rsHistory.next()) {
				
				banHistory.add(rsHistory.getInt("banID") +" ");
			}
			System.out.println("banHistory.size =" + banHistory.size());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return banHistory.size();
	}
}

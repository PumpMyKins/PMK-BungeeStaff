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
		List<String> banHistory = null;
		if(sender.hasPermission("bungeestaff.command.history.personnal") && sender.getName().toLowerCase() == args[0].toLowerCase() || sender.hasPermission("bungeestaff.command.history.player")) {
			
			int userID = 0;
			try {
				ResultSet id = Main.getMySQL().getResult("SELECT userID FROM MinecraftPlayer WHERE username = '" 
						+ args[0] 
						+ "'");
				
				if(id.next()) {
					
					userID = id.getInt("userID");
				
				}
			} catch (SQLException e) {
				
				e.printStackTrace();
			} 
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
			sender.sendMessage(banHistory.size()+ "");
		}
	}
}

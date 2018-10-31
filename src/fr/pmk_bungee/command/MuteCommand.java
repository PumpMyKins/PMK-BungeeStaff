package fr.pmk_bungee.command;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MuteCommand extends Command {

	public MuteCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender.hasPermission("bungeeban.command.mute")) {
			
			  if(args.length >= 4) {
				  String playerName = args[0];
				  String reason = "";
				  for(int i = 3; i <= args.length - 1; i++) {
					  
					  reason = reason + args[i] + " ";
					  Main.getConfigManager().save();
					  PlayerProfile profile = new PlayerProfile(playerName);
					  if(profile != null) {
						  
						  if(!profile.isMuted()) {
							  try {
								  
								  long seconds = Integer.parseInt(args[1]);
								  Main.TimeUnit unit = Main.TimeUnit.getByString(args[2]);
								  
								  if(unit != null) {
									  
									  seconds *= unit.getSeconds();
									  profile.setMute(reason, getUserID(sender.getName()), seconds);
									  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.mute.muted", new String[] { "{NAME}~" + playerName }));		
									  }
							  } catch (NumberFormatException e) {sender.sendMessage("An interal error occured");}
							  
						  } else {
							  
							  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_already_muted", new String[] { "{NAME}~" + playerName }));						  }
					  } else {
						  
						  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found"));
					  }
				  }
			  }
			  else {
				  
				  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.mute.syntax"));
				  }
		  } else {
			  
			  sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));
		  };
	  } 
	private int getUserID(String playerName) {
		
		try {
			ResultSet id = Main.getMySQL().getResult("SELECT * FROM MinecraftPlayer WHERE username = '" + playerName + "'");
			if(id.next()) {
				
				int userID = id.getInt("userID");
				return userID;
			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return -1;
	}
}

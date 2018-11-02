package fr.pmk_bungee.command;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		ProxiedPlayer player = (ProxiedPlayer) sender;
		// TODO Auto-generated method stub
		if(player.hasPermission("bungeestaff.command.kick")) {

			if(args.length > 1) {

				String playername = args[0];
				String reason = "";
				TextComponent kickReason = new TextComponent();
				String str = "";
				LocalDateTime now = LocalDateTime.now();
				java.sql.Date kickAt = java.sql.Date.valueOf(now.toLocalDate());	

				for(int i = 1; i <= args.length - 1; i++) {

					reason = reason + args[i] + " ";
				}
				List<String> msgs = Main.getConfigManager().getStringList("lang.kickmessage", new String[] { 
						"{NAME}~" + sender.getName(), 
						"{REASON}~" + reason, 

				});

				for (String line : msgs) {
					str = str + line + "\n";
				}

				kickReason.addExtra(str);
				kickRegister(playername, reason, sender.getName(), kickAt); 
						
				ProxyServer.getInstance().getPlayer(playername).disconnect(kickReason);

				sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.kicked", new String[] { "{NAME}~" + playername})));

			} else { sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.syntax")));}

		} else { sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));}
	}
	public boolean kickRegister(String userID, String reason, String kickBy, Date kickAt) {
		
		try {
			
			Main.getMySQL().update("INSERT INTO PastBungeeKicks(userID, kickAt, kickBy, kickReason) VALUES ('" 
					+ getUserID(userID) 
					+ "', '" 
					+ kickAt
					+ "','" 
					+ getUserID(kickBy) 
					+ "','" 
					+ reason
					+ "')");

		} catch(NullPointerException e) {
			return false;
		}
		return true;
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

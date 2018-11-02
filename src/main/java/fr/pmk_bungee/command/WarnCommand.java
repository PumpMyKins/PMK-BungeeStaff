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
import net.md_5.bungee.api.plugin.Command;

public class WarnCommand extends Command {

	public WarnCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender.hasPermission("bungeestaff.command.warn")) {

			if(args.length > 1) {

				String playername = args[0];
				String reason = "";
				String warnBy = sender.getName();
				LocalDateTime now = LocalDateTime.now();
				java.sql.Date warnAt = java.sql.Date.valueOf(now.toLocalDate());				
				for(int i = 1; i <= args.length - 1; i++) {

					reason+=args[i] + " ";
				}

				if(addWarn(playername, reason, warnBy, warnAt)) {

					List<String> msgs = Main.getConfigManager().getStringList("lang.warnmessage", new String[] { 
							"{NAME}~" + warnBy,
							"{REASON}~" + reason

					});
					for(String msg : msgs) {

						ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent(msg));
					}
					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.warn.succes", new String[] {
							"{NAME}~" + playername,

					})));
				} else {
					System.out.println("[PUMPMYSTAFF] ERREUR WARN_COMMAND_SQL_INSERT_REFUSE");
				}
			} else {sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.warn.syntax")));}
		} else {sender.sendMessage(new TextComponent(Main.PREFIX +Main.getConfigManager().getString("lang.errors.no_permissions")));}
	}
	public boolean addWarn(String playername, String warnReason, String warnBy, Date warnAt) {

		try {

			Main.getMySQL().update("INSERT INTO BungeeWarn(userID, warnAt, warnBy, warnReason) VALUES ('" 
					+ getUserID(playername) 
					+ "', '" 
					+ warnAt
					+ "','" 
					+ getUserID(warnBy) 
					+ "','" 
					+ warnReason
					+ "')");

		} catch(NullPointerException e) {
			return false;
		}
		return true;

	}

	private int getUserID(String playerName) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT userID FROM MinecraftPlayer WHERE username = '" + playerName + "'");
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

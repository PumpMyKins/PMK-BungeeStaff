package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnmuteCommand extends Command {

	public UnmuteCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings({ "deprecation", "unused" })
	@Override
	public void execute(final CommandSender sender, final String[] args) {
		if(sender.hasPermission("bungeeban.command.unmute")) {
			if(args.length == 1) {
				String playerName = args[0];
				PlayerProfile profile = new PlayerProfile(playerName);
				if(profile != null) {
					if(profile.isMuted()) {
						profile.unmute();
						sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.unmute.unmuted", new String[] { "{NAME}~" + playerName }));							  
					}
					else {
						sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_muted", new String[] { "{NAME}~" + playerName }));				  }
				}
				else {
					sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found"));			  }
			}
			else {
				sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.unmute.syntax"));
			}
		}
		else {
			sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));	  
		}
	}

}

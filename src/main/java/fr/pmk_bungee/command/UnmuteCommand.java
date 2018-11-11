package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class UnmuteCommand extends Command {

	public UnmuteCommand(String name) {
		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(final CommandSender sender, final String[] args) {
		
		if(sender.hasPermission("bungeestaff.command.unmute")) {
			
			if(args.length > 1) {
				
				Main.getConfigManager().save();

				String playername = args[0];
				PlayerSituation situation = new PlayerSituation(playername);
				if(situation != null) {
					if(!situation.getMuteList().isEmpty()) {
						if(PlayerSituation.isMuted(playername)) {
							PlayerSituation.unMute(playername);
							sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.unmute.unmuted", new String[] { "{NAME}~" + playername })));							  
						}
					} else {
						sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_muted", new String[] { "{NAME}~" + playername })));
					}
				} else {
					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found")));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.unmute.syntax")));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));	  
		}
	}
}

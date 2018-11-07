package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

public class HistoryCommand extends Command {

	public HistoryCommand(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		ProxyServer.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable() {

			public void run() {
				
				if(sender.hasPermission("bungeestaff.command.history")) {
					
					if(args.length > 1) {
						
						Main.getConfigManager().save();

						String playername = args[0];
						PlayerSituation situation = new PlayerSituation(playername);
						switch(args[1].toLowerCase()) {
						case "warn":
						
							if(!situation.getWarnList().isEmpty()) {
								//TODO show_warn
							}
							else {
								//TODO no_warn
							}
				            break;
						case "ban":
							if(!situation.getBanList().isEmpty()) {
								//TODO show_ban
							}
							else {
								//TODO no_ban
							}
							break;
						case "mute":
							if(!situation.getMuteList().isEmpty()) {
								//TODO show_mute
							}
							else {
								//TODO no_mute
							}
							break;

						case "kick":
							if(!situation.getKickList().isEmpty()) {
								//TODO show_kick
							}
							else {
								//TODO no_kick
							}
							break;
						case"all":
							if(!situation.getWarnList().isEmpty()) {
								//TODO show_warn
							}
							else {
								//TODO no_warn
							}
							if(!situation.getBanList().isEmpty()) {
								//TODO show_ban
							}
							else {
								//TODO no_ban
							}
							if(!situation.getMuteList().isEmpty()) {
								//TODO show_mute
							}
							else {
								//TODO no_mute
							}
							if(!situation.getKickList().isEmpty()) {
								//TODO show_kick
							}
							else {
								//TODO no_kick
							}
							break;

						default:
							//TODO syntax
						}
					} else {
						//TODO syntax
					}

				} else {
					//TODO no_permission
				}
			}
		});

	}

}

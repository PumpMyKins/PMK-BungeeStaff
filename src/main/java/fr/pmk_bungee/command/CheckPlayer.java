package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;

import net.md_5.bungee.api.plugin.Command;

public class CheckPlayer extends Command {

	public CheckPlayer(String string) {
		super(string);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
		ProxyServer.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable() {

			@SuppressWarnings({ "unused" })
			public void run() {
				
				if(sender.hasPermission("bungeestaff.command.check")) {
					
					if(args.length > 0) {
						
						PlayerSituation situation = new PlayerSituation(args[0]);
						if(!situation.getBanList().isEmpty()) {
							//TODO show_ban
						} else {
							//TODO no_ban
						}
						if(!situation.getMuteList().isEmpty()) {
							//TODO show_mute
						} else {
							//TODO no_mute
						}
						if(!situation.getKickList().isEmpty()) {
							//TODO show_kick
						} else {
							//TODO no_kick
						}
						if(!situation.getWarnList().isEmpty()) {
							//TODO show_warn
						} else {
							//TODO no_warn
						}
						
					} //TODO syntax_error
				} //TODO no_permission
				};
	});
	}
}
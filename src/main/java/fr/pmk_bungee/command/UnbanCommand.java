package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class UnbanCommand extends Command {

	public UnbanCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(final CommandSender sender, final String[] args) {
		
		if(sender.hasPermission("bungeestaff.command.unban")) {
			
			if(args.length > 1) {
				
				Main.getConfigManager().save();

				String playername = args[0];
				PlayerSituation situation = new PlayerSituation(playername);
				if(situation != null) {
					
					if(PlayerSituation.testBan(situation)) {
						
						situation.unban();
						
					} else {
						//TODO not_banned
					}
				} else {
					//TODO player_not_found
				}
			} else {
				//TODO syntax_error
			}
		} else {
			//TODO no_permission
		}
	}

}

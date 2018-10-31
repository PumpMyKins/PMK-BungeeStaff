package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

	public BanCommand(String name) {
		
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		
		if(sender.hasPermission("bungeestaff.command.ban")) {
			if(args.length >= 4) {
				
				String playerName = args[0];
				String reason = "";
				for(int i = 3; i <= args.length - 1; i++) {
					
					reason = reason + args[i] + " ";
					Main.getConfigManager().save();
					Profile profile = new Profile(playerName)
				
				}
			}
			
		}
	}

}

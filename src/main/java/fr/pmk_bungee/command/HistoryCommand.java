package fr.pmk_bungee.command;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PastPlayerProfile;
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
		
	if(args[0] != null) {
			String playername = args[0];
		if(sender.hasPermission("bungeestaff.command.history.personnal") && sender.getName().equals(playername) || sender.hasPermission("bungeestaff.command.history.player")) {

			if(args.length > 0) {
											
					PastPlayerProfile profile = new PastPlayerProfile(playername);
					if(profile != null) {
						
					sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.command.history.succes", new String[] { "{NAME}~" + args[0] }));
					
					if(profile.getPastBan().isEmpty()) {
						
						sender.sendMessage(Main.getConfigManager().getString("lang.command.history.no_ban"));
						
					} else {
						
						for(int i = 0;i < profile.getPastBan().size(); i++) {
						
							sender.sendMessages(profile.getPastBanMessage(i));
						}
						
					}
					if(profile.getPastMute().size() == 0) {
						
						sender.sendMessage(Main.getConfigManager().getString("lang.command.history.no_mute"));
					} else {
						
						for(int i = 0;i < profile.getPastMute().size(); i++) {
							
							sender.sendMessages(profile.getPastMuteMessage(i));
						}
					}
				} else { System.out.println("PROFILE ERROR");}
			} else { System.out.println("NO ARGUMENT");}
		} else { System.out.println("NO PERMISSION");}
	}
}
}

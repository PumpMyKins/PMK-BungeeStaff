package fr.pmk_bungee.command;

import java.util.List;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender.hasPermission("bungeestaff.command.kick")) {
			
			if(args.length > 1) {
				
				String playername = args[0];
				String reason = "";
				TextComponent kickReason = new TextComponent();
				String str = "";
				
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
				ProxyServer.getInstance().getPlayer(playername).disconnect(kickReason);
				
				sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.kicked", new String[] { "{NAME}~" + playername})));
			
			} else { sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.syntax")));}
		
		} else { sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));}
	}

}

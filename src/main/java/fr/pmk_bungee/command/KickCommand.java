package fr.pmk_bungee.command;

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

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		if(sender.hasPermission("bungeestaff.command.kick")) {
			
			if(args.length > 1) {
				
				String playername = args[0];
				String reason = "";
				TextComponent kickReason = new TextComponent();
				
				for(int i = 1; i <= args.length - 1; i++) {
					
					reason = reason + args[i] + " ";
					
				}
				kickReason.addExtra(reason + " par " + sender);
				ProxyServer.getInstance().getPlayer(playername).disconnect(kickReason);
				sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.kicked", new String[] { "{NAME}~" + playername}));
			} else { sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.syntax"));}
		} else { sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));}
	}

}

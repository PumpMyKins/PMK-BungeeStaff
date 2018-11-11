package fr.pmk_bungee.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.object.Message;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import fr.pmk_bungee.object.Parameter;

public class BanCommand extends Command {

	public BanCommand(String name) {

		super(name);
	}


	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		Message msg = new Message();
		List<Parameter> param = new ArrayList<Parameter>();

		if(sender.hasPermission("bungeestaff.command.ban")) {


			if(args.length >= 4) {

				String playername = args[0];
				String banReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 3; i <= args.length - 1; i++) {

					banReason += args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {

					if(!PlayerSituation.isBanned(playername)) {

						Ban ban = new Ban();
						long seconds = Integer.parseInt(args[1]);
						Main.TimeUnit unit = Main.TimeUnit.getByString(args[2]);
						if(unit != null) {

							seconds*= unit.getSeconds();
							ban.setStartBan( new Timestamp(System.currentTimeMillis()));
							ban.setEndBan( new Timestamp(System.currentTimeMillis() + seconds * 1000));
							ban.setBanBy(situation.getPlayerId(sender.getName()));
							ban.setPlayerId(situation.getPlayerId(playername));
							ban.setBanReason(banReason);
							
							PlayerSituation.setBanned(ban);
							
							sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.ban.banned", new String[] { "{NAME}~" + playername })));
							
							if(toProxiedPlayer(playername) != null) {
								
								toProxiedPlayer(playername).disconnect(new TextComponent(PlayerSituation.getBanMessage(playername))); 
							}
						}
					} else {
						sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_already_banned", new String[] { "{NAME}~" + playername })));
					}
				} else {
					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found")));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.ban.syntax")));
			}
		} else {
			sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));
		}

	}
	public ProxiedPlayer toProxiedPlayer(String playername) {

		return ProxyServer.getInstance().getPlayer(playername);
	}
}

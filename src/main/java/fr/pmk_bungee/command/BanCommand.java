package fr.pmk_bungee.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.object.Message;
import fr.pmk_bungee.utils.MessageSender;
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

				String playername = args[0].toLowerCase();
				String banReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 3; i <= args.length - 1; i++) {

					banReason+=banReason + args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {

					if(!PlayerSituation.testBan(situation)) {

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

							Main.getMySQL().update("INSERT INTO BungeeBan(playerId, startBan, endBan, banReason, banBy) VALUES ('" 
									+ ban.getPlayerId()
									+ "', '" 
									+ ban.getStartBan()
									+ "','" 
									+ ban.getEndBan()
									+ "','" 
									+ ban.getBanReason()
									+ "','" 
									+ ban.getBanBy()
									+ "')");

							if(toProxiedPlayer(playername) != null) {
								
								toProxiedPlayer(playername).disconnect(new TextComponent(PlayerSituation.getBanMessage(situation))); //TODO disconnecte Message
							}

							Parameter name = new Parameter();
							name.setParamTitle("NAME");
							name.setParamContent(playername);
							param.add(name);

							msg.setMessageTitle("lang.commands.ban.banned");

						}
					} else {
						//TODO player_already_ban
						Parameter name = new Parameter();
						name.setParamTitle("NAME");
						name.setParamContent(playername);
						param.add(name);

						msg.setMessageTitle("lang.errors.player_already_banned");


					}
				} else {
					msg.setMessageTitle("lang.errors.player_not_found");

				}
			} else {

				msg.setMessageTitle("lang.commands.ban.syntax");


			}
		} else {

			msg.setMessageTitle("lang.errors.no_permissions");


		}
		msg.setPrefix(true);
		msg.setSender(sender);
		msg.setParameter(param);

		MessageSender.send(msg);
	}
	public ProxiedPlayer toProxiedPlayer(String playername) {

		return ProxyServer.getInstance().getPlayer(playername);
	}
}

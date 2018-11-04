package fr.pmk_bungee.command;

import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.utils.MessageSender;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

	public BanCommand(String name) {

		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("bungeestaff.command.ban")) {

			if(args.length >= 4) {

				String playername = args[0];
				String banReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 3; i <= args.length - 1; i++) {

					banReason+=banReason + args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {

					if(!situation.isBanned()) {

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
									+ ban.getId()
									+ "', '" 
									+ ban.getStartBan()
									+ "','" 
									+ ban.getEndBan()
									+ "','" 
									+ ban.getBanReason()
									+ "','" 
									+ ban.getBanBy()
									+ "')");
						}
					} else {
						//TODO player_already_ban
					 }
				} else {
					//TODO Player_Not_Found
				}
			} else {
				//TODO error_syntax
			}
		} else {
			//TODO no_Permission
		}
	}
}

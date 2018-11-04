package fr.pmk_bungee.command;

import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand(String name) {

		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("bungeestaff.command.kick")) {

			if(args.length >= 4) {

				String playername = args[0];
				String kickReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 1; i <= args.length - 1; i++) {

					kickReason+=kickReason + args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {


					Kick kick = new Kick();

						seconds*= unit.getSeconds();
						kick.setKickDate(new Timestamp(System.currentTimeMillis()));
						kick.setKickBy(situation.getPlayerId(sender.getName()));
						kick.setPlayerId(situation.getPlayerId(playername));
						kick.setKickReason(kickReason);

						Main.getMySQL().update("INSERT INTO BungeeKick(playerId, kickDate, kickReason, kickBy) VALUES ('" 
								+ kick.getId()
								+ "', '" 
								+ kick.getKickDate()
								+ "','" 
								+ kick.getKickReason()
								+ "','" 
								+ kick.getKickBy()
								+ "')");

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

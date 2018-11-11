package fr.pmk_bungee.command;

import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Mute;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

public class MuteCommand extends Command {

	public MuteCommand(String name) {

		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("bungeestaff.command.mute")) {

			if(args.length >= 4) {

				String playername = args[0];
				String muteReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 3; i <= args.length - 1; i++) {

					muteReason+=muteReason + args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {

					if(!PlayerSituation.isMuted(playername)) {

						Mute mute = new Mute();
						long seconds = Integer.parseInt(args[1]);
						Main.TimeUnit unit = Main.TimeUnit.getByString(args[2]);
						if(unit != null) {

							seconds*= unit.getSeconds();
							mute.setStartMute( new Timestamp(System.currentTimeMillis()));
							mute.setEndMute( new Timestamp(System.currentTimeMillis() + seconds * 1000));
							mute.setMuteBy(situation.getPlayerId(sender.getName()));
							mute.setPlayerId(situation.getPlayerId(playername));
							mute.setMuteReason(muteReason);
							
							PlayerSituation.setMuted(mute);

						} else {
							//TODO 
						}
					} else {
						//TODO player_already_mute
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

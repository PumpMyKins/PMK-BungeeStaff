package fr.pmk_bungee.command;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.object.Message;
import fr.pmk_bungee.object.Parameter;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand(String name) {

		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		Message msg = new Message();
		List<Parameter> param = new ArrayList<Parameter>();

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

					kick.setKickDate(new Timestamp(System.currentTimeMillis()));
					kick.setKickBy(situation.getPlayerId(sender.getName()));
					kick.setPlayerId(situation.getPlayerId(playername));
					kick.setKickReason(kickReason);

					PlayerSituation.setKick(kick);

					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.kicked", new String[] { "{NAME}~" + playername})));
				} else {
					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.kick.syntax")));
				}
			} else {
				sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));		
			}
		}
	}
}

package fr.pmk_bungee.command;

import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	public KickCommand(String name) {

		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("bungeestaff.command.kick")) {

			if(args.length >= 2) {

				String playername = args[0];
				String kickReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 1; i <= args.length - 1; i++) {

					kickReason+= args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {


					Kick kick = new Kick();

					kick.setKickDate(new Timestamp(System.currentTimeMillis()));
					kick.setKickBy(situation.getPlayerId(sender.getName()));
					kick.setPlayerId(situation.getPlayerId(playername));
					kick.setKickReason(kickReason);

					PlayerSituation.setKick(kick);
					ProxiedPlayer p = ProxyServer.getInstance().getPlayer(args[0]);
					p.disconnect(new TextComponent(Main.PREFIX + Main.getConfigManager().getStringList("lang.kickmessage", new String[] {
							"{NAME}~" + playername,
							"{REASON}~" + kickReason
					})));

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

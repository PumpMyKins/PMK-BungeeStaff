package fr.pmk_bungee.command;

import java.sql.Timestamp;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Warn;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class WarnCommand extends Command {

	public WarnCommand(String name) {

		super(name);
	}

	@SuppressWarnings("unused")
	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("bungeestaff.command.warn")) {

			if(args.length >= 2) {

				String playername = args[0];
				String warnReason = "";
				PlayerSituation situation = new PlayerSituation(playername);
				for(int i = 1; i <= args.length - 1; i++) {

					warnReason+= args[i] + " ";
				}

				Main.getConfigManager().save();

				if(situation != null) {


					Warn warn = new Warn();

					warn.setWarnDate(new Timestamp(System.currentTimeMillis()));
					warn.setWarnBy(situation.getPlayerId(sender.getName()));
					warn.setPlayerId(situation.getPlayerId(playername));
					warn.setWarnReason(warnReason);

					PlayerSituation.setWarn(warn);

					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.warn.succes", new String[] {
							"{NAME}~" + playername,

					})));
					List<String> msgs = Main.getConfigManager().getStringList("lang.warnmessage", new String[] { 
							"{NAME}~" + sender.getName(),
							"{REASON}~" + warn.getWarnReason()

					});
					for(String msg : msgs) {

						ProxyServer.getInstance().getPlayer(playername).sendMessage(new TextComponent(msg));
					}


				} else {
					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.warn.syntax")));
				}
			}
		}  else {
			sender.sendMessage(new TextComponent(Main.PREFIX +Main.getConfigManager().getString("lang.errors.no_permissions")));
		}
	}
}

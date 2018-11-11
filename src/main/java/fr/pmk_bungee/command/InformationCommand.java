package fr.pmk_bungee.command;

import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.object.Mute;
import fr.pmk_bungee.object.Warn;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class InformationCommand extends Command {

	public InformationCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {


		if(sender.hasPermission("bungeestaff.commands.info")) {

			if(args.length > 0) {
				String playername = args[0];
				PlayerSituation situation = new PlayerSituation(playername);
				if(situation != null) {
					if(!situation.getWarnList().isEmpty()) {
						for(int i = 0; i < situation.getWarnList().size(); i++) {
							Warn actual =situation.getWarnList().get(i);
							List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.warn.true", new String[] {
									"{NAME}~" + PlayerSituation.getUsername(actual.getWarnBy()),
									"{REASON}~" + actual.getWarnReason(),
									"{DATE}~" + actual.getWarnDate()

							});
							for (String msg : msgs) {
								sender.sendMessage(new TextComponent(msg));
							}
						}
					}
					else {
						List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.warn.false", new String[] {
								"{NAME}~" + playername

						});
						for(String msg : msgs) {

							sender.sendMessage(new TextComponent(msg));
						}
					}
					if(!situation.getBanList().isEmpty()) {
						if(!situation.getBanList().isEmpty()) {
							for(int i = 0; i < situation.getBanList().size(); i++) {
								Ban actual = situation.getBanList().get(i);
								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.ban.true", new String[] {
										"{NAME}~" + PlayerSituation.getUsername(actual.getBanBy()),
										"{REASON}~" + actual.getBanReason(),
										"{DATE}~" + actual.getStartBan()

								});
								for (String msg : msgs) {
									sender.sendMessage(new TextComponent(msg));
								}
							}
						}							
					}
					else {
						List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.banned.false", new String[] { 
								"{NAME}~" + playername 

						});

						for (String msg : msgs) {
							sender.sendMessage(new TextComponent(msg));
						}
					}
					if(!situation.getMuteList().isEmpty()) {
						for(int i = 0; i < situation.getMuteList().size(); i++) {	
							Mute actual = situation.getMuteList().get(i);
							List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.mute.true", new String[] {
									"{NAME}~" + PlayerSituation.getUsername(actual.getMuteBy()),
									"{REASON}~" + actual.getMuteReason(),
									"{DATE}~" + actual.getStartMute()

							});
							for (String msg : msgs) {
								sender.sendMessage(new TextComponent(msg));
							}							
						}
					}
					else {
						List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.muted.false", new String[] { 
								"{NAME}~" + playername 

						});

						for (String msg : msgs) {
							sender.sendMessage(new TextComponent(msg));
						}
					}
					if(!situation.getKickList().isEmpty()) {
						for(int i = 0; i < situation.getKickList().size(); i++) {
							Kick actual = situation.getKickList().get(i);
							List<String> msgs = Main.getConfigManager().getStringList("lang.commands.history.kick.true", new String[] {
									"{NAME}~" + PlayerSituation.getUsername(actual.getKickBy()),
									"{REASON}~" + actual.getKickReason(),
									"{DATE}~" + actual.getKickDate()

							});
							for (String msg : msgs) {
								sender.sendMessage(new TextComponent(msg));
							}
						}
					}
					else {
						sender.sendMessage(new TextComponent(Main.getConfigManager().getString("lang.commands.history.kick.false", new String[] {
								"{NAME}~" + playername,
						})));
					}
						
						List<String> msgs = Main.getConfigManager().getStringList("lang.commands.info.player", new String[] {
								"{NAME}~" + situation.getPlayer().getUsername(),
								"{UUID}~" + situation.getPlayer().getUuid(),
								"{FIRST}~" + situation.getPlayer().getFirstCome(),
								"{LAST}~" + situation.getPlayer().getLastCome(),
								"{IP}~" + situation.getPlayer().getPlayerIp()
						});
						for(String msg : msgs) {
							sender.sendMessage(new TextComponent(msg));
					}
				}
			}
		}
	}
}


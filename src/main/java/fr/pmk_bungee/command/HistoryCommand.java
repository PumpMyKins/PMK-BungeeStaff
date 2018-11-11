package fr.pmk_bungee.command;

import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.object.Mute;
import fr.pmk_bungee.object.Warn;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class HistoryCommand extends Command {

	public HistoryCommand(String name) {
		super(name);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		ProxyServer.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable() {

			public void run() {
				
				if(sender.hasPermission("bungeestaff.command.history")) {
					
					if(args.length > 1) {
						
						Main.getConfigManager().save();

						String playername = args[0];
						PlayerSituation situation = new PlayerSituation(playername);
						switch(args[1].toLowerCase()) {
						case "warn": 
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
								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.warn.false", new String[] {
										"{NAME}~" + playername
									
								});
								for(String msg : msgs) {
									
									sender.sendMessage(new TextComponent(msg));
								}
							}
				            break;
						case "ban":
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
								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.banned.false", new String[] { 
										"{NAME}~" + playername 

								});

								for (String msg : msgs) {
									sender.sendMessage(new TextComponent(msg));
								}
							}
							break;
						case "mute":
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
								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.false", new String[] { 
										"{NAME}~" + playername 

								});

								for (String msg : msgs) {
									sender.sendMessage(new TextComponent(msg));
								}
							}
							break;

						case "kick":
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
								//TODO no kick
							}
							break;
						default:
							sender.sendMessage(new TextComponent(
									Main.PREFIX +
									Main.getConfigManager().getString("lang.commands.history.syntax")));
						}
					} else {
						sender.sendMessage(new TextComponent(
								Main.PREFIX +
								Main.getConfigManager().getString("lang.commands.history.syntax")));
					}

				} else {
					sender.sendMessage(new TextComponent(
							Main.PREFIX + 
							Main.getConfigManager().getString("lang.errors.no_permissions")));
				}
			}
		});

	}

}

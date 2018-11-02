package fr.pmk_bungee.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PastBan;
import fr.pmk_bungee.utils.PastBanShower;
import fr.pmk_bungee.utils.PastKick;
import fr.pmk_bungee.utils.PastKickShower;
import fr.pmk_bungee.utils.PastMute;
import fr.pmk_bungee.utils.PastMuteShower;
import fr.pmk_bungee.utils.Warn;
import fr.pmk_bungee.utils.WarnShower;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class HistoryCommand extends Command {

	public HistoryCommand(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

		ProxyServer.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable() {

			public void run() {
				if(sender.hasPermission("bungeestaff.command.history")) {
					if(args.length > 1) {
						List<Warn> warnList = null;
						List<PastBan> pastBanList = null;
						List<PastMute> pastMuteList = null;
						List<PastKick> pastKickList = null;
						String playername = args[0];
						try {
							warnList = WarnShower.listWarn(playername);
						} catch (ClassNotFoundException | NullPointerException | SQLException e) {
							e.printStackTrace();
						}
						try {
							pastBanList = PastBanShower.listPastBan(playername);
						} catch (ClassNotFoundException | NullPointerException | SQLException e) {
							e.printStackTrace();
						}
						try {
							pastMuteList = PastMuteShower.listPastMute(playername);
						} catch (ClassNotFoundException | NullPointerException | SQLException e) {
							e.printStackTrace();
						}
						try {
							pastKickList = PastKickShower.listPastKick(playername);
						} catch (ClassNotFoundException | NullPointerException | SQLException e) {
							e.printStackTrace();
						}

						switch(args[1].toLowerCase()) {
						case "warn":
							if(warnList.size() > 0)
								for(int i = 0; i < warnList.size(); i++) {
									Warn actual = warnList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.warn.true", new String[] {
											"{NAME}~" + getUsername(actual.getWarnBy()),
											"{REASON}~" + actual.getWarnReason(),
											"{DATE}~" + actual.getWarnAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.warn.false")));
				             break;

						case "ban":
							if(pastBanList.size() > 0)
								for(int i = 0; i < pastBanList.size(); i++) {
									PastBan actual = pastBanList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.ban.true", new String[] {
											"{NAME}~" + getUsername(actual.getBanBy()),
											"{REASON}~" + actual.getBanReason(),
											"{DATE}~" + actual.getBanAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.ban.false")));
				             break;

						case "mute":
							if(pastMuteList.size() > 0)
								for(int i = 0; i < pastMuteList.size(); i++) {	
									PastMute actual = pastMuteList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.mute.true", new String[] {
											"{NAME}~" + getUsername(actual.getMuteBy()),
											"{REASON}~" + actual.getMuteReason(),
											"{DATE}~" + actual.getMuteAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.mute.false")));
				            break;

						case "kick":
							if(pastKickList.size() > 0)
								for(int i = 0; i < pastKickList.size(); i++) {
									PastKick actual = pastKickList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.kick.true", new String[] {
											"{NAME}~" + getUsername(actual.getKickBy()),
											"{REASON}~" + actual.getKickReason(),
											"{DATE}~" + actual.getKickAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.mute.false")));
				             break;

						case"all":
							if(warnList.size() > 0)
								for(int i = 0; i < warnList.size(); i++) {
									Warn actual = warnList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.warn.true", new String[] {
											"{NAME}~" + getUsername(actual.getWarnBy()),
											"{REASON}~" + actual.getWarnReason(),
											"{DATE}~" + actual.getWarnAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.warn.false")));
							if(pastBanList.size() > 0)
								for(int i = 0; i < pastBanList.size(); i++) {
									PastBan actual = pastBanList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.ban.true", new String[] {
											"{NAME}~" + getUsername(actual.getBanBy()),
											"{REASON}~" + actual.getBanReason(),
											"{DATE}~" + actual.getBanAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.ban.false")));
							if(pastMuteList.size() > 0)
								for(int i = 0; i < pastMuteList.size(); i++) {	
									PastMute actual = pastMuteList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.mute.true", new String[] {
											"{NAME}~" + getUsername(actual.getMuteBy()),
											"{REASON}~" + actual.getMuteReason(),
											"{DATE}~" + actual.getMuteAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.mute.false")));
							if(pastKickList.size() > 0)
								for(int i = 0; i < pastKickList.size(); i++) {
									PastKick actual = pastKickList.get(i);
									List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.kick.true", new String[] {
											"{NAME}~" + getUsername(actual.getKickBy()),
											"{REASON}~" + actual.getKickReason(),
											"{DATE}~" + actual.getKickAt()

									});
									for (String msg : msgs) {
										sender.sendMessage(new TextComponent(msg));
									}
								}
							else
								sender.sendMessage(new TextComponent(
										Main.PREFIX + 
										Main.getConfigManager().getString("lang.commands.history.mute.false")));

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
	public static  String getUsername(int userID) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT username FROM MinecraftPlayer WHERE userID = '" 
					+ userID 
					+ "'");

			if(id.next()) {

				String username = id.getString("username");
				return username;
			}

		} catch (SQLException e) {

			e.printStackTrace(); 
		} return "";
	}

}

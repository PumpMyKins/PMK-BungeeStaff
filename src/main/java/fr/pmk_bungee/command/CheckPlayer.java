package fr.pmk_bungee.command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import fr.pmk_bungee.utils.Warn;
import fr.pmk_bungee.utils.WarnShower;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CheckPlayer extends Command {

	public CheckPlayer(String string) {
		// TODO Auto-generated constructor stub
		super(string);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub
		ProxyServer.getInstance().getScheduler().runAsync(Main.sharedInstance(), new Runnable() {

			@SuppressWarnings({ "unused" })
			public void run() {

				if(sender.hasPermission("bungeestaff.command.check")) {
					List<Warn> warnList = null;
					if(args.length == 1) {

						PlayerProfile profile = new PlayerProfile(args[0]);
						String playername = args[0];
						try {
							warnList = WarnShower.listWarn(playername);
						} catch (ClassNotFoundException | NullPointerException | SQLException e) {
							e.printStackTrace();
						}

						if(profile != null) {

							sender.sendMessage(new TextComponent(
									Main.PREFIX + 
									Main.getConfigManager().getString("lang.commands.check.succes", new String[] { 
											"{NAME}~" + playername

									})
									));

							if(profile.isBanned()) {

								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.banned.true", new String[] { 
										"{NAME}~" + playername, 
										"{REASON}~" + profile.getBanReason(), 
										"{BY}~" + profile.getUsername(profile.getBanBy()), 
										"{REMAININGTIME}~" + profile.getRemainingbanTime(),
										"{BANAT}~" + profile.getInNowBan()

								});

								for (String msg : msgs) {
									sender.sendMessage(new TextComponent(msg));
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
							if(profile.isMuted()) {

								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.true", new String[] { 
										"{NAME}~" + playername, 
										"{REASON}~" + profile.getMuteReason(), 
										"{BY}~" + profile.getUsername(profile.getMutedBy()), 
										"{REMAININGTIME}~" + profile.getRemainingmuteTime(),
										"{MUTEAT}~" + profile.getInNowMute()

								});

								for (String msg : msgs) {
									sender.sendMessage(new TextComponent(msg));
								}

							}
							else
							{
								List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.false", new String[] { 
										"{NAME}~" + playername 

								});

								for (String msg : msgs) {
									sender.sendMessage(new TextComponent(msg));
								}

							}
						} else {
							sender.sendMessage(new TextComponent(Main.getConfigManager().getString("lang.errors.player_not_found")));
						} 

						if(warnList.size() < 1) {

							sender.sendMessage(new TextComponent(Main.getConfigManager().getString("lang.commands.check.warn.false", new String[] {
									"{NAME}~" + playername
							})));

						} else {

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
						}
					} else { sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.commands.check.syntax")));}
				} else { sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));}
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

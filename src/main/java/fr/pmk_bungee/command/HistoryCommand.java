package fr.pmk_bungee.command;

import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PastBan;
import fr.pmk_bungee.utils.PastBanShower;
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
						
						
					}
				
				} else {
					sender.sendMessage(new TextComponent(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions")));
				}
			}
		});

	}

}

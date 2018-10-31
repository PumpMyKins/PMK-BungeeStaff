package fr.pmk_bungee.command;

import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
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
			
			@SuppressWarnings({ "deprecation", "unused" })
			public void run() {
				
				if(sender.hasPermission("bungeestaff.command.history")) {
					
					if(args.length == 1) {
						
						PlayerProfile profile = new PlayerProfile(args[0]);
						
						if(profile != null) {
							
							String playername = args[0];
							sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.history", new String[] { "{PLAYERNAME}~" + playername }));
							if(profile.isBanned()) {
								
				                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.banned.positive", new String[] { "{NAME}~" + playername, "{REASON}~" +  profile.getBanReason(), "{BY}~" + profile.getBanBy(), "{REMAININGTIME}~" + profile.getRemainingbanTime() });
				                for (String msg : msgs) {
				                	sender.sendMessage(Main.PREFIX + msg);
							
				                }
						
							} else {
								
				                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.banned.negative", new String[] { "{NAME}~" + playername });
				                for (String msg : msgs) {
				                	sender.sendMessage(Main.PREFIX + msg);
				                  
				                }
							}
							if(profile.isMuted()) {
								
				                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.positive", new String[] { "{NAME}~" + playername, "{REASON}~" + profile.getMuteReason(), "{BY}~" + profile.getMutedBy(), "{REMAININGTIME}~" + profile.getRemainingmuteTime() });
				                for (String msg : msgs) {
				                	sender.sendMessage(Main.PREFIX + msg);
				                
				                }
							} else {
								
				                List<String> msgs = Main.getConfigManager().getStringList("lang.commands.check.muted.negative", new String[] { "{NAME}~" + playername });
				                for (String msg : msgs) {
				                	sender.sendMessage(Main.PREFIX + msg);
				                }
								
							}
							
							
				              
			            }
			            else
			            {
			              sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.player_not_found"));
			            }

					} else {
			            sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.commands.check.syntax"));
			          }
			        } else {
			          sender.sendMessage(Main.PREFIX + Main.getConfigManager().getString("lang.errors.no_permissions"));
			          }
			      }
			    });
			  }
			}

package fr.pmk_bungee.utils.manager;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.command.BanCommand;
import fr.pmk_bungee.command.HistoryCommand;
import fr.pmk_bungee.command.InformationCommand;
import fr.pmk_bungee.command.KickCommand;
import fr.pmk_bungee.command.MuteCommand;
import fr.pmk_bungee.command.UnbanCommand;
import fr.pmk_bungee.command.UnmuteCommand;
import fr.pmk_bungee.command.WarnCommand;
import fr.pmk_bungee.listener.ChatEvent;
import fr.pmk_bungee.listener.LoginEvent;
import fr.pmk_bungee.listener.LogoutEvent;
import net.md_5.bungee.api.plugin.PluginManager;

public class CommandManager {

	public static CommandManager init() {
		
	return new CommandManager();	
	}
	
	public CommandManager() {
		
		PluginManager pm = Main.sharedInstance().getProxy().getPluginManager();
		
		// UNBAN
		pm.registerCommand(Main.sharedInstance(), new UnbanCommand("gunban"));
		//UNMUTE
		pm.registerCommand(Main.sharedInstance(), new UnmuteCommand("gunmute"));
		//MUTE
		pm.registerCommand(Main.sharedInstance(), new MuteCommand("gmute"));
		//BAN
		pm.registerCommand(Main.sharedInstance(), new BanCommand("gban"));
		//GET HISTORY OF THE PLAYER
		pm.registerCommand(Main.sharedInstance(), new HistoryCommand("ghistory"));
		// GET ALL INFORMATION OF A PLAYER
		pm.registerCommand(Main.sharedInstance(), new InformationCommand("ginfo"));
		//Kick a Player
		pm.registerCommand(Main.sharedInstance(), new KickCommand("gkick"));
		//Warn a player (FOREVER)
		pm.registerCommand(Main.sharedInstance(), new WarnCommand("gwarn"));

		pm.registerListener(Main.sharedInstance(), new ChatEvent());

		pm.registerListener(Main.sharedInstance(), new LoginEvent());
		
		pm.registerListener(Main.sharedInstance(), new LogoutEvent());
		
	
	}
}

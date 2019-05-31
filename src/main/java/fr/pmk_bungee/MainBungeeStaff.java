package fr.pmk_bungee;

import java.sql.SQLException;

//IMPORT SQL
import fr.pmk_bungee.MySQL.MySQLCredentials;
//IMPORT COMMAND
import fr.pmk_bungee.command.BanCommand;
import fr.pmk_bungee.command.CheckPlayer;
import fr.pmk_bungee.command.InformationCommand;
import fr.pmk_bungee.command.KickCommand;
import fr.pmk_bungee.command.MuteCommand;
import fr.pmk_bungee.command.UnbanCommand;
import fr.pmk_bungee.command.UnmuteCommand;
import fr.pmk_bungee.command.WarnCommand;
//IMPORT LISTENER
import fr.pmk_bungee.listener.ChatEvent;
import fr.pmk_bungee.listener.LoginEvent;
import fr.pmk_bungee.objects.PlayersLog;
//IMPORT CONFIG
import fr.pmk_bungee.utils.ConfigManager;
//IMPORT BUNGEE THINGS
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;

public class MainBungeeStaff extends Plugin{

	public static ConfigManager configManager;
	private PlayersLog pl;

	static MainBungeeStaff sharedInstance = null;
	static MySQL mySQL;
	public static String host = "";
	public static String username = "";
	public static String password = "";
	public static String database = "";
	public static String PREFIX = "[BungeeStaff]", CONSOLE_PREFIX = "[BS]";
	public static int port = 3306;

	public void onEnable() {

		sharedInstance = this;

		//Config
		configManager = new ConfigManager();
		configManager.init();

		//SQL
		MySQLCredentials credentials = new MySQLCredentials(host, port, username, password, database);
		mySQL = new MySQL(credentials);
		mySQL.openConnection();
		if(mySQL.isConnected()) {

			logToConsole("§aMySQL connection success.");
			//BASE DE DONNER
			mySQL.update("CREATE TABLE IF NOT EXISTS Players(`username` VARCHAR(16) NOT NULL , `uuid` VARCHAR(256) NOT NULL ,`ip` VARCHAR(256) NOT NULL,`firstCome` DATETIME NOT NULL,`lastCome` DATETIME NOT NULL)");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeBan(banId INT NOT NULL AUTO_INCREMENT, banAt DATETIME NOT NULL, banDuration INT NOT NULL, banBy VARCHAR(256) NOT NULL, bannedPlayer VARCHAR(256) NOT NULL, banIp VARCHAR(256) NOT NULL, banReason VARCHAR(256), PRIMARY KEY (banId))");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeMutes(muteId INT NOT NULL AUTO_INCREMENT, muteAt DATETIME NOT NULL, muteDuration INT NOT NULL, muteBy VARCHAR(256) NOT NULL, mutePlayer VARCHAR(256) NOT NULL, muteReason VARCHAR(256), PRIMARY KEY (muteId))");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeKicks(kickId INT NOT NULL AUTO_INCREMENT, kickBy VARCHAR(256) NOT NULL,kickPlayer VARCHAR(256),kickReason VARCHAR(256) NOT NULL, kickDate DATETIME, PRIMARY KEY (kickId))");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeWarn(warnId INT NOT NULL AUTO_INCREMENT, warnBy VARCHAR(256) NOT NULL, warnDate DATETIME, warnReason VARCHAR(256), warnPlayer VARCHAR(256), PRIMARY KEY (warnId))");
			
			
		}
		
		try {
			this.pl = new PlayersLog();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		
		
		getProxy();
		PluginManager pm = ProxyServer.getInstance().getPluginManager();

		//UNBAN + BAN COMMAND
		pm.registerCommand(this, new UnbanCommand("bunban", this.pl));
		pm.registerCommand(this, new BanCommand("bban", this.pl));
		//UNMUTE + MUTE COMMAND
		pm.registerCommand(this, new UnmuteCommand("bunmute", this.pl));
		pm.registerCommand(this, new MuteCommand("bmute", this.pl));
		//CHECK IF BAN / MUTE  + GET ALL INFORMATION OF A PLAYER
		pm.registerCommand(this, new CheckPlayer("bcheck", this.pl));
		pm.registerCommand(this, new InformationCommand("binfo", this.pl));
		//Kick a Player + Warn a player (FOREVER)
		pm.registerCommand(this, new KickCommand("bkick", this.pl));
		pm.registerCommand(this, new WarnCommand("bwarn", this.pl));
		//THE DIFFERENT LISTENER CHAT FOR MUTE + LOGIN FOR BAN
		pm.registerListener(this, new ChatEvent(pl));
		pm.registerListener(this, new LoginEvent(pl));
		
		
	}

	//GETTER AND SETTER 

	@SuppressWarnings("deprecation")
	private void logToConsole(String string) {
		ProxyServer.getInstance().getConsole().sendMessage(string);
	}

	public static MainBungeeStaff sharedInstance() {
		return sharedInstance;
	}

	public static MySQL getMySQL() throws SQLException {
		if(mySQL.isConnected()) {
			return mySQL;
		} else {
			mySQL.refreshConnection();
			if(mySQL.isConnected()) {
				return mySQL;
			}
		}
		return null;
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public enum TimeUnit {
		
		SECOND(new String[] { "s", "sec", "secs", "second", "seconds" }, 1000L),  MINUTE(new String[] { "m", "min", "mins", "minute", "minutes" }, 60000L),  HOUR(new String[] { "h", "hs", "hour", "hours" }, 3600000L),  DAY(new String[] { "d", "ds", "day", "days" }, 86400000L);

		private String[] names;
		private long seconds;

		private TimeUnit(String[] names, long seconds)
		{
			this.names = names;
			this.seconds = seconds;
		}

		public long getSeconds()
		{
			return this.seconds;
		}

		public String[] getNames()
		{
			return this.names;
		}

		public static TimeUnit getByString(String str)
		{
			for (TimeUnit timeUnit : TimeUnit.values()) {
				for (String name : timeUnit.getNames()) {
					if (name.equalsIgnoreCase(str)) {
						return timeUnit;
					}
				}
			}
			return null;
		}
	}

	public PlayersLog getPl() {
		return pl;
	}

	public void setPl(PlayersLog pl) {
		this.pl = pl;
	}
	
	
}

package fr.pmk_bungee;

import fr.pmk_bungee.utils.MySQL;
import fr.pmk_bungee.utils.MySQL.MySQLCredentials;
import fr.pmk_bungee.utils.manager.CommandManager;
import fr.pmk_bungee.utils.manager.ConfigManager;
import fr.pmk_bungee.utils.manager.DatabaseManager;
//IMPORT BUNGEE THINGS
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{

	public static ConfigManager configManager;

	static Main sharedInstance = null;
	static MySQL mySQL;
	public static String host = "";
	public static String username = "";
	public static String password = "";
	public static String database = "";
	public static String PREFIX = "", CONSOLE_PREFIX = "";
	public static int port = 3306;

	public void onEnable() {

		sharedInstance = this;

		//Config
		configManager = new ConfigManager();
		configManager.init();
		
		//CommandManager
		CommandManager.init();

		//SQL
		MySQLCredentials credentials = new MySQLCredentials(host, port, username, password, database);
		mySQL = new MySQL(credentials);
		mySQL.openConnection();
		
		DatabaseManager.init();

	}

	//GETTER AND SETTER 

	@SuppressWarnings("deprecation")
	public static void logToConsole(String string) {
		ProxyServer.getInstance().getConsole().sendMessage(string);
	}

	public static Main sharedInstance() {
		return sharedInstance;
	}

	public static MySQL getMySQL() {
		return mySQL;
	}

	public static ConfigManager getConfigManager() {
		return configManager;
	}

	public enum TimeUnit
	{
		SECOND(new String[] { "s", "sec", "secs", "second", "seconds" }, 1L),  MINUTE(new String[] { "m", "min", "mins", "minute", "minutes" }, 60L),  HOUR(new String[] { "h", "hs", "hour", "hours" }, 3600L),  DAY(new String[] { "d", "ds", "day", "days" }, 86400L);

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

}

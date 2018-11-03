package fr.pmk_bungee.utils.manager;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.MySQL;

public class DatabaseManager {

	public static DatabaseManager init(){
		
		return new DatabaseManager();
	}
	public DatabaseManager() {
		
		if(Main.getMySQL().isConnected()) {
			
			Main.logToConsole("§aMySQL connection success.");
			
			MySQL mySQL = Main.getMySQL();
			
			mySQL.update("CREATE TABLE IF NOT EXISTS MinecraftPlayer( `playerID` INT NOT NULL AUTO_INCREMENT , `username` VARCHAR(16) NOT NULL , `uuid` VARCHAR(256) NOT NULL ,`ip` VARCHAR(256) NOT NULL,`firstCome` DATETIME,`lastCome` DATETIME ,PRIMARY KEY (`playerId`))");
			//BDD Relation 1:N (1 = USER; 1 = OTHER)
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeBan(`playerId` INT, `startBan` DATETIME, `endBan` DATETIME, `banBy` INT, `banReason` VARCHAR(256), `id` INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id))");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeMute(`playerId` INT, `startBan` DATETIME, `endBan` DATETIME, `banBy` INT, `banReason` VARCHAR(256), `id` INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id))");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeKick(`playerId` INT, `kickDate` DATETIME, `kickBy` INT, `kickReason` VARCHAR(256), `id` INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id))");
			mySQL.update("CREATE TABLE IF NOT EXISTS BungeeWarn(`playerId` INT, `warnDate` DATETIME, `warnBy` INT, `warnReason` VARCHAR(256), `id` INT NOT NULL AUTO_INCREMENT, PRIMARY KEY (id))");

		}
	}
}

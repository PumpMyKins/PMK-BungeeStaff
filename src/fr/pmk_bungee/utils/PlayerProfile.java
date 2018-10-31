package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerProfile {

	private String playerName;
	
	private String banReason;
	private String banBy;
	private boolean isBanned;
	private long banEnd;
	
	private String muteReason;
	private String mutedBy;
	private boolean isMuted;
	private long muteEnd;
	
	public PlayerProfile(String player) {
		this.playerName = player;
		init();
	}
	
	public void init() {
		try {
			
			ResultSet rs = Main.getMySQL().getResult("SELECT * FROM ActuelBungeeBan WHERE ")
		}
	}
	
	public void getUserID(String playerName) {
		
		return ResultSet id = Main.getMySQL().getResult("SELECT userid FROM ")
	}
}

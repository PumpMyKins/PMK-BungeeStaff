package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.object.Mute;
import fr.pmk_bungee.object.Warn;

public class PlayerSituation {

	private String playername;
	private int playerId;
	private List<Ban> banList;
	private List<Mute> muteList;
	private List<Kick> kickList;
	private List<Warn> warnList;
	private boolean isBanned;
	private boolean isMuted;
	
	
	
	public PlayerSituation(String playername){
	
	this.playername = playername;
	this.playerId = getPlayerId(playername);
	init();
	
	}
	
	public void init() {
		
		try {
			
			ResultSet getBan = Main.getMySQL().getResult("SELECT * FROM BungeeBan WHERE playerId ='" + playerId + "'");
			ResultSet getKick = Main.getMySQL().getResult("SELECT * FROM BungeeKick WHERE playerId ='" + playerId + "'");
			ResultSet getMute = Main.getMySQL().getResult("SELECT * FROM BungeeMute WHERE playerId ='" + playerId + "'");
			ResultSet getWarn = Main.getMySQL().getResult("SELECT * FROM BungeeWarn WHERE playerId ='" + playerId + "'");
			while(getBan.next()) {
				
				Ban ban = new Ban();
				ban.setBanBy(getBan.getInt("banBy"));
				ban.setPlayerId(playerId);
				ban.setBanReason(getBan.getString("banReason"));
				ban.setStartBan(getBan.getDate("banStart"));
				ban.setEndBan(getBan.getDate("endBan"));
				banList.add(ban);
				
			}
			while(getMute.next()) {
				
				Mute mute = new Mute();
				mute.setMuteBy(getMute.getInt("muteBy"));
				mute.setPlayerId(playerId);
				mute.setMuteReason(getMute.getString("muteReason"));
				mute.setStartMute(getMute.getDate("banMute"));
				mute.setEndMute(getMute.getDate("endMute"));
				muteList.add(mute);
				
			}
			while(getKick.next()) {
				
				Kick kick = new Kick();
				kick.setKickBy(getKick.getInt("kickBy"));
				kick.setKickDate(getKick.getDate("kickDate"));
				kick.setKickReason(getKick.getString("kickReason"));
				kick.setPlayerId(playerId);
				kickList.add(kick);
				
			}
			
			while(getWarn.next()) {
				
				Warn warn = new Warn();
				warn.setWarnBy(getWarn.getInt("kickBy"));
				warn.setWarnDate(getWarn.getDate("kickDate"));
				warn.setWarnReason(getWarn.getString("warnReason"));
				warn.setPlayerId(playerId);
				warnList.add(warn);
				
			}

			
		} catch (SQLException e ) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public int getPlayerId(String playerName) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT playerId FROM MinecraftPlayer WHERE username = '" + playerName + "'");
			if(id.next()) {

				int userID = id.getInt("userID");
				return userID;

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		return -1;
	}
}

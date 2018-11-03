package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	Timestamp now = new Timestamp(System.currentTimeMillis());
	
	
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
				ban.setStartBan(getBan.getTimestamp("banStart"));
				ban.setEndBan(getBan.getTimestamp("endBan"));
				ban.setId(getBan.getInt("id"));
				this.banList.add(ban);
				
			}
			while(getMute.next()) {
				
				Mute mute = new Mute();
				mute.setMuteBy(getMute.getInt("muteBy"));
				mute.setPlayerId(playerId);
				mute.setMuteReason(getMute.getString("muteReason"));
				mute.setStartMute(getMute.getTimestamp("banMute"));
				mute.setEndMute(getMute.getTimestamp("endMute"));
				mute.setId(getMute.getInt("id"));
				this.muteList.add(mute);
				
			}
			while(getKick.next()) {
				
				Kick kick = new Kick();
				kick.setKickBy(getKick.getInt("kickBy"));
				kick.setKickDate(getKick.getTimestamp("kickDate"));
				kick.setKickReason(getKick.getString("kickReason"));
				kick.setPlayerId(playerId);
				kick.setId(getKick.getInt("id"));
				this.kickList.add(kick);
				
			}
			
			while(getWarn.next()) {
				
				Warn warn = new Warn();
				warn.setWarnBy(getWarn.getInt("kickBy"));
				warn.setWarnDate(getWarn.getTimestamp("kickDate"));
				warn.setWarnReason(getWarn.getString("warnReason"));
				warn.setPlayerId(playerId);
				warn.setId(getBan.getInt("id"));
				this.warnList.add(warn);
				
			}
			
			if(!banList.isEmpty()) {
				
				for(Ban ban : banList) {
					
					if(now.compareTo(ban.getEndBan()) > 0) {
						this.isBanned = true;
						break;
					} else {
						this.isBanned = false;
					}
						
				}
			} else {
				this.isBanned = false;
			}
			
			if(!muteList.isEmpty()) {
				
				for(Mute mute : this.muteList) {
					
					if(now.compareTo(mute.getEndMute()) > 0) {
						this.isMuted = true;
						break;
					} else {
						this.isMuted = false;
					}
						
				}
			} else {
				this.isMuted = false;
			}
			
			
			
		} catch (SQLException | NullPointerException e) {
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

	public String getPlayername() {
		return playername;
	}

	public void setPlayername(String playername) {
		this.playername = playername;
	}

	public int getPlayerId() {
		return playerId;
	}

	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public List<Ban> getBanList() {
		return banList;
	}

	public void setBanList(List<Ban> banList) {
		this.banList = banList;
	}

	public List<Mute> getMuteList() {
		return muteList;
	}

	public void setMuteList(List<Mute> muteList) {
		this.muteList = muteList;
	}

	public List<Kick> getKickList() {
		return kickList;
	}

	public void setKickList(List<Kick> kickList) {
		this.kickList = kickList;
	}

	public List<Warn> getWarnList() {
		return warnList;
	}

	public void setWarnList(List<Warn> warnList) {
		this.warnList = warnList;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public void unban() {

		for(Ban ban : banList) {
			
			if(now.compareTo(ban.getEndBan()) > 0) {
				ban.setEndBan(new Timestamp(System.currentTimeMillis()));
				Main.getMySQL().update("UPDATE `BungeeBan` SET `banEnd` = `"+ ban.getEndBan() +"` WHERE 'id' = '"+ban.getId()+"'");
				break;
			}
		}
	}
	public void unmute() {

		for(Mute mute : muteList) {
			
			if(now.compareTo(mute.getEndMute()) > 0) {
				mute.setEndMute(new Timestamp(System.currentTimeMillis()));
				Main.getMySQL().update("UPDATE `BungeeMute` SET `banMute` = `"+ mute.getEndMute() +"` WHERE 'id' = '"+mute.getId()+"'");
				break;
			}
		}
	}
	
}

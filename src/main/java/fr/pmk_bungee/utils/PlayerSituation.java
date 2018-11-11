package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Ban;
import fr.pmk_bungee.object.Kick;
import fr.pmk_bungee.object.Mute;
import fr.pmk_bungee.object.Player;
import fr.pmk_bungee.object.Warn;

public class PlayerSituation {

	private String playername;
	private int playerId;
	private List<Ban> banList;
	private List<Mute> muteList;
	private List<Kick> kickList;
	private List<Warn> warnList;
	private Player player;
	static Timestamp now = new Timestamp(System.currentTimeMillis());


	public PlayerSituation(String playername){

		this.playername = playername;
		this.playerId = getPlayerId(playername);
		
		this.banList = new ArrayList<>();
		this.muteList = new ArrayList<>();
		this.kickList = new ArrayList<>();
		this.warnList = new ArrayList<>();
		
		init();
		
	}

	public void init() {

		try {
			
			ResultSet getBan = Main.getMySQL().getResult("SELECT * FROM BungeeBan WHERE playerId ='" + playerId + "'");
			ResultSet getKick = Main.getMySQL().getResult("SELECT * FROM BungeeKick WHERE playerId ='" + playerId + "'");
			ResultSet getMute = Main.getMySQL().getResult("SELECT * FROM BungeeMute WHERE playerId ='" + playerId + "'");
			ResultSet getWarn = Main.getMySQL().getResult("SELECT * FROM BungeeWarn WHERE playerId ='" + playerId + "'");
			ResultSet getPlayer = Main.getMySQL().getResult("SELECT * FROM MinecraftPlayer WHERE playerId ='" + playerId + "'");

			while(getBan.next()) {

				Ban ban = new Ban();
				ban.setBanBy(getBan.getInt("banBy"));
				ban.setPlayerId(this.playerId);
				ban.setBanReason(getBan.getString("banReason"));
				ban.setStartBan(getBan.getTimestamp("startBan"));
				ban.setEndBan(getBan.getTimestamp("endBan"));
				ban.setId(getBan.getInt("id"));
				banList.add(ban);

			}
			while(getMute.next()) {

				Mute mute = new Mute();
				mute.setMuteBy(getMute.getInt("muteBy"));
				mute.setPlayerId(this.playerId);
				mute.setMuteReason(getMute.getString("muteReason"));
				mute.setStartMute(getMute.getTimestamp("startMute"));
				mute.setEndMute(getMute.getTimestamp("endMute"));
				mute.setId(getMute.getInt("id"));
				muteList.add(mute);

			}
			while(getKick.next()) {

				Kick kick = new Kick();
				kick.setKickBy(getKick.getInt("kickBy"));
				kick.setKickDate(getKick.getTimestamp("kickDate"));
				kick.setKickReason(getKick.getString("kickReason"));
				kick.setPlayerId(this.playerId);
				kick.setId(getKick.getInt("id"));
				kickList.add(kick);

			}

			while(getWarn.next()) {

				Warn warn = new Warn();
				warn.setWarnBy(getWarn.getInt("kickBy"));
				warn.setWarnDate(getWarn.getTimestamp("kickDate"));
				warn.setWarnReason(getWarn.getString("warnReason"));
				warn.setPlayerId(this.playerId);
				warn.setId(getBan.getInt("id"));
				warnList.add(warn);

			}
			if(getPlayer.next()) {

				Player player = new Player();
				player.setPlayerId(getPlayer.getInt("playerId"));
				player.setUsername(getPlayer.getString("username"));
				player.setUuid(getPlayer.getString("uuid"));
				player.setFirstCome(getPlayer.getTimestamp("firstCome"));
				player.setLastCome(getPlayer.getTimestamp("lastCome"));
				
			}
		}		catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	//CHECKER 
	
	public boolean isBanned() {
		
		//TODO check is banned
		return false;
	}

	public boolean isMuted() {
		//TODO Check if muted
		return false;
	}
	
	// END CHECKER	
	
		
	// Ban / Mute SETTER
	
	public boolean setBanned() {
		
		//TODO SET BANNED
		return false;
	}
	
	public boolean setMuted() {
		
		//TODO SET MUTED
		return false;
	}
	
	
	// END SETTER
	
	
	// GETTER AND SETTER 
	
	public int getPlayerId(String playerName) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT playerId FROM MinecraftPlayer WHERE username = '" + playerName + "'");
			if(id.next()) {

				int playerId = id.getInt("playerId");
				return playerId;

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

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	
	
}

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

	public PlayerSituation(String playername){

		this.playername = playername;
		this.playerId = getPlayerId(playername);
		
		this.banList = new ArrayList<>();
		this.muteList = new ArrayList<>();
		this.kickList = new ArrayList<>();
		this.warnList = new ArrayList<>();
		this.player = new Player();
		
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
				warn.setWarnBy(getWarn.getInt("warnBy"));
				warn.setWarnDate(getWarn.getTimestamp("warnDate"));
				warn.setWarnReason(getWarn.getString("warnReason"));
				warn.setPlayerId(this.playerId);
				warn.setId(getWarn.getInt("id"));
				warnList.add(warn);

			}
			if(getPlayer.next()) {

				player.setPlayerId(getPlayer.getInt("playerId"));
				player.setUsername(getPlayer.getString("username"));
				player.setUuid(getPlayer.getString("uuid"));
				player.setFirstCome(getPlayer.getTimestamp("firstCome"));
				player.setLastCome(getPlayer.getTimestamp("lastCome"));
				player.setPlayerIp(getPlayer.getString("ip"));
				
			}
		}		catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	//CHECKER 
	
	public static boolean isBanned(String playername) {
		
		PlayerSituation situation = new PlayerSituation(playername);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if(situation != null) {
			if(!situation.getBanList().isEmpty()) {
				for(Ban ban : situation.getBanList()) {
					if(ban.getEndBan().after(now)) {
						System.out.println(now);
						System.out.println("BanCompara : "+ban.getEndBan().compareTo(now));
						return true;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}

	public static boolean isMuted(String playername) {

		PlayerSituation situation = new PlayerSituation(playername);
		Timestamp now = new Timestamp(System.currentTimeMillis());
		if(situation != null) {
			if(!situation.getMuteList().isEmpty()) {
				for(Mute mute : situation.getMuteList()) {
					if(mute.getEndMute().after(now)) {
						return true;
					}
				}
			} else {
				return false;
			}
		}
		return false;
	}
	
	// END CHECKER	
	
		
	// Ban / Mute / Kick / warn SETTER
	
	public static void setBanned(Ban ban) {
		
		Main.getMySQL().update("INSERT INTO `BungeeBan`(`playerId`,`startBan`, `endBan`, `banBy`, `banReason`) VALUES ('"+ban.getPlayerId()+"','"+ban.getStartBan()+"','"+ban.getEndBan()+"','"+ban.getBanBy()+"','"+ban.getBanReason()+"')");
	}
	
	public static void setMuted(Mute mute) {
		
		Main.getMySQL().update("INSERT INTO `BungeeMute`(`playerId`, `startMute`, `endMute`, `muteBy`, `muteReason`) VALUES ('"+mute.getPlayerId()+"','"+mute.getStartMute()+"','"+mute.getEndMute()+"','"+mute.getMuteBy()+"','"+mute.getMuteReason()+"')");
	}
	
	public static void setKick(Kick kick) {
		
		Main.getMySQL().update("INSERT INTO BungeeKick(playerId, kickDate, kickReason, kickBy) VALUES ('" 
				+ kick.getId()
				+ "', '" 
				+ kick.getKickDate()
				+ "','" 
				+ kick.getKickReason()
				+ "','" 
				+ kick.getKickBy()
				+ "')");
	}
	
	public static void setWarn(Warn warn) {
		
		Main.getMySQL().update("INSERT INTO BungeeWarn(playerID, warnDate, warnBy, warnReason) VALUES ('" 
				+ warn.getPlayerId()
				+ "', '" 
				+ warn.getWarnDate()
				+ "','" 
				+ warn.getWarnBy()
				+ "','" 
				+ warn.getWarnReason()
				+ "')");
		
	}
	// END SETTER
	
	// FORCE UNMUTE / UNBAN
	
	public static void unMute(String playername) {
		
		Timestamp now = new Timestamp(System.currentTimeMillis());
		PlayerSituation situation = new PlayerSituation(playername);
		for(Mute mute : situation.getMuteList()) {
			if(mute.getEndMute().after(now)) {
				Main.getMySQL().update("UPDATE `BungeeMute` SET `endMute`= '"+now+"' WHERE `id` = '"+mute.getId()+"'");
			}
		}
	}
	
	public static void unBan(String playername) {
		Timestamp now = new Timestamp(System.currentTimeMillis());
		PlayerSituation situation = new PlayerSituation(playername);
		for(Ban ban : situation.getBanList()) {
			if(ban.getEndBan().after(now)) {
				Main.getMySQL().update("UPDATE `BungeeBan` SET `endBan` = '"+now+"' WHERE `id` = '"+ban.getId()+"'");
			}
		}
		
	}

	// END FORCE UNMUTE / UNBAN
	
	// MESSAGE FOR BAN AND MUTE
	
	public static String getBanMessage(String playername)
	{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Ban forKick = new Ban();
		PlayerSituation situation = new PlayerSituation(playername);
		for(Ban ban : situation.getBanList()) {
			if(ban.getEndBan().after(now)) {
				forKick = ban;
			}
		}
		
		List<String> lines = Main.getConfigManager().getStringList("lang.banmessage", new String[] {
				"{REASON}~" + forKick.getBanReason(), 
				"{BY}~" + getUsername(forKick.getBanBy()), 
				"{REMAININGTIME}~" + getRemainingTime(forKick.getEndBan()) });
		String str = "";
		for (String line : lines) {
			str = str + line + "\n";
		}
		return str;
	}
	public static String[] getMuteMessage(String playername)
	{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		Mute forMute = new Mute();
		PlayerSituation situation = new PlayerSituation(playername);
		for(Mute mute : situation.getMuteList()) {
			if(mute.getEndMute().after(now)) {
				forMute = mute;
			}
		}
		
		List<String> lines = Main.getConfigManager().getStringList("lang.mutemessage", new String[] {
				"{REASON}~" + forMute.getMuteReason(), 
				"{BY}~" + getUsername(forMute.getMuteBy()), 
				"{REMAININGTIME}~" + getRemainingTime(forMute.getEndMute()) });
		String[] str = new String [] {lines.get(0), lines.get(1), lines.get(2), lines.get(3)};
		return str;
	}
	
	// END MESSAGE FOR BAN AND MUTE
	
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
	
	public static String getUsername(int userID) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT username FROM MinecraftPlayer WHERE playerId = '" 
					+ userID 
					+ "'");

			if(id.next()) {

				String username = id.getString("username");
				return username;
			}

		} catch (SQLException e) {

			e.printStackTrace(); 
		} return "";
	}
	
	public static String getRemainingTime(Timestamp ender) {
			long end = ender.getTime();
			if (end > 0L)
			{
				long millis = end - System.currentTimeMillis();
				int days = 0;
				int hours = 0;
				int minutes = 0;
				int seconds = 0;
				while (millis >= 1000L)
				{
					seconds++;
					millis -= 1000L;
				}
				while (seconds >= 60)
				{
					minutes++;
					seconds -= 60;
				}
				while (minutes >= 60)
				{
					hours++;
					minutes -= 60;
				}
				while (hours >= 24)
				{
					days++;
					hours -= 24;
				}
				return Main.getConfigManager().timeFormat(days, hours, minutes, seconds);
			}
			return Main.getConfigManager().getString("lang.time_format_permanent");
	}
}

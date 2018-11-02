package fr.pmk_bungee.utils;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import fr.pmk_bungee.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class PlayerProfile {

	private String playerName;

	private String banReason;
	private int banBy;
	private boolean isBanned;
	private long banEnd;
	private Date inNowBan;

	private String muteReason;
	private int mutedBy;
	private boolean isMuted;
	private long muteEnd;
	private Date inNowMute;

	public PlayerProfile(String player) {
		this.playerName = player;
		init();
	}

	public void init() {
		try {

			ResultSet rs = Main.getMySQL().getResult("SELECT * FROM ActualBungeeBan WHERE userID ='" + getUserID(playerName) + "'");
			if(rs.next()) {

				this.isBanned = true;
				this.banEnd = rs.getLong("banEnd");
				this.banReason = rs.getString("banReason");
				this.banBy = rs.getInt("banBy");
				this.inNowBan = rs.getDate("banAt");
			}
			else {

				this.isBanned = false;
				this.banBy = 0;
				this.banEnd = 0;
				this.banReason = "";
				this.inNowBan = null;
			}

		} catch (SQLException e ) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}

		try {
      
			ResultSet rs = Main.getMySQL().getResult("SELECT * FROM ActualBungeeMutes WHERE userID = '" + getUserID(playerName) + "'");
			if(rs.next()) {

				this.isMuted = true;
				this.muteEnd = rs.getLong("muteEnd");
				this.muteReason = rs.getString("muteReason");
				this.mutedBy = rs.getInt("muteBy");
				this.inNowMute = rs.getDate("muteAt");
			}
			else {

				this.isMuted = false;
				this.muteEnd = 0;
				this.muteReason = "";
				this.mutedBy = 0;
				this.inNowMute = null;
			}

		} catch (SQLException e) {

			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}


	}

	// ALL THE BAN

	@SuppressWarnings("deprecation")
	public void setBanned(String reason, int by, long seconds, Date inNow) {

		long end;
		if(seconds == -1L){
			end = -1L;
		}
		else {

			end = System.currentTimeMillis() + seconds * 1000L;
		}
		this.isBanned = true;
		setBanEnd(end);
		setBanReason(reason);
		setBanBy(by);
		setInNowBan(inNow);
		if(toProxiedPlayer() != null) {

			toProxiedPlayer().disconnect(getBanKickMessage());
		}
		Main.getMySQL().update("INSERT INTO ActualBungeeBan(userID, banEnd, banBy, banReason, banAt) VALUES ('" 
				+ this.getUserID(playerName) 
				+ "', '" 
				+ getBanEnd() 
				+ "','" 
				+ getBanBy() 
				+ "','" 
				+ getBanReason()
				+ "','" 
				+ getInNowBan()
				+ "')");

	}

	public void unban() {

		Main.getMySQL().update("DELETE FROM ActualBungeeBan WHERE userID = '" 
				+ this.getUserID(playerName) 
				+ "'");

		Main.getMySQL().update("INSERT INTO PastBungeeBan(userID, banAt, banBy, banReason) VALUES ('" 
				+ this.getUserID(playerName) 
				+ "', '" 
				+ getInNowBan() 
				+ "','" 
				+ getBanBy() 
				+ "','" 
				+ getBanReason() 
				+ "')");

	}

	public String getBanKickMessage()
	{
		List<String> lines = Main.getConfigManager().getStringList("lang.banmessage", new String[] {
				"{REASON}~" + getBanReason(), 
				"{BY}~" + getUsername(getBanBy()), 
				"{REMAININGTIME}~" + getRemainingbanTime() });
		String str = "";
		for (String line : lines) {
			str = str + line + "\n";
		}
		return str;
	}

	public String getBanReason() {
		return banReason;
	}

	public void setBanReason(String banReason) {
		this.banReason = banReason;
	}

	public int getBanBy() {
		return banBy;
	}

	public void setBanBy(int by) {
		this.banBy = by;
	}

	public boolean isBanned() {
		return isBanned;
	}

	public void setBanned(boolean isBanned) {
		this.isBanned = isBanned;
	}

	public long getBanEnd() {
		return banEnd;
	}

	public void setBanEnd(long banEnd) {
		this.banEnd = banEnd;
	}

	public String getRemainingbanTime()
	{
		if (isBanned())
		{
			long end = getBanEnd();
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
		return null;
	}

	// ALL THE MUTE

	@SuppressWarnings("deprecation")
	public void setMute(String reason, int by, long seconds, Date inNow){

		long end;
		if (seconds == -1L) {
			end = -1L;

		}
		else {
			end = System.currentTimeMillis() + seconds * 1000L;

		}
		this.isMuted = true;
		setMuteEnd(end);
		setMuteReason(reason);
		setMutedBy(by);
		setInNowMute(inNow);
		for(int i = 0;i < ProxyServer.getInstance().getPlayers().size(); i++) {

			Object[] players = ProxyServer.getInstance().getPlayers().toArray();

			if(toProxiedPlayer() == players[i]) {
				toProxiedPlayer().sendMessages(Main.PREFIX + getMuteMessage());
			}
		}


		Main.getMySQL().update("INSERT INTO ActualBungeeMutes(userID, muteEnd, muteReason, muteBy, muteAt) VALUES ('" 
				+ this.getUserID(playerName) 
				+ "','" 
				+ getMuteEnd() 
				+ "','" 
				+ getMuteReason() 
				+ "', '" 
				+ getMutedBy() 
				+ "', '"
				+ getInNowMute()
				+ "')"); 
	}

	public void unmute() {

		Main.getMySQL().update("DELETE FROM ActualBungeeMutes WHERE userID = '" 
				+ this.getUserID(playerName) 
				+ "'");

		Main.getMySQL().update("INSERT INTO PastBungeeMutes(userID, muteAt, muteReason, muteBy) VALUES ('" 
				+ this.getUserID(playerName) 
				+ "','" 
				+ getInNowMute()
				+ "','" 
				+ getMuteReason() 
				+ "', '" 
				+ getMutedBy() 
				+ "')"); 

	}

	public String[] getMuteMessage()
	{
		List<String> lines = Main.getConfigManager().getStringList("lang.mutemessage", new String[] {
				"{REASON}~" + getMuteReason(), 
				"{BY}~" + getUsername(getMutedBy()), 
				"{REMAININGTIME}~" + getRemainingmuteTime() });
		String[] str = new String [] {lines.get(0), lines.get(1), lines.get(2), lines.get(3)};
		return str;
	}

	public String getMuteReason() {
		return muteReason;
	}

	public void setMuteReason(String muteReason) {
		this.muteReason = muteReason;
	}

	public int getMutedBy() {
		return mutedBy;
	}

	public void setMutedBy(int by) {
		this.mutedBy = by;
	}

	public boolean isMuted() {
		return isMuted;
	}

	public void setMuted(boolean isMuted) {
		this.isMuted = isMuted;
	}

	public long getMuteEnd() {
		return muteEnd;
	}

	public void setMuteEnd(long muteEnd) {

		this.muteEnd = muteEnd;
	}

	public String getRemainingmuteTime()
	{
		if (isMuted())
		{
			long end = getMuteEnd();
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
		return null;
	}

	//OTHER

	public ProxiedPlayer toProxiedPlayer() {

		return ProxyServer.getInstance().getPlayer(playerName);
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	private int getUserID(String playerName) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT userID FROM MinecraftPlayer WHERE username = '" + playerName + "'");
			if(id.next()) {

				int userID = id.getInt("userID");
				return userID;

			}
		} catch (SQLException e) {

			e.printStackTrace();
		} 
		return -1;
	}

	public String getUsername(int userID) {

		try {
			ResultSet id = Main.getMySQL().getResult("SELECT username FROM MinecraftPlayer WHERE userID = '" 
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

	public void refreshPlayerData() {

		init();
	}

	public Date getInNowBan() {
		return inNowBan;
	}

	public void setInNowBan(Date inNowBan) {
		this.inNowBan = inNowBan;
	}

	public Date getInNowMute() {
		return inNowMute;
	}

	public void setInNowMute(Date inNowMute) {
		this.inNowMute = inNowMute;
	}


}
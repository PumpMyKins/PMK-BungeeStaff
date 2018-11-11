package fr.pmk_bungee.object;

import java.sql.Timestamp;

public class Player {

	private int playerId;
	private String username;
	private String uuid;
	private Timestamp firstCome;
	private Timestamp lastCome;
	private String playerIp;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Timestamp getFirstCome() {
		return firstCome;
	}
	public void setFirstCome(Timestamp firstCome) {
		this.firstCome = firstCome;
	}
	public Timestamp getLastCome() {
		return lastCome;
	}
	public void setLastCome(Timestamp lastCome) {
		this.lastCome = lastCome;
	}
	public String getPlayerIp() {
		return playerIp;
	}
	public void setPlayerIp(String playerIp) {
		this.playerIp = playerIp;
	}

	
	
}

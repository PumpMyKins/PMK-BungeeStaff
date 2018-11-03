package fr.pmk_bungee.object;

import java.util.Date;

public class Joueur {

	private int playerId;
	private String username;
	private String uuid;
	private Date firstCome;
	private Date lastCome;
	
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
	public Date getFirstCome() {
		return firstCome;
	}
	public void setFirstCome(Date firstCome) {
		this.firstCome = firstCome;
	}
	public Date getLastCome() {
		return lastCome;
	}
	public void setLastCome(Date lastCome) {
		this.lastCome = lastCome;
	}
	
	
}

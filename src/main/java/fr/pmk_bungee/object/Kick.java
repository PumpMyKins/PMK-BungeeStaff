package fr.pmk_bungee.object;

import java.sql.Timestamp;

public class Kick {

	private int playerId;
	private Timestamp kickDate;
	private String kickReason;
	private int kickBy;
	private int id;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}

	public String getKickReason() {
		return kickReason;
	}
	public void setKickReason(String kickReason) {
		this.kickReason = kickReason;
	}
	public int getKickBy() {
		return kickBy;
	}
	public void setKickBy(int kickBy) {
		this.kickBy = kickBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getKickDate() {
		return kickDate;
	}
	public void setKickDate(Timestamp kickDate) {
		this.kickDate = kickDate;
	}
	
	
}

package fr.pmk_bungee.object;

import java.sql.Timestamp;

public class Warn {

	private int playerId;
	private Timestamp warnDate;
	private String warnReason;
	private int warnBy;
	private int id;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public String getWarnReason() {
		return warnReason;
	}
	public void setWarnReason(String warnReason) {
		this.warnReason = warnReason;
	}
	public int getWarnBy() {
		return warnBy;
	}
	public void setWarnBy(int warnBy) {
		this.warnBy = warnBy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getWarnDate() {
		return warnDate;
	}
	public void setWarnDate(Timestamp warnDate) {
		this.warnDate = warnDate;
	}

	
}

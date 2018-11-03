package fr.pmk_bungee.object;

import java.util.Date;

public class Kick {

	private int playerId;
	private Date kickDate;
	private String kickReason;
	private int kickBy;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public Date getKickDate() {
		return kickDate;
	}
	public void setKickDate(Date kickDate) {
		this.kickDate = kickDate;
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
	
	
}

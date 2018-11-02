package fr.pmk_bungee.utils;

import java.sql.Date;

public class PastMute {

	private String playername;
	private Date muteAt;
	private int muteBy;
	private String muteReason;
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public Date getMuteAt() {
		return muteAt;
	}
	public void setMuteAt(Date muteAt) {
		this.muteAt = muteAt;
	}
	public int getMuteBy() {
		return muteBy;
	}
	public void setMuteBy(int muteBy) {
		this.muteBy = muteBy;
	}
	public String getMuteReason() {
		return muteReason;
	}
	public void setMuteReason(String muteReason) {
		this.muteReason = muteReason;
	}


}

package fr.pmk_bungee.utils;

import java.sql.Date;

public class PastKick {
	
	private String playername;
	private Date kickAt;
	private int kickBy;
	private String kickReason;
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public Date getKickAt() {
		return kickAt;
	}
	public void setKickAt(Date kickAt) {
		this.kickAt = kickAt;
	}
	public int getKickBy() {
		return kickBy;
	}
	public void setKickBy(int kickBy) {
		this.kickBy = kickBy;
	}
	public String getKickReason() {
		return kickReason;
	}
	public void setKickReason(String kickReason) {
		this.kickReason = kickReason;
	}
	
	
}

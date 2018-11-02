package fr.pmk_bungee.utils;

import java.sql.Date;

public class Warn {
	
	private String playername;
	private Date warnAt;
	private int warnBy;
	private String warnReason;
	
	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public Date getWarnAt() {
		return warnAt;
	}
	public void setWarnAt(Date warnAt) {
		this.warnAt = warnAt;
	}
	public int getWarnBy() {
		return warnBy;
	}
	public void setWarnBy(int warnBy) {
		this.warnBy = warnBy;
	}
	public String getWarnReason() {
		return warnReason;
	}
	public void setWarnReason(String warnReason) {
		this.warnReason = warnReason;
	}
	
	
}

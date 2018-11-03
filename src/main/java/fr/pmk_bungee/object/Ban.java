package fr.pmk_bungee.object;

import java.util.Date;

public class Ban {
	
	private int playerId;
	private Date startBan;
	private Date endBan;
	private int banBy;
	private String banReason;
	

	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public int getBanBy() {
		return banBy;
	}
	public void setBanBy(int banBy) {
		this.banBy = banBy;
	}
	public String getBanReason() {
		return banReason;
	}
	public void setBanReason(String banReason) {
		this.banReason = banReason;
	}
	public Date getEndBan() {
		return endBan;
	}
	public void setEndBan(Date endBan) {
		this.endBan = endBan;
	}
	public Date getStartBan() {
		return startBan;
	}
	public void setStartBan(Date startBan) {
		this.startBan = startBan;
	}
	
	
}

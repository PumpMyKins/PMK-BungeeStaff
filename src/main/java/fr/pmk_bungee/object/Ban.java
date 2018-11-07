package fr.pmk_bungee.object;

import java.sql.Timestamp;

public class Ban {
	
	private int playerId;
	private Timestamp startBan;
	private Timestamp endBan;
	private int banBy;
	private String banReason;
	private int id;
	

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

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getStartBan() {
		return startBan;
	}
	public void setStartBan(Timestamp startBan) {
		this.startBan = startBan;
	}
	public Timestamp getEndBan() {
		return endBan;
	}
	public void setEndBan(Timestamp endBan) {
		this.endBan = endBan;
	}
	
	
}

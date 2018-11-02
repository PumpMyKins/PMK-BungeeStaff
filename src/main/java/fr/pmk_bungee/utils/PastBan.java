package fr.pmk_bungee.utils;

import java.sql.Date;

public class PastBan {

	private String playername;
	private Date banAt;
	private int banBy;
	private String banReason;

	public String getPlayername() {
		return playername;
	}
	public void setPlayername(String playername) {
		this.playername = playername;
	}
	public Date getBanAt() {
		return banAt;
	}
	public void setBanAt(Date banAt) {
		this.banAt = banAt;
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


}

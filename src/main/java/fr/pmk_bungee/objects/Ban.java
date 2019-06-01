package fr.pmk_bungee.objects;

import java.util.Date;
import java.util.UUID;

public class Ban {

	private int banId;
	private Date banAt;
	private int banDuration;
	private UUID banBy;
	private UUID bannedPlayer;
	private String banIp;
	private String banReason;
	
	public Ban(Date banAt, int banDuration, UUID banBy, UUID bannedPlayer, String banIp) {
		
		this.banAt = banAt;
		this.banDuration = banDuration;
		this.banBy = banBy;
		this.bannedPlayer = bannedPlayer;
		this.banIp = banIp;
	}
	public Ban() {
		
	}

	public Date getBanAt() {
		return banAt;
	}

	public void setBanAt(Date banAt) {
		this.banAt = banAt;
	}

	public int getBanDuration() {
		return banDuration;
	}

	public void setBanDuration(int banDuration) {
		this.banDuration = banDuration;
	}

	public UUID getBanBy() {
		return banBy;
	}

	public void setBanBy(UUID banBy) {
		this.banBy = banBy;
	}

	public UUID getBannedPlayer() {
		return bannedPlayer;
	}

	public void setBannedPlayer(UUID bannedPlayer) {
		this.bannedPlayer = bannedPlayer;
	}

	public String getBanIp() {
		return banIp;
	}

	public void setBanIp(String banIp) {
		this.banIp = banIp;
	}

	public String getBanReason() {
		return banReason;
	}

	public void setBanReason(String banReason) {
		this.banReason = banReason;
	}
	public int getBanId() {
		return banId;
	}
	public void setBanId(int banId) {
		this.banId = banId;
	}
}

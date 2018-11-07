package fr.pmk_bungee.object;

import java.sql.Timestamp;

public class Mute {

	private int playerId;
	private Timestamp startMute;
	private Timestamp endMute;
	private int muteBy;
	private String muteReason;
	private int id;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Timestamp getStartMute() {
		return startMute;
	}
	public void setStartMute(Timestamp startMute) {
		this.startMute = startMute;
	}
	public Timestamp getEndMute() {
		return endMute;
	}
	public void setEndMute(Timestamp endMute) {
		this.endMute = endMute;
	}
	
	
}

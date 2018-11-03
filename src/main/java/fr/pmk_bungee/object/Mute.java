package fr.pmk_bungee.object;

import java.util.Date;

public class Mute {

	private int playerId;
	private Date startMute;
	private Date endMute;
	private int muteBy;
	private String muteReason;
	
	public int getPlayerId() {
		return playerId;
	}
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	public Date getStartMute() {
		return startMute;
	}
	public void setStartMute(Date startMute) {
		this.startMute = startMute;
	}
	public Date getEndMute() {
		return endMute;
	}
	public void setEndMute(Date endMute) {
		this.endMute = endMute;
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

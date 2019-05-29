package fr.pmk_bungee.objects;

import java.util.Date;
import java.util.UUID;

public class Mute {

	private int muteId;
	private Date muteAt;
	private int muteDuration;
	private UUID muteBy;
	private UUID mutePlayer;
	private String muteReason;
	
	public Mute(Date muteAt, int muteDuration, UUID muteBy, UUID mutePlayer, String muteReason) {
		
		this.muteAt = muteAt;
		this.muteDuration = muteDuration;
		this.muteBy = muteBy;
		this.mutePlayer = mutePlayer;
		this.muteReason = muteReason;
	}
	public Mute() {
		
	}

	public Date getMuteAt() {
		return muteAt;
	}

	public void setMuteAt(Date muteAt) {
		this.muteAt = muteAt;
	}

	public int getMuteDuration() {
		return muteDuration;
	}

	public void setMuteDuration(int muteDuration) {
		this.muteDuration = muteDuration;
	}

	public UUID getMuteBy() {
		return muteBy;
	}

	public void setMuteBy(UUID muteBy) {
		this.muteBy = muteBy;
	}

	public UUID getMutePlayer() {
		return mutePlayer;
	}

	public void setMutePlayer(UUID mutePlayer) {
		this.mutePlayer = mutePlayer;
	}

	public String getMuteReason() {
		return muteReason;
	}

	public void setMuteReason(String muteReason) {
		this.muteReason = muteReason;
	}
	public int getMuteId() {
		return muteId;
	}
	public void setMuteId(int muteId) {
		this.muteId = muteId;
	}
	
	
}


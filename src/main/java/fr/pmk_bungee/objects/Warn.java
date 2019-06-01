package fr.pmk_bungee.objects;

import java.util.Date;
import java.util.UUID;

public class Warn {

	private int warnId;
	private UUID warnBy;
	private UUID warnPlayer;
	private String warnReason;
	private Date warnDate;
	
	public Warn(UUID warnBy, UUID warnPlayer, String warnReason, Date warnDate) {
		
		this.warnBy = warnBy;
		this.warnPlayer = warnPlayer;
		this.warnReason = warnReason;
		this.warnDate = warnDate;
	}
	public Warn() {
		
	}

	public UUID getWarnBy() {
		return warnBy;
	}

	public void setWarnBy(UUID warnBy) {
		this.warnBy = warnBy;
	}

	public UUID getWarnPlayer() {
		return warnPlayer;
	}

	public void setWarnPlayer(UUID warnPlayer) {
		this.warnPlayer = warnPlayer;
	}

	public String getWarnReason() {
		return warnReason;
	}

	public void setWarnReason(String warnReason) {
		this.warnReason = warnReason;
	}

	public Date getWarnDate() {
		return warnDate;
	}

	public void setWarnDate(Date warnDate) {
		this.warnDate = warnDate;
	}
	public int getWarnId() {
		return warnId;
	}
	public void setWarnId(int warnId) {
		this.warnId = warnId;
	}
	
	
}

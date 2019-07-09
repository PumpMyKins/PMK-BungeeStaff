package fr.pumpmybstaff.objects;

import java.util.Date;
import java.util.UUID;

public class Kick {

	private int kickId;
	private UUID kickBy;
	private UUID kickPlayer;
	private String kickReason;
	private Date kickDate;
	
	public Kick(UUID kickBy, UUID kickPlayer, String kickReason, Date kickDate) {
		
		this.kickBy = kickBy;
		this.kickPlayer = kickPlayer;
		this.kickReason = kickReason;
		this.kickDate = kickDate;
		
	}
	public Kick() {
		
	}

	public UUID getKickBy() {
		return kickBy;
	}

	public void setKickBy(UUID kickBy) {
		this.kickBy = kickBy;
	}

	public UUID getKickPlayer() {
		return kickPlayer;
	}

	public void setKickPlayer(UUID kickPlayer) {
		this.kickPlayer = kickPlayer;
	}

	public String getKickReason() {
		return kickReason;
	}

	public void setKickReason(String kickReason) {
		this.kickReason = kickReason;
	}

	public Date getKickDate() {
		return kickDate;
	}

	public void setKickDate(Date kickDate) {
		this.kickDate = kickDate;
	}
	public int getKickId() {
		return kickId;
	}
	public void setKickId(int kickId) {
		this.kickId = kickId;
	}
	
	
}

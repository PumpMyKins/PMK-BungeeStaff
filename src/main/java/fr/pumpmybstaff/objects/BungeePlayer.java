package fr.pumpmybstaff.objects;

import java.util.Date;
import java.util.UUID;

public class BungeePlayer {

	private String username;
	private UUID uuid;
	private String ip;
	private Date firstCome;
	private Date lastConnection;
	
	public BungeePlayer() {
		
		
	}
	
	public BungeePlayer(String username, UUID uuid, String ip, Date firstCome, Date lastConnection) {
		
		this.username = username;
		this.uuid = uuid;
		this.ip = ip;
		this.firstCome = firstCome;
		this.lastConnection = lastConnection;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public UUID getUniqueId() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getFirstCome() {
		return firstCome;
	}
	public void setFirstCome(Date firstCome) {
		this.firstCome = firstCome;
	}
	public Date getLastConnection() {
		return lastConnection;
	}
	public void setLastConnection(Date lastConnection) {
		this.lastConnection = lastConnection;
	}
	
	
}

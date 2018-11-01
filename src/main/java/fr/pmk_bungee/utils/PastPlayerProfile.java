package fr.pmk_bungee.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;

public class PastPlayerProfile {

	private String playerName;
	
	private ArrayList<Integer> pastBan;
	private ArrayList<Integer> pastMute;
	//private ArrayList<String> pastKick;
	
	private ArrayList<String> banReason;
	private ArrayList<String> banBy;
	
	private ArrayList<String> muteReason;
	private ArrayList<String> mutedBy;
	
	public PastPlayerProfile(String player) {
		
		this.playerName = player;
		init();
	}
	
	public void init() {
		try {
			System.out.println(getUserID(playerName));
			ResultSet rs = Main.getMySQL().getResult("SELECT * FROM PastBungeeBan WHERE userID ='" + getUserID(this.playerName) + "'");
			while(rs.next()) {
				
				this.pastBan.add(rs.getInt("banID"));
			}
			if(this.pastBan.size() > 0) {
				
				for(int i = 0; i <= this.pastBan.size(); i++) {
					
					try {
						ResultSet rs2 = Main.getMySQL().getResult("SELECT * FROM PastBungeeBan WHERE banID = '" + this.pastBan.get(i) + "'");
						if(rs.next()) {
							
							this.banReason.add(i, rs2.getString("banReason"));
							this.banBy.add(i, getUsername(rs2.getInt("banBy")));
						
						}
						
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		try {
			ResultSet rs3 = Main.getMySQL().getResult("SELECT * FROM PastBungeeMutes WHERE userID ='" + getUserID(playerName) + "'");
			while(rs3.next()) {
				
				this.pastMute.add(rs3.getInt("muteID"));
			}
			if(this.pastMute.size() > 0) {
				
				for(int i = 0; i <= this.pastMute.size(); i++) {
					
					try {
						ResultSet rs4 = Main.getMySQL().getResult("SELECT * FROM PastBungeeBan WHERE banID = '" + this.pastMute.get(i) + "'");
						if(rs4.next()) {
							
							this.muteReason.add(rs4.getString("muteReason"));
							this.mutedBy.add(getUsername(rs4.getInt("mutedBy")));
						
						}
						
					} catch (SQLException e) {
						
						e.printStackTrace();
					} catch (NullPointerException e) {
						e.printStackTrace();
					}
					
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public String[] getPastBanMessage(int i) {
		
		List<String> lines = Main.getConfigManager().getStringList("lang.pastbanmessage", new String[] {
				 "{REASON}~" + getBanReason().get(i),
				 "{BY}~" +getBanBy().get(i)
		});
		String[] str = new String [] {lines.get(0), lines.get(1), lines.get(2)};
		return str;
	}
	
	public String[] getPastMuteMessage(int i) {
		
		List<String> lines = Main.getConfigManager().getStringList("lang.pastmutemessage", new String[] {
				 "{REASON}~" + getMuteReason().get(i),
				 "{BY}~" +getMutedBy().get(i)
		});
		String[] str = new String [] {lines.get(0), lines.get(1), lines.get(2)};
		return str;
	}
	
	private int getUserID(String playerName) {
		
		try {
			ResultSet id = Main.getMySQL().getResult("SELECT userID FROM MinecraftPlayer WHERE username = '" + this.playerName + "'");
			if(id.next()) {
				
				int userID = id.getInt("userID");
				System.out.println(userID);
				return userID;
			
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
		return -1;
	}

	public String getUsername(int userID) {
		
		try {
			ResultSet id = Main.getMySQL().getResult("SELECT username FROM MinecraftPlayer WHERE userID = '" 
					+ userID 
					+ "'");
			
			if(id.next()) {
				
				String username = id.getString("username");
				return username;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		} return "";
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public ArrayList<Integer> getPastBan() {
		return pastBan;
	}

	public void setPastBan(ArrayList<Integer> pastBan) {
		this.pastBan = pastBan;
	}

	public ArrayList<Integer> getPastMute() {
		return pastMute;
	}

	public void setPastMute(ArrayList<Integer> pastMute) {
		this.pastMute = pastMute;
	}

	public ArrayList<String> getBanReason() {
		return banReason;
	}

	public void setBanReason(ArrayList<String> banReason) {
		this.banReason = banReason;
	}

	public ArrayList<String> getBanBy() {
		return banBy;
	}

	public void setBanBy(ArrayList<String> banBy) {
		this.banBy = banBy;
	}

	public ArrayList<String> getMuteReason() {
		return muteReason;
	}

	public void setMuteReason(ArrayList<String> muteReason) {
		this.muteReason = muteReason;
	}

	public ArrayList<String> getMutedBy() {
		return mutedBy;
	}

	public void setMutedBy(ArrayList<String> mutedBy) {
		this.mutedBy = mutedBy;
	}
	
}

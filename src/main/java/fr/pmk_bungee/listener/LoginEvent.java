package fr.pmk_bungee.listener;

import java.sql.ResultSet;
import java.sql.SQLException;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {
		
		String playerName = e.getConnection().getName();
		boolean isPresent = false;
		try {
			ResultSet rs = Main.getMySQL().getResult("SELECT isPresent FROM MinecraftPlayer WHERE username = '" + e.getConnection().getName()+ "'"); 
			if(rs.next()) {
				
				isPresent = rs.getBoolean("isPresent");
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
		} catch (NullPointerException e2) {
			isPresent = false;
			e2.printStackTrace();
		}
		if(!isPresent) {
			Main.getMySQL().update("INSERT IGNORE INTO MinecraftPlayer(username, uuid, ip, isPresent) VALUES ('" 
				+ playerName 
				+ "', '" 
				+ e.getConnection().getUniqueId() 
				+ "', '" 
				+ e.getConnection().getAddress().getAddress()
				+ "','true')");
		}
			PlayerProfile profile = new PlayerProfile(e.getConnection().getName());
			if(profile.isBanned()) {
				
				long end = profile.getBanEnd();
				long current = System.currentTimeMillis();
				
				if(end > 0L) {
					
					if(end < current) {
						profile.unban();
					}
				
					else {
					
						e.setCancelled(true);
						e.setCancelReason(profile.getBanKickMessage() + "");
					}
				}else {
				
					e.setCancelled(true);
					e.setCancelReason(profile.getBanKickMessage() + "");
				}
			}
	}
}

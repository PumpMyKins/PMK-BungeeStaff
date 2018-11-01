package fr.pmk_bungee.listener;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {
		
		try {
			
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
				}	else {
				
					e.setCancelled(true);
					e.setCancelReason(profile.getBanKickMessage() + "");
					}
			}
		}
		catch (NullPointerException error) {
			
			Main.getMySQL().update("INSERT INTO MinecraftPlayer(username, uuid, ip) VALUES ('" + e.getConnection().getName() + "', '" + e.getConnection().getUniqueId() + "', '" + e.getConnection().getAddress() + "')");
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
				}	else {
				
					e.setCancelled(true);
					e.setCancelReason(profile.getBanKickMessage() + "");
					}
			}
			
		}
		
	}
}

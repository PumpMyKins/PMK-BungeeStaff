package fr.pmk_bungee.listener;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onChar(net.md_5.bungee.api.event.ChatEvent e) {
		
		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		PlayerProfile profile = new PlayerProfile(p.getName());
		
		if(profile.isMuted()) {
			
			if(e.getMessage().startsWith("/")) {
				
				return;
			}
			long end = profile.getMuteEnd();
			long current = System.currentTimeMillis();
			
			if(end == -1L) {
				
				e.setCancelled(true); 
				e.setMessage("");
				p.sendMessages(Main.PREFIX + profile.getMuteMessage());
				return;
			}
			if(end > current) {
				
				e.setCancelled(true);
				e.setMessage("");
				p.sendMessages(Main.PREFIX + profile.getMuteMessage());
				return;
			}
			else {
				
				e.setCancelled(false);
				profile.unmute();
			}
		}
	}

}

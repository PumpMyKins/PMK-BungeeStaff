package fr.pmk_bungee.listener;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerProfile;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {

	@EventHandler
	public void onChar(net.md_5.bungee.api.event.ChatEvent e) {

		ProxiedPlayer proxiedPlayer = (ProxiedPlayer) e.getSender();
		PlayerSituation situation = new PlayerSituation(proxiedPlayer.getName());

		if(situation.isMuted()) {

			if(e.getMessage().startsWith("/")) {

				return;
			}
			if(situation.isMuted()) {
				
				if(!situation.unmute()) {
					
					//e.setCancelled(situation.getBanKickMessage(););
				}
			}
		}
	}

}

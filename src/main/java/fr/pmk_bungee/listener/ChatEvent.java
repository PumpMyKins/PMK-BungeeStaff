package fr.pmk_bungee.listener;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {

	@EventHandler
	public void onChar(net.md_5.bungee.api.event.ChatEvent e) {

		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		PlayerSituation situation = new PlayerSituation(p.getName());

		//TODO add test mute

		if(e.getMessage().startsWith("/")) {

			return;
		}
		if (!situation.getMuteList().isEmpty()) {
			if(PlayerSituation.isMuted(p.getName())) {
				e.setCancelled(true);
				for(String str : PlayerSituation.getMuteMessage(p.getName())) {

					p.sendMessage(new TextComponent(Main.PREFIX + str));
				}
			}

		}				
	}
}



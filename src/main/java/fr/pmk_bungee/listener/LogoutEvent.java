package fr.pmk_bungee.listener;

import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Player;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LogoutEvent implements Listener{

	@EventHandler
	public void onLogout(net.md_5.bungee.api.event.ServerDisconnectEvent e) {
		
		Player player = new Player();
		player.setUsername(e.getPlayer().getName());
		player.setLastCome(new Timestamp(System.currentTimeMillis()));
		Main.getMySQL().update("UPDATE 'MinecraftPlayer' SET 'lastCome' = '"+player.getLastCome()+"' WHERE 'playerId' = '"+player.getPlayerId()+"'");
	}
}

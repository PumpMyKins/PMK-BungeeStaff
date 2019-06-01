package fr.pmk_bungee.listener;

import java.util.List;

import fr.pmk_bungee.objects.BungeePlayer;
import fr.pmk_bungee.objects.PlayersLog;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;

public class LogoutEvent {

	private PlayersLog pl;
	
	public LogoutEvent(PlayersLog lg) {
		
		this.pl = lg;
	}
	
	public void PlayerDisconnect(PlayerDisconnectEvent e) {
		
		List<BungeePlayer> bp = this.pl.getPlayerList();
		BungeePlayer player = new BungeePlayer();
		for(BungeePlayer b : bp) {
			
			if(b.getUniqueId().equals(e.getPlayer().getUniqueId())) {
				player = b;
				break;
			}
		}
		if(player != null) {
			
			this.pl.dbLastCome(player);
		}
	}
}

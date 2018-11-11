package fr.pmk_bungee.listener;

import java.sql.Timestamp;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.utils.PlayerSituation;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginEvent implements Listener {

	@EventHandler
	public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {

		String playername = e.getConnection().getName();
		PlayerSituation situation = new PlayerSituation(playername);
		if(situation != null) {
			if(situation.getPlayer().getUuid() != null) {
				
				System.out.println("Player Not Exist in DB");
				Timestamp now = new Timestamp(System.currentTimeMillis());
				Main.getMySQL().update("INSERT INTO `MinecraftPlayer`(`username`, `uuid`, `ip`, `firstCome`) VALUES ('"+e.getConnection().getName()+"','"+e.getConnection().getUniqueId()+"','"+e.getConnection().getAddress().getAddress()+"','"+now+"')");
			
			} else {
				System.out.println("Already Exist ");
			}
			if(PlayerSituation.isBanned(playername)){
				
				e.setCancelled(true);
				e.setCancelReason(new TextComponent(PlayerSituation.getBanMessage(playername)));
			}
			
			//TODO test ban
		
			//TODO kickBan
		}
	}
}

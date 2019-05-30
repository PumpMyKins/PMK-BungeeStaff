package fr.pmk_bungee.listener;

import java.util.Date;
import java.util.List;

import fr.pmk_bungee.objects.Ban;
import fr.pmk_bungee.objects.BungeePlayer;
import fr.pmk_bungee.objects.PlayersLog;
import fr.pmk_bungee.utils.Converter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class LoginEvent implements Listener {

	private PlayersLog pl;
	
	public LoginEvent(PlayersLog plg) {
	
		this.pl = plg;
	}

	@EventHandler
	public void onLogin(net.md_5.bungee.api.event.LoginEvent e) {

		List<BungeePlayer> bp = this.pl.getPlayerList();
		BungeePlayer player = new BungeePlayer();
		boolean exist = false;
		for(BungeePlayer b : bp) {
			
			if(b.getUniqueId().equals(e.getConnection().getUniqueId())) {
				player = b;
				exist = true;
			}
		}
		if(!exist) {
			
			player.setFirstCome(new Date());
			player.setIp(e.getConnection().getAddress().getHostName());
			player.setLastConnection(new Date());
			player.setUsername(e.getConnection().getName());
			player.setUuid(e.getConnection().getUniqueId());
			
			this.pl.addPlayer(player);
		}
		if(this.pl.isBan(player)) {
			
			Ban b = this.pl.getPlayerCurrentBan(player);
			
			BaseComponent bc1 = new TextComponent("[BANNI] Vous êtes banni des serveurs PumpMyKins [BANNI] \n");
			bc1.setColor(ChatColor.RED);
			TextComponent bc2 = new TextComponent("Raison :"+b.getBanReason() +"\n");
			bc2.setColor(ChatColor.GOLD);
			String timeleft = Converter.milliToDayHourMinuteSecond((b.getBanAt().getTime()+b.getBanDuration())-(new Date().getTime()));
			TextComponent bc3 = new TextComponent("Temps restant :"+timeleft);
			bc3.setColor(ChatColor.DARK_GREEN);
			e.setCancelReason(bc1, bc2, bc3);
			e.setCancelled(true);
		}
	}
}

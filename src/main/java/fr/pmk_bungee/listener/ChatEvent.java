package fr.pmk_bungee.listener;

import java.util.Date;

import fr.pmk_bungee.objects.BungeePlayer;
import fr.pmk_bungee.objects.Mute;
import fr.pmk_bungee.objects.PlayersLog;
import fr.pmk_bungee.utils.Converter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatEvent implements Listener {

	private PlayersLog pl;

	public ChatEvent(PlayersLog plg) {

		this.pl = plg;
	}

	@EventHandler
	public void onChar(net.md_5.bungee.api.event.ChatEvent e) {

		ProxiedPlayer p = (ProxiedPlayer) e.getSender();
		BungeePlayer bp = this.pl.getPlayer(p.getUniqueId());

		if(this.pl.isMute(bp)) {

			Mute m = this.pl.getPlayerCurrentMute(bp);
			if(e.getMessage().startsWith("/")) {
				return;
			}
			long current = new Date().getTime();
			long timeleft = m.getMuteAt().getTime()+m.getMuteDuration() - current;
			TextComponent bc1 = new TextComponent("/*/*/*/ MUTE /*/*/*/");
			bc1.setColor(ChatColor.DARK_RED);
			bc1.setBold(true);
			TextComponent bc2 = new TextComponent("Raison :"+m.getMuteReason());
			bc2.setColor(ChatColor.BLUE);
			TextComponent bc3 = new TextComponent("Temps restant : "+Converter.milliToDayHourMinuteSecond(timeleft));
			bc3.setColor(ChatColor.BLUE);
			e.setCancelled(true); 
			TextComponent bc4 = new TextComponent("Muté par :"+(this.pl.getPlayer(m.getMuteBy())).getUsername());
			bc4.setColor(ChatColor.BLUE);
			
			p.sendMessage(bc1);
			p.sendMessage(bc2);
			p.sendMessage(bc3);
			p.sendMessage(bc4);
		}
	}
}


package fr.pumpmybstaff.listener;

import java.util.Date;

import fr.pumpmybstaff.objects.BungeePlayer;
import fr.pumpmybstaff.objects.Mute;
import fr.pumpmybstaff.objects.PlayersLog;
import fr.pumpmybstaff.utils.Converter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class ChatEvent implements Listener {

	private PlayersLog pl;

	public ChatEvent(PlayersLog plg) {

		this.pl = plg;
	}

	@EventHandler(priority = EventPriority.LOWEST)
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
			TextComponent bc2 = new TextComponent("Raison : ");
			bc2.setColor(ChatColor.BLUE);
			TextComponent bc2bis = new TextComponent(m.getMuteReason());
			bc2bis.setColor(ChatColor.GOLD);
			bc2.addExtra(bc2bis);
			TextComponent bc3 = new TextComponent("Temps restant : ");
			bc3.setColor(ChatColor.BLUE);
			TextComponent bc3bis = new TextComponent(Converter.milliToDayHourMinuteSecond(timeleft));
			bc3bis.setColor(ChatColor.GOLD);
			bc3.addExtra(bc3bis);
			TextComponent bc4 = new TextComponent("Mut� par : ");
			bc4.setColor(ChatColor.BLUE);
			TextComponent bc4bis = new TextComponent((this.pl.getPlayer(m.getMuteBy())).getUsername());
			bc4bis.setColor(ChatColor.GOLD);
			bc4.addExtra(bc4bis);
			
			p.sendMessage(bc1);
			p.sendMessage(bc2);
			p.sendMessage(bc3);
			p.sendMessage(bc4);
			p.sendMessage(bc1);
			
			e.setCancelled(true);
		}
	}
}


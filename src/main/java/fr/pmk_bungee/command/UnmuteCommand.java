package fr.pmk_bungee.command;

import fr.pmk_bungee.objects.BungeePlayer;
import fr.pmk_bungee.objects.PlayersLog;
import fr.pmk_bungee.utils.TypicalMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class UnmuteCommand extends Command {

	private PlayersLog pl;
	
	public UnmuteCommand(String name, PlayersLog plg) {
		super(name);
		this.pl = plg;
	}

	@Override
	public void execute(final CommandSender sender, final String[] args) {
		
		if(sender.hasPermission("rank.staff.modo") || sender.hasPermission("rank.staff.admin") || sender.hasPermission("rank.staff.responsable")) {
			
			if(args.length > 0) {
				
				BungeePlayer bp = this.pl.getPlayer(args[0]);
				if(bp != null) {
					
					if(this.pl.isMute(bp)) {
						
						this.pl.unMute(bp);
						
						TextComponent bc1 = new TextComponent("Le joueur ");
						bc1.setColor(ChatColor.DARK_GREEN);
						TextComponent bc2 = new TextComponent(bp.getUsername());
						bc2.setColor(ChatColor.GOLD);
						TextComponent bc3 = new TextComponent(" n'est plus mute !");
						bc3.setColor(ChatColor.DARK_GREEN);
						
						bc1.addExtra(bc2);
						bc1.addExtra(bc3);
						
						sender.sendMessage(bc1);
						
					} else {
						
						TextComponent bc1 = new TextComponent("Le joueur ");
						bc1.setColor(ChatColor.DARK_RED);
						TextComponent bc2 = new TextComponent(bp.getUsername());
						bc2.setColor(ChatColor.GOLD);
						TextComponent bc3 = new TextComponent(" n'est pas mute !");
						bc3.setColor(ChatColor.DARK_RED);
						
						bc1.addExtra(bc2);
						bc1.addExtra(bc3);
						
						sender.sendMessage(bc1);
					}
				} else {
					
					sender.sendMessage(TypicalMessage.playerUnknown(args[0]));
				}
			} else {
				
				TextComponent bc1 = new TextComponent("Commande : ");
				bc1.setColor(ChatColor.DARK_RED);
				TextComponent bc2 = new TextComponent("/unmute <pseudo>");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour unmute quelqu'un !");
				bc3.setColor(ChatColor.DARK_RED);
				
				bc1.addExtra(bc2);
				bc1.addExtra(bc3);
				
				sender.sendMessage(bc1);
			}
		} else {
			
			sender.sendMessage(TypicalMessage.noPermission("/unmute"));
		}
	}

}

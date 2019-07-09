package fr.pumpmybstaff.command;

import fr.pumpmybstaff.objects.BungeePlayer;
import fr.pumpmybstaff.objects.PlayersLog;
import fr.pumpmybstaff.utils.TypicalMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command {

	private PlayersLog pl;
	
	public KickCommand(String name, PlayersLog plg) {
		super(name);
		this.pl = plg;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("rank.staff.modo") || sender.hasPermission("rank.staff.admin") || sender.hasPermission("rank.staff.responsable")) {

			if(args.length > 1) {

				ProxiedPlayer pp = (ProxiedPlayer) sender;
				
				String playername = args[0];
				String reason = "";
				for(int i = 1; i <= args.length - 1; i++) {

					reason = reason + args[i] + " ";
				}
				
				TextComponent kickreason = new TextComponent("Vous avez �t� kick du serveur ! \n Raison : ");
				kickreason.setColor(ChatColor.RED);
				TextComponent kickreasonbis = new TextComponent(reason);
				kickreasonbis.setColor(ChatColor.GOLD);
				
				
				BungeePlayer kicked = this.pl.getPlayer(playername);
				BungeePlayer kicker = this.pl.getPlayer(pp.getUniqueId());
				ProxiedPlayer ki = ProxyServer.getInstance().getPlayer(playername);
				
				if(kicked != null && ki != null) {
					
					this.pl.addKick(kicked, reason, kicker);
					ki.disconnect(kickreason, kickreasonbis);
					
					TextComponent bc1 = new TextComponent("Vous avez kick :");
					bc1.setColor(ChatColor.DARK_GREEN);
					TextComponent bc2 = new TextComponent(playername);
					bc2.setColor(ChatColor.GOLD);
					TextComponent bc3 = new TextComponent(" avec succ�s !");
					bc3.setColor(ChatColor.DARK_GREEN);
					
					bc1.addExtra(bc2);
					bc1.addExtra(bc3);
					
					sender.sendMessage(bc1);
					
				} else {
					
					sender.sendMessage(TypicalMessage.playerUnknown(args[0]));
				}

			} else { 
				
				TextComponent bc1 = new TextComponent("Commande : ");
				bc1.setColor(ChatColor.DARK_RED);
				TextComponent bc2 = new TextComponent("/kick <pseudo> <raison>");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour kick un joueur du serveur !");
				bc3.setColor(ChatColor.DARK_RED);
				
				bc1.addExtra(bc2);
				bc1.addExtra(bc3);
				
				sender.sendMessage(bc1);
			}

		} else { 
			
			sender.sendMessage(TypicalMessage.noPermission("/kick"));
		}
	}
}

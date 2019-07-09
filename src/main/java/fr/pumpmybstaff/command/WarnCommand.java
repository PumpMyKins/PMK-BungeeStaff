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

public class WarnCommand extends Command {

	private PlayersLog pl;
	
	public WarnCommand(String name, PlayersLog plg) {
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
				
				TextComponent kickreason = new TextComponent("Vous avez �t� warn ! Pour : ");
				kickreason.setColor(ChatColor.RED);
				TextComponent kickreasonbis = new TextComponent(reason);
				kickreasonbis.setColor(ChatColor.GOLD);
				kickreason.addExtra(kickreasonbis);
				
				BungeePlayer warned = this.pl.getPlayer(playername);
				BungeePlayer warner = this.pl.getPlayer(pp.getUniqueId());
				
				ProxiedPlayer ki = ProxyServer.getInstance().getPlayer(playername);
				
				if(warned != null && ki != null) {
					
					this.pl.addWarn(warned, reason, warner);
					
					TextComponent bc1 = new TextComponent("Vous avez warn :");
					bc1.setColor(ChatColor.DARK_GREEN);
					TextComponent bc2 = new TextComponent(playername);
					bc2.setColor(ChatColor.GOLD);
					TextComponent bc3 = new TextComponent(" avec succ�s !");
					bc3.setColor(ChatColor.DARK_GREEN);
					
					bc1.addExtra(bc2);
					bc1.addExtra(bc3);
					
					sender.sendMessage(bc1);
					ki.sendMessage(kickreason);
					
				} else {
					
					sender.sendMessage(TypicalMessage.playerUnknown(args[0]));
				}

			} else { 
				
				TextComponent bc1 = new TextComponent("Commande : ");
				bc1.setColor(ChatColor.DARK_RED);
				TextComponent bc2 = new TextComponent("/warn <pseudo> <raison>");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour warn un joueur !");
				bc3.setColor(ChatColor.DARK_RED);
				
				bc1.addExtra(bc2);
				bc1.addExtra(bc3);
				
				sender.sendMessage(bc1);
			}

		} else { 
			
			sender.sendMessage(TypicalMessage.noPermission("/warn"));
		}
	}
}

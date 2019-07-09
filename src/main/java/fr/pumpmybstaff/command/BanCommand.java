package fr.pumpmybstaff.command;

import fr.pumpmybstaff.MainBungeeStaff;
import fr.pumpmybstaff.objects.BungeePlayer;
import fr.pumpmybstaff.objects.PlayersLog;
import fr.pumpmybstaff.utils.TypicalMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class BanCommand extends Command {

	private PlayersLog pl;

	public BanCommand(String name, PlayersLog plg) {
		super(name);
		this.pl = plg;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		// TODO Auto-generated method stub

		if(sender.hasPermission("rank.staff.modo") || sender.hasPermission("rank.staff.admin") || sender.hasPermission("rank.staff.responsable")) {
			if(args.length >= 4) {

				String playerName = args[0];
				String reason = "";
				for(int i = 3; i <= args.length - 1; i++) {

					reason = reason + args[i] + " ";

				}

				ProxiedPlayer pp = (ProxiedPlayer) sender;
				MainBungeeStaff.getConfigManager().save();
				BungeePlayer bp = this.pl.getPlayer(playerName);
				BungeePlayer banner = this.pl.getPlayer(pp.getUniqueId());
				if(bp != null) {

					if(!pl.isBan(bp)) {
						
						long time = Integer.parseInt(args[1]);
						MainBungeeStaff.TimeUnit unit = MainBungeeStaff.TimeUnit.getByString(args[2]);
						if(unit != null) {
							System.out.println(unit);
							time *= unit.getSeconds();
							System.out.println(time);
							this.pl.addBan(bp, reason, banner,(int) time);
							
							ProxiedPlayer prp = ProxyServer.getInstance().getPlayer(bp.getUniqueId());
							if(prp != null) {
								
								
							}
						}

					} else {
						TextComponent bc1 = new TextComponent("Le joueur ");
						bc1.setColor(ChatColor.DARK_RED);
						TextComponent bc2 = new TextComponent(bp.getUsername());
						bc2.setColor(ChatColor.GOLD);
						TextComponent bc3 = new TextComponent(" est d�j� banni !");
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
				TextComponent bc2 = new TextComponent("/ban <pseudo> <temps> <unite de temps> <raison>");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour bannir quelqu'un !");
				bc3.setColor(ChatColor.DARK_RED);
				
				bc1.addExtra(bc2);
				bc1.addExtra(bc3);
				
				sender.sendMessage(bc1);
			}
		} else {
			sender.sendMessage(TypicalMessage.noPermission("/ban"));
		};
	} 
}

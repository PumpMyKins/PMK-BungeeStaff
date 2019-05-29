package fr.pmk_bungee.command;

import fr.pmk_bungee.MainBungeeStaff;
import fr.pmk_bungee.objects.BungeePlayer;
import fr.pmk_bungee.objects.PlayersLog;
import fr.pmk_bungee.utils.TypicalMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class MuteCommand extends Command {

	private PlayersLog pl;
	
	public MuteCommand(String name, PlayersLog pl) {
		super(name);
		this.pl = pl;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		
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
				BungeePlayer mutter = this.pl.getPlayer(pp.getUniqueId());
				if(bp != null) {

					if(!pl.isMute(bp)) {
						
						long time = Integer.parseInt(args[1]);
						MainBungeeStaff.TimeUnit unit = MainBungeeStaff.TimeUnit.getByString(args[2]);
						if(unit != null) {

							time *= unit.getSeconds();
							this.pl.addMute(bp, reason, mutter,(int) time);
						}
					} else {
						TextComponent bc1 = new TextComponent("Le joueur ");
						bc1.setColor(ChatColor.DARK_RED);
						TextComponent bc2 = new TextComponent(bp.getUsername());
						bc2.setColor(ChatColor.GOLD);
						TextComponent bc3 = new TextComponent(" est déjà mute !");
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
				TextComponent bc2 = new TextComponent("/mute <pseudo> <temps> <unite de temps> <raison>");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour mute quelqu'un !");
				bc3.setColor(ChatColor.DARK_RED);
				
				bc1.addExtra(bc2);
				bc1.addExtra(bc3);
				
				sender.sendMessage(bc1);
			}
		} else {
			sender.sendMessage(TypicalMessage.noPermission("/mute"));
		};
	} 
}

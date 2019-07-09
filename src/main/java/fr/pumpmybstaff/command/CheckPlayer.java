package fr.pumpmybstaff.command;

import fr.pumpmybstaff.objects.Ban;
import fr.pumpmybstaff.objects.BungeePlayer;
import fr.pumpmybstaff.objects.Mute;
import fr.pumpmybstaff.objects.PlayersLog;
import fr.pumpmybstaff.utils.Converter;
import fr.pumpmybstaff.utils.TypicalMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class CheckPlayer extends Command {

	private PlayersLog pl;

	public CheckPlayer(String string, PlayersLog plg) {
		super(string);
		this.pl = plg;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("rank.staff.modo") || sender.hasPermission("rank.staff.admin") || sender.hasPermission("rank.staff.responsable")) {

			if(args.length > 0) {
				
				BungeePlayer bp = this.pl.getPlayer(args[0]);
				
				if(bp != null) {
					
					Mute m = this.pl.getPlayerCurrentMute(bp);
					Ban b = this.pl.getPlayerCurrentBan(bp);

					TextComponent bc1 = new TextComponent("Sanction actuelles du joueur : ");
					bc1.setColor(ChatColor.BLUE);
					TextComponent bc2 = new TextComponent(bp.getUsername());
					bc2.setColor(ChatColor.GOLD);
					bc1.addExtra(bc2);
					
					sender.sendMessage(bc1);
					
					if(b != null) {
						
						TextComponent bc3 = new TextComponent("Ban actuel : ");
						bc3.setColor(ChatColor.DARK_RED);
						TextComponent bc4 = new TextComponent("Raison :");
						bc4.setColor(ChatColor.RED);
						TextComponent bc4bis = new TextComponent(b.getBanReason());
						bc4bis.setColor(ChatColor.GOLD);
						bc4.addExtra(bc4bis);
						TextComponent bc5 = new TextComponent("Banni le :");
						bc5.setColor(ChatColor.RED);
						TextComponent bc5bis = new TextComponent(b.getBanAt().toString());
						bc5bis.setColor(ChatColor.GOLD);
						bc5.addExtra(bc5bis);
						TextComponent bc6 = new TextComponent("Dur�e du ban :");
						bc6.setColor(ChatColor.RED);
						TextComponent bc6bis = new TextComponent(Converter.milliToDayHourMinuteSecond(b.getBanDuration()));
						bc6bis.setColor(ChatColor.GOLD);
						bc6.addExtra(bc6bis);
						TextComponent bc7 = new TextComponent("/* Fin ban */");
						bc7.setColor(ChatColor.DARK_RED);
						
						sender.sendMessage(bc3);
						sender.sendMessage(bc4);
						sender.sendMessage(bc5);
						sender.sendMessage(bc6);
						sender.sendMessage(bc7);
					} else {
						
						TextComponent bc8 = new TextComponent("Le joueur n'est pas banni !");
						bc8.setColor(ChatColor.DARK_GREEN);
						
						sender.sendMessage(bc8);
					}
					if(m != null) {
						
						TextComponent bc3 = new TextComponent("Mute actuel : ");
						bc3.setColor(ChatColor.DARK_RED);
						TextComponent bc4 = new TextComponent("Raison :");
						bc4.setColor(ChatColor.RED);
						TextComponent bc4bis = new TextComponent(m.getMuteReason());
						bc4bis.setColor(ChatColor.GOLD);
						bc4.addExtra(bc4bis);
						TextComponent bc5 = new TextComponent("Mute le :");
						bc5.setColor(ChatColor.RED);
						TextComponent bc5bis = new TextComponent(b.getBanAt().toString());
						bc5bis.setColor(ChatColor.GOLD);
						bc5.addExtra(bc5bis);
						TextComponent bc6 = new TextComponent("Dur�e du mute :");
						bc6.setColor(ChatColor.RED);
						TextComponent bc6bis = new TextComponent(Converter.milliToDayHourMinuteSecond(b.getBanDuration()));
						bc6bis.setColor(ChatColor.GOLD);
						bc6.addExtra(bc6bis);
						TextComponent bc7 = new TextComponent("/* Fin mute */");
						bc7.setColor(ChatColor.DARK_RED);
						
						sender.sendMessage(bc3);
						sender.sendMessage(bc4);
						sender.sendMessage(bc5);
						sender.sendMessage(bc6);
						sender.sendMessage(bc7);
					} else {
						
						TextComponent bc8 = new TextComponent("Le joueur n'est pas mute !");
						bc8.setColor(ChatColor.DARK_GREEN);
						
						sender.sendMessage(bc8);
					}
				} else {
					
					sender.sendMessage(TypicalMessage.playerUnknown(args[0]));
				}
			} else {
				
				TextComponent bc1 = new TextComponent("Commande : ");
				bc1.setColor(ChatColor.DARK_RED);
				TextComponent bc2 = new TextComponent("/check <pseudo> ");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour connaitre les sanctions actuelles d'un joueur !");
				bc3.setColor(ChatColor.DARK_RED);
				
				bc1.addExtra(bc2);
				bc1.addExtra(bc3);
				
				sender.sendMessage(bc1);
			}
		} else {
			
			sender.sendMessage(TypicalMessage.noPermission("/check"));
		}
	}
}
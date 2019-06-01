package fr.pmk_bungee.command;

import java.util.List;

import fr.pmk_bungee.objects.Ban;
import fr.pmk_bungee.objects.BungeePlayer;
import fr.pmk_bungee.objects.Kick;
import fr.pmk_bungee.objects.Mute;
import fr.pmk_bungee.objects.PlayersLog;
import fr.pmk_bungee.objects.Warn;
import fr.pmk_bungee.utils.Converter;
import fr.pmk_bungee.utils.TypicalMessage;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class InformationCommand extends Command {

	private PlayersLog pl;

	public InformationCommand(String name, PlayersLog plg) {
		super(name);
		this.pl = plg;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {

		if(sender.hasPermission("rank.staff.modo") || sender.hasPermission("rank.staff.admin") || sender.hasPermission("rank.staff.responsable")) {
			// /info <pseudo> <type : ALL / BAN / MUTE / KICK / WARN>
			if(args.length > 1) {

				BungeePlayer bp = this.pl.getPlayer(args[0]);

				if(bp != null) {

					List<Ban> lb = this.pl.getPlayerBans(bp);
					List<Mute> lm = this.pl.getPlayerMute(bp);
					List<Warn> lw = this.pl.getPlayerWarn(bp);
					List<Kick> lk = this.pl.getPlayerKick(bp);

					switch (args[1]) {
					case "ban":
						showBan(lb, sender);
						break;
					case "mute":
						showMute(lm, sender);
						break;
					case "kick":
						showKick(lk, sender);
						break;
					case "warn":
						showWarn(lw, sender);
						break;
					case "player":
						showPlayer(bp, sender);
					default:
						showPlayer(bp, sender);
						showBan(lb, sender);
						showMute(lm, sender);
						showKick(lk, sender);
						showWarn(lw, sender);
						break;
					}



				} else {

					sender.sendMessage(TypicalMessage.playerUnknown(args[0]));
				}
			} else {

				TextComponent bc1 = new TextComponent("Commande : ");
				bc1.setColor(ChatColor.DARK_RED);
				TextComponent bc2 = new TextComponent("/info <pseudo> <type : ALL / BAN / MUTE / KICK / WARN>");
				bc2.setColor(ChatColor.GOLD);
				TextComponent bc3 = new TextComponent(" | Pour connaitre les sanctions actuelles d'un joueur !");
				bc3.setColor(ChatColor.DARK_RED);

				bc1.addExtra(bc2);
				bc1.addExtra(bc3);

				sender.sendMessage(bc1);
			}
		}
	}

	public void showBan(List<Ban> lb, CommandSender pp) {

		if(!lb.isEmpty()) {

			TextComponent bc1 = new TextComponent("Liste des ban(s) :");
			bc1.setColor(ChatColor.DARK_RED);

			pp.sendMessage(bc1);

			int i = 1;
			for(Ban b : lb) {

				TextComponent bc2 = new TextComponent("--[[[[Ban numero : "+i+" ]]]]--");
				bc2.setColor(ChatColor.RED);
				i++;
				TextComponent bc3 = new TextComponent("Banni par :");
				bc3.setColor(ChatColor.BLUE);
				TextComponent bc3bis = new TextComponent(this.pl.getPlayer(b.getBanBy()).getUsername());
				bc3bis.setColor(ChatColor.GOLD);
				bc3.addExtra(bc3bis);
				TextComponent bc4 = new TextComponent("Durée du ban :");
				bc4.setColor(ChatColor.BLUE);
				TextComponent bc4bis = new TextComponent(Converter.milliToDayHourMinuteSecond(b.getBanDuration()));
				bc4bis.setColor(ChatColor.GOLD);
				bc4.addExtra(bc4bis);
				TextComponent bc5 = new TextComponent("Date du ban :");
				bc5.setColor(ChatColor.BLUE);
				TextComponent bc5bis = new TextComponent(b.getBanAt().toString());
				bc5bis.setColor(ChatColor.GOLD);
				bc5.addExtra(bc5bis);
				TextComponent bc6 = new TextComponent("Raison du Ban :");
				bc6.setColor(ChatColor.BLUE);
				TextComponent bc6bis = new TextComponent(b.getBanReason());
				bc6bis.setColor(ChatColor.GOLD);
				bc6.addExtra(bc6bis);
				
				pp.sendMessage(bc2);
				pp.sendMessage(bc3);
				pp.sendMessage(bc4);
				pp.sendMessage(bc5);
				pp.sendMessage(bc6);
			}
		} else {

			TextComponent bc2 = new TextComponent("Le joueur n'a jamais été banni");
			bc2.setColor(ChatColor.DARK_GREEN);

			pp.sendMessage(bc2);
		}
	}

	public void showMute(List<Mute> lm, CommandSender pp) {

		if(!lm.isEmpty()) {
			
			TextComponent bc1 = new TextComponent("Liste des mute(s) :");
			bc1.setColor(ChatColor.DARK_RED);

			pp.sendMessage(bc1);

			int i = 1;
			for(Mute m : lm) {

				TextComponent bc2 = new TextComponent("--[[[[Mute numero : "+i+" ]]]]--");
				bc2.setColor(ChatColor.RED);
				i++;
				TextComponent bc3 = new TextComponent("Mute par :");
				bc3.setColor(ChatColor.BLUE);
				TextComponent bc3bis = new TextComponent(this.pl.getPlayer(m.getMuteBy()).getUsername());
				bc3bis.setColor(ChatColor.GOLD);
				bc3.addExtra(bc3bis);
				TextComponent bc4 = new TextComponent("Durée du Mute :");
				bc4.setColor(ChatColor.BLUE);
				TextComponent bc4bis = new TextComponent(Converter.milliToDayHourMinuteSecond(m.getMuteDuration()));
				bc4bis.setColor(ChatColor.GOLD);
				bc4.addExtra(bc4bis);
				TextComponent bc5 = new TextComponent("Date du Mute :");
				bc5.setColor(ChatColor.BLUE);
				TextComponent bc5bis = new TextComponent(m.getMuteAt().toString());
				bc5bis.setColor(ChatColor.GOLD);
				bc5.addExtra(bc5bis);
				TextComponent bc6 = new TextComponent("Raison du Mute :");
				bc6.setColor(ChatColor.BLUE);
				TextComponent bc6bis = new TextComponent(m.getMuteReason());
				bc6bis.setColor(ChatColor.GOLD);
				bc6.addExtra(bc6bis);

				pp.sendMessage(bc2);
				pp.sendMessage(bc3);
				pp.sendMessage(bc4);
				pp.sendMessage(bc5);
				pp.sendMessage(bc6);
			}
		} else {

			TextComponent bc2 = new TextComponent("Le joueur n'a jamais été mute");
			bc2.setColor(ChatColor.DARK_GREEN);

			pp.sendMessage(bc2);
		}
	}

	public void showWarn(List<Warn> lw, CommandSender pp) {

		if(!lw.isEmpty()) {
			
			TextComponent bc1 = new TextComponent("Liste des warn(s) :");
			bc1.setColor(ChatColor.DARK_RED);

			pp.sendMessage(bc1);

			int i = 1;
			for(Warn w : lw) {

				TextComponent bc2 = new TextComponent("--[[[[Warn numero : "+i+" ]]]]--");
				bc2.setColor(ChatColor.RED);
				i++;
				TextComponent bc3 = new TextComponent("Warn par :");
				bc3.setColor(ChatColor.BLUE);
				TextComponent bc3bis = new TextComponent(this.pl.getPlayer(w.getWarnBy()).getUsername());
				bc3bis.setColor(ChatColor.GOLD);
				bc3.addExtra(bc3bis);
				TextComponent bc4 = new TextComponent("Raison du Warn :");
				bc4.setColor(ChatColor.BLUE);
				TextComponent bc4bis = new TextComponent(w.getWarnReason());
				bc4bis.setColor(ChatColor.GOLD);
				bc4.addExtra(bc4bis);
				TextComponent bc5 = new TextComponent("Date du Warn :");
				bc5.setColor(ChatColor.BLUE);
				TextComponent bc5bis = new TextComponent(w.getWarnDate().toString());
				bc5bis.setColor(ChatColor.GOLD);
				bc5.addExtra(bc5bis);

				pp.sendMessage(bc2);
				pp.sendMessage(bc3);
				pp.sendMessage(bc4);
				pp.sendMessage(bc5);
			}
		} else {

			TextComponent bc2 = new TextComponent("Le joueur n'a jamais été warn");
			bc2.setColor(ChatColor.DARK_GREEN);

			pp.sendMessage(bc2);
		}
	}

	public void showKick(List<Kick> lk, CommandSender pp) {

		if(!lk.isEmpty()) {
			
			TextComponent bc1 = new TextComponent("Liste des warn(s) :");
			bc1.setColor(ChatColor.DARK_RED);

			pp.sendMessage(bc1);

			int i = 1;
			for(Kick k : lk) {

				TextComponent bc2 = new TextComponent("--[[[[ Kick numero : "+i+" ]]]]--");
				bc2.setColor(ChatColor.RED);
				i++;
				TextComponent bc3 = new TextComponent("Kick par :");
				bc3.setColor(ChatColor.BLUE);
				TextComponent bc3bis = new TextComponent(this.pl.getPlayer(k.getKickBy()).getUsername());
				bc3bis.setColor(ChatColor.GOLD);
				bc3.addExtra(bc3bis);
				TextComponent bc4 = new TextComponent("Raison du Kick :");
				bc4.setColor(ChatColor.BLUE);
				TextComponent bc4bis = new TextComponent(k.getKickReason());
				bc4bis.setColor(ChatColor.GOLD);
				bc4.addExtra(bc4bis);
				TextComponent bc5 = new TextComponent("Date du Kick :");
				bc5.setColor(ChatColor.BLUE);
				TextComponent bc5bis = new TextComponent(k.getKickDate().toString());
				bc5bis.setColor(ChatColor.GOLD);
				bc5.addExtra(bc5bis);

				pp.sendMessage(bc2);
				pp.sendMessage(bc3);
				pp.sendMessage(bc4);
				pp.sendMessage(bc5);
			}
		} else {

			TextComponent bc2 = new TextComponent("Le joueur n'a jamais été kick");
			bc2.setColor(ChatColor.DARK_GREEN);

			pp.sendMessage(bc2);
		}
	}

	public void showPlayer(BungeePlayer bp, CommandSender pp) {

		TextComponent bc1 = new TextComponent("Info sur le joueur :");
		bc1.setColor(ChatColor.DARK_RED);
		TextComponent bc2 = new TextComponent("Pseudo :");
		bc2.setColor(ChatColor.BLUE);
		TextComponent bc2bis = new TextComponent(bp.getUsername());
		bc2bis.setColor(ChatColor.GOLD);
		bc2.addExtra(bc2bis);
		TextComponent bc3 = new TextComponent("Ip :");
		bc3.setColor(ChatColor.BLUE);
		TextComponent bc3bis = new TextComponent(bp.getIp());
		bc3bis.setColor(ChatColor.GOLD);
		bc3.addExtra(bc3bis);
		TextComponent bc4 = new TextComponent("Uuid :");
		bc4.setColor(ChatColor.BLUE);
		TextComponent bc4bis = new TextComponent(bp.getUniqueId().toString());
		bc4bis.setColor(ChatColor.GOLD);
		bc4.addExtra(bc4bis);
		TextComponent bc5 = new TextComponent("Première connection :");
		bc5.setColor(ChatColor.BLUE);
		TextComponent bc5bis = new TextComponent(bp.getFirstCome().toString());
		bc5bis.setColor(ChatColor.GOLD);
		bc5.addExtra(bc5bis);
		TextComponent bc6 = new TextComponent("Dernière connection :");
		bc6.setColor(ChatColor.BLUE);
		TextComponent bc6bis = new TextComponent(bp.getLastConnection().toString());
		bc6bis.setColor(ChatColor.GOLD);
		bc6.addExtra(bc6bis);
		
		pp.sendMessage(bc1);
		pp.sendMessage(bc2);
		pp.sendMessage(bc3);
		pp.sendMessage(bc4);
		pp.sendMessage(bc5);
		pp.sendMessage(bc6);
	}
}

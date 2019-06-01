package fr.pmk_bungee.utils;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;

public class TypicalMessage {

	public static TextComponent noPermission(String command) {
		TextComponent bc1 = new TextComponent("Vous n'avez pas la permission pour ");
		bc1.setColor(ChatColor.DARK_RED);
		TextComponent bc2 = new TextComponent(command);
		bc2.setColor(ChatColor.GOLD);
		TextComponent bc3 = new TextComponent(" !");
		bc3.setColor(ChatColor.DARK_RED);

		bc1.addExtra(bc2);
		bc1.addExtra(bc3);

		return bc1;
	}
	
	public static TextComponent playerUnknown(String playername) {
		TextComponent bc1 = new TextComponent("Le joueur ");
		bc1.setColor(ChatColor.DARK_RED);
		TextComponent bc2 = new TextComponent(playername);
		bc2.setColor(ChatColor.GOLD);
		TextComponent bc3 = new TextComponent(" n'existe pas !");
		bc3.setColor(ChatColor.DARK_RED);

		bc1.addExtra(bc2);
		bc1.addExtra(bc3);

		return bc1;
	}
}

package fr.pmk_bungee.utils;

import java.util.ArrayList;
import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Message;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageSender {
		
	public static void send(Message message) {
		
		List<String> msgs= new ArrayList<String>();
		if(message.getParameter().isEmpty()) {
			
			if(message.isPrefix()) {
				msgs.add(Main.PREFIX);
				msgs.add(Main.getConfigManager().getString(message.getMessageTitle()));
				
			} else {
				
				msgs.add(Main.getConfigManager().getString(message.getMessageTitle()));
			}
			
		} else {
			
			if(message.isPrefix()) {
				msgs.add(Main.PREFIX);
				msgs.addAll(Main.getConfigManager().getStringList(message.getMessageTitle(), message.getParameter()));
				
			} else {
				
				msgs.addAll(Main.getConfigManager().getStringList(message.getMessageTitle(), message.getParameter()));
			}	
		}
		for(String msg : msgs) {
			
			message.getSender().sendMessage(new TextComponent(msg));
		}
	}
}

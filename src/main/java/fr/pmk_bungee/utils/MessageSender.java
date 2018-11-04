package fr.pmk_bungee.utils;

import java.util.List;

import fr.pmk_bungee.Main;
import fr.pmk_bungee.object.Message;
import fr.pmk_bungee.object.Message.Parameter;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class MessageSender {
		
	public MessageSender(Message message) {
		
		List<String> msgs = null;
		ProxiedPlayer sender = (ProxiedPlayer) message.getSender();
		if(message.getParameter().isEmpty()) {
			
			if(message.isPrefix()) {
				
				msgs.add(Main.PREFIX);
				msgs.add(Main.getConfigManager().getString(message.getMessageTitle()));
				
			} else {
				
				msgs.add(Main.getConfigManager().getString(message.getMessageTitle()));
			}
			
		} else {

			List<Parameter> paramList = message.getParameter();
			
			if(message.isPrefix()) {
				
				msgs.add(Main.PREFIX);
				msgs.addAll(Main.getConfigManager().getStringList(message.getMessageTitle(), )); /*{
					for(Parameter paramD : message.getParameter()) {
						String title = paramD.getParamTitle();
						String content = paramD.getParamContent();
					}	
				}));
				*/
				
			}
		}
		
		
	}
}

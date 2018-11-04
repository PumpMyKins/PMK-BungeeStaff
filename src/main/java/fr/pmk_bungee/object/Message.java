package fr.pmk_bungee.object;

import net.md_5.bungee.api.CommandSender;
import java.util.List;

public class Message {

	public class Parameter {
		
		private String paramTitle;
		private String paramContent;
		
		public String getParamTitle() {
			return paramTitle;
		}
		public void setParamTitle(String paramTitle) {
			this.paramTitle = paramTitle;
		}
		public String getParamContent() {
			return paramContent;
		}
		public void setParamContent(String paramContent) {
			this.paramContent = paramContent;
		}
		
	}
	
	private CommandSender sender;
	private boolean prefix;
	private String messageTitle;
	private List<Parameter> parameter;
	
	public CommandSender getSender() {
		return sender;
	}
	public void setSender(CommandSender sender) {
		this.sender = sender;
	}
	public boolean isPrefix() {
		return prefix;
	}
	public void setPrefix(boolean prefix) {
		this.prefix = prefix;
	}
	public List<Parameter> getParameter() {
		return parameter;
	}
	public void setParameter(List<Parameter> parameter) {
		this.parameter = parameter;
	}
	public String getMessageTitle() {
		return messageTitle;
	}
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	public Message(CommandSender sender, boolean prefix, String messageTitle, List<Parameter> parameter) {
		
		this.setSender(sender);
		this.setPrefix(prefix);
		this.setMessageTitle(messageTitle);
		this.setParameter(parameter);
		
	}
	
	
	
}

package me.idiom.godfists.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageManager {
	
	public enum MessageType {
		
		INFO(ChatColor.YELLOW),
		GOOD(ChatColor.GREEN),
		BAD(ChatColor.RED);
		
		private ChatColor color;
		
		MessageType(ChatColor color) {
			this.color = color;
		}
		
		public ChatColor getColor() {
			return color;
		}
	}
	
	private MessageManager() {};
	
	private static MessageManager instance = new MessageManager();
	
	public static MessageManager getInstance() {
		return instance;
	}
	
	private String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "GodFists" + ChatColor.DARK_GRAY + "]" + ChatColor.RESET;
	public void msg(CommandSender sender, MessageType type, String... messages) {
		for (String msg : messages) {
			sender.sendMessage(prefix + type.getColor() + msg);
		}
	}

}


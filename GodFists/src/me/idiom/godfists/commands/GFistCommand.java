package me.idiom.godfists.commands;

import org.bukkit.command.CommandSender;

public abstract class GFistCommand {

	public abstract void onCommand(CommandSender s, String[] args);
	
	private String message, usage;
	private String[] aliases;
	
	public GFistCommand(String message, String usage, String... aliases) {
		this.message = message;
		this.usage = usage;
		this.aliases = aliases;
	}
	
	public final String getMessage() {
		return message;
	}
	
	public final String getUsage() {
		return usage;
	}
	
	public final String[] getAliases() {
		return aliases;
	}
}

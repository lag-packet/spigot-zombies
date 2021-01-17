package me.idiom.godfists.utils;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.idiom.godfists.commands.BeginGod;
import me.idiom.godfists.commands.GFistCommand;
import me.idiom.godfists.commands.RemoveOffering;
import me.idiom.godfists.commands.SetOffering;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class CommandManager implements CommandExecutor {
	
	private ArrayList<GFistCommand> cmds = new ArrayList<>();
	
	public void setup() {
		cmds.add(new BeginGod());
		cmds.add(new SetOffering());
		cmds.add(new RemoveOffering());
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
		CommandSender p =  sender;
		
		if (cmd.getName().equalsIgnoreCase("godfists")) {
			if (args.length == 0) {
				for (GFistCommand gc : cmds) {
					MessageManager.getInstance().msg(p, MessageType.BAD, "/gfs " + aliases(gc) + " " + gc.getUsage() + 
							" - " + gc.getMessage());
				}
				return true;
			}
			
			GFistCommand c = getCommand(args[0]);
			
			if (c == null) {
				MessageManager.getInstance().msg(p, MessageType.BAD, "That command doesn't exist.");
				return true;
			}

			ArrayList<String> a = new ArrayList<String>(Arrays.asList(args));
			a.remove(0);
			args = a.toArray(new String[a.size()]);
			
			c.onCommand(p, args);
			
			return true;
		}
		
		return true;
	}

	private String aliases(GFistCommand cmd) {
		String fin = "";
		
		for (String a : cmd.getAliases()) {
			fin += a + " | ";
		}
		
		return fin.substring(0, fin.lastIndexOf(" | "));
	}
	
	private GFistCommand getCommand(String name) {
		for (GFistCommand cmd : cmds) {
			for (String alias : cmd.getAliases()) if (name.equalsIgnoreCase(alias)) return cmd;
		}
		return null;
	}
}

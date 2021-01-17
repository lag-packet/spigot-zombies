package me.idiom.godfists.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.idiom.godfists.GodFists;
import me.idiom.godfists.GodFistsPlayer;
import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class BeginGod extends GFistCommand {

	public BeginGod() {
		super("God is ready for offerings.", "<timer amount>", "start", "s");
	}

	@Override
	public void onCommand(CommandSender s, String[] args) {
		
		//check for permissions
		
		if (args.length == 0) {
			MessageManager.getInstance().msg(s, MessageType.BAD, "not enought arguments, ex: /gfs start (amount of seconds)");
			return;
		}
		
		if (GodFists.godSettings.get("offerings") == null) {GodFists.godGame.setup();}
		
		GodFists.godGame.setTimer(Integer.parseInt(args[0]));
		GodFists.godGame.startGod();
		
		for (GodFistsPlayer p : GodFists.playerManager.players()) {
			Player player = p.bukkitPlayer();
			
			MessageManager.getInstance().msg(player, MessageType.INFO, "God ritual has started, a worthy holder of immense power will be chosen in " + GodFists.godGame.timer() + " seconds.");
		}
	}

}

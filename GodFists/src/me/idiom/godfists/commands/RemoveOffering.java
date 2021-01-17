package me.idiom.godfists.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import me.idiom.godfists.GodFists;
import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.Utils;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class RemoveOffering extends GFistCommand {

	public RemoveOffering() {
		super("Use this to remove offering from the list", "<item name>", "r", "remove");

	}

	@Override
	public void onCommand(CommandSender s, String[] args) {
		if (args.length != 1) {
			MessageManager.getInstance().msg(s, MessageType.BAD, "Must specify a item, ex: /gfs r diamond ");
			// GodFists.god.setup();
			// gfs.update or whatever it is that refreshes god offering list
			return;
		}

		if (GodFists.godSettings.contains("offerings")) {
			if (GodFists.godSettings.contains("offerings." + args[0])) {
				MessageManager.getInstance().msg(s, MessageType.BAD, "You got this, now removing");
				GodFists.godSettings.set("offerings." + args[0], null);
				GodFists.godGame.refreshItems();
				return;
			} else if (!GodFists.godSettings.contains("offerings." + args[0])) {
				MessageManager.getInstance().msg(s, MessageType.BAD, "You dont have this item in the offering list.");
			} else {
				ItemStack item = Utils.stringToItemStack(args[0]);
				if (item == null) {
					MessageManager.getInstance().msg(s, MessageType.BAD,
							"Must specify VALID item, ex: /gfs offering diamond");
					return;
				}
			}
		}
	}
}

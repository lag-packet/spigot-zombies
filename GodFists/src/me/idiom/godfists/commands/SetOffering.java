package me.idiom.godfists.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import me.idiom.godfists.GodFists;
import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.Utils;
import me.idiom.godfists.utils.MessageManager.MessageType;


public class SetOffering extends GFistCommand {

	public SetOffering() {
		super("Set the offerings with this command (set them one at a time)" , "<item name> <amount>", "offering", "o");
	}

	@Override
	public void onCommand(CommandSender s, String[] args) {
		
		if (args.length != 2) {
			MessageManager.getInstance().msg(s, MessageType.BAD, "Must specify item and amount, ex: /gfs offering diamond 1");
			System.out.println("Gods offerings consist of:"+GodFists.godGame.offeringReqList());
			return;
		}

		if (GodFists.godSettings.contains("offerings")) {
			if (GodFists.godSettings.contains("offerings." + args[0])) {
				MessageManager.getInstance().msg(s, MessageType.BAD, "You already have that item added, remove the item with the remove command and add the item again with the new value!");
				return;
			} else {
				ItemStack item = Utils.stringToItemStack(args[0], Integer.parseInt(args[1]));
				if (item == null) {
					MessageManager.getInstance().msg(s, MessageType.BAD, "Must specify VALID item and amount, ex: /gfs offering diamond 1");
					return;
				}
				GodFists.godSettings.set("offerings." + args[0], args[1]);
				GodFists.godGame.refreshItems();	
			}
		}
		
		System.out.println("Gods offerings consist of:"+ GodFists.godGame.offeringReqList());
	}
}

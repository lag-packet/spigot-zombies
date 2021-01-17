package me.idiom.godfists.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class Utils {
	
	//TODO ADD VARS TO YML CONFIG
	public static int gfistTimer = 10;
	public static int gflyTimer = 3;
	public static int gteleTimer = 15;
	public static int godEndTimer = 1115;
	
	public static ItemStack stringToItemStack(String itemString, int amount) {
		itemString=itemString.replace("_", " ");
		for (Material m : Material.values()) {
			if (itemString.equalsIgnoreCase(m.toString().replace("_", " "))) {
				System.out.println("found a match");
				ItemStack item = new ItemStack(m, amount);
				System.out.println(item.toString());
				return item;
			}
		}
		System.out.println("no match found for" + itemString);
		return null;
	}
	
	public static ItemStack stringToItemStack(String itemString) {
		itemString=itemString.replace("_", " ");
		for (Material m : Material.values()) {
			if (itemString.equalsIgnoreCase(m.toString().replace("_", " "))) {
				System.out.println("found a match");
				ItemStack item = new ItemStack(m);
				System.out.println(item.toString());
				return item;
			}
		}
		System.out.println("no match found for" + itemString);
		return null;
	}


}

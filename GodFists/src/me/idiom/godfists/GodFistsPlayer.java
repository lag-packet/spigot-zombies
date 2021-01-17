package me.idiom.godfists;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.libs.jline.internal.Log;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import com.mysql.jdbc.Util;

import me.idiom.godfists.utils.MessageManager.MessageType;
import net.md_5.bungee.api.ChatColor;


public class GodFistsPlayer {
	
	private Player p;
	
	public boolean cancelDmg = false;
	
	private List<ItemStack> currentOfferings = new ArrayList<>();
	private int offeringAmount;

	private boolean isChosen = false;
	
	public GodFistsPlayer(Player p) {
		this.p = p;
		setup();
	}
	
	public void setup() {
		if (!GodFists.playerSettings.contains(p.getUniqueId() + "(" + p.getDisplayName() + ").offeringAmount")) {
			GodFists.playerSettings.createConfigSec(p.getUniqueId().toString() + "(" + p.getDisplayName() + ").offeringAmount");
			GodFists.playerSettings.set(p.getUniqueId() + "(" + p.getDisplayName() + ").offeringAmount", 0);
		}
		if (!GodFists.playerSettings.contains(p.getUniqueId() + "(" + p.getDisplayName() + ").currentOffering")) {
			GodFists.playerSettings.createConfigSec(p.getUniqueId() + "(" + p.getDisplayName() + ").currentOffering");
			GodFists.playerSettings.set(p.getUniqueId() + "(" + p.getDisplayName() + ").currentOffering", currentOfferings);
		}
		//updateSettings();
	}
	
	public boolean isChosen() {
		return isChosen;
	}
	
	public void setChosen(boolean bool) {
		isChosen = bool;
	}
	
	public void updateSettings() {
		this.currentOfferings = GodFists.playerSettings.get(p.getUniqueId() + "(" + p.getDisplayName() + ").currentOffering");
		this.offeringAmount = GodFists.playerSettings.get(p.getUniqueId() + "(" + p.getDisplayName() + ").offeringAmount");
		
	}
	
	public List<ItemStack> currentOfferings() {
		return currentOfferings;
	}
	
	public int getItemCount(ItemStack item) {
		int count = 0;
		for (ItemStack i : currentOfferings()) {
			if (item.getType() == i.getType()) {count++;}
		}
		return count;
	}
	
	
	public int offeringAmount() {
		return offeringAmount;
	}
	
	
	public Player bukkitPlayer() {
		return p;
	}
	
	public boolean hasOfferingReq() {
		if (GodFists.godGame.offeringReqList().size() == 0) { return false; }
		 
		HashMap<Material, Boolean> testedItems = new HashMap<>();
		for (ItemStack godItem : GodFists.godGame.offeringReqList()) {
			
			if (!testedItems.containsKey(godItem.getType())) {
				testedItems.put(godItem.getType(), false);
			}
			
			System.out.println("testing: " + godItem);
			for (ItemStack currentOff : currentOfferings()) {
				if (currentOff.getType() == godItem.getType()) {
					System.out.println("testing god item " + godItem + " with  " + currentOff);
					if (!testedItems.get(godItem.getType()) && getItemCount(currentOff) >= GodFists.godGame.getItemAmount(godItem)) {
						testedItems.put(godItem.getType(), true);
					}	
				}
			}
			for (boolean b : testedItems.values()) {
			    if (b == false) {
			    	return false;
			    }
			}
		}
		return true;
	}

}

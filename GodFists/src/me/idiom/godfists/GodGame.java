package me.idiom.godfists;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitTask;

import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.Utils;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class GodGame {
	
	private List<ItemStack> offeringReq = new ArrayList<>();
	private int timer = 0;
	private int godTaskID;
	private BukkitTask godGameTickTask;
	
	public boolean gameEnded;
	
	private God god;
	
	public GodGame() {
		setup();
	}
	
	public void setup() {
		
		if (GodFists.godSettings.get("offerings") == null) {System.out.println("making config part");GodFists.godSettings.createConfigSec("offerings");}
		refreshItems();
		
		/*if (GodFists.godSettings.get("timer") == null) { GodFists.godSettings.createConfigSec("timer"); GodFists.godSettings.set("timer", 0); }
		timer = GodFists.godSettings.get("timer");*/
		
	}
	
	public void refreshItems() {
		offeringReq.clear();
		for (String key : GodFists.godSettings.<ConfigurationSection>get("offerings").getKeys(true)) {
			System.out.println(key);
			int amount = Integer.parseInt(GodFists.godSettings.get("offerings." + key));
			ItemStack item = Utils.stringToItemStack(key, amount);
			offeringReq.add(item);
		}
	}
	
	public void startGod() {
		gameEnded = false;
		godTaskID = GodFists.getPlugin().getServer().getScheduler().scheduleSyncDelayedTask(GodFists.getPlugin(), new Runnable() {
			
			@Override
			public void run() {

				if (getHighestOfferer() != null) {
					MessageManager.getInstance().msg((Player)getHighestOfferer(), MessageType.BAD, "You are now wielded with the most powerful force known to man!");
					god = new God(GodFists.godGame.getHighestOfferer());
					god.setChosen(true);
					if (god.isChosen()) {
						god.setupScoreboard();
						god.bukkitPlayer().setAllowFlight(true);
						System.out.println("Set flying" + god.bukkitPlayer().getAllowFlight());
						runGod();
						Bukkit.getScheduler().cancelTask(godTaskID);
						System.out.println("God start task canceled");
					}
				}
			}
		}, 20 * timer);
	}
	
	public void end() {
		if (god != null) { 
			god.reset();
			if (god.bukkitPlayer() != null) {god.bukkitPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());}
			god = null;
		}
		if (gameEnded == false) { gameEnded = true;}
		System.out.println("end() in GodGame");
		for (GodFistsPlayer p : GodFists.playerManager.players()) {
			Player player = p.bukkitPlayer();
			
			MessageManager.getInstance().msg(player, MessageType.INFO, "God ritual has ended. <ADD END CODE FOR GOD>");
		}
		Bukkit.getScheduler().cancelTask(godGameTickTask.getTaskId());
	}
	
	public void runGod() {
		
		godGameTickTask = GodFists.getPlugin().getServer().getScheduler().runTaskTimer(GodFists.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if ( god == null || god.godStopTime() <= System.currentTimeMillis()) {
					end();
				}
				if (god != null) {
					//TODO this is where run god abilites.
					System.out.println("updating scoreboard.");
					god.updatecoolDownAndScoreBoard();
				}
			}
		}, 0, 20);
	}

	public int getItemAmount(ItemStack i) {
		for (ItemStack tempI : this.offeringReq) {
			if (i.getType() == tempI.getType())
				return tempI.getAmount();
		}
		return 0;
	}
	  
	private Player getHighestOfferer() {
		GodFistsPlayer highestPlayer = GodFists.playerManager.players().get(0);

		
		for (GodFistsPlayer player : GodFists.playerManager.players()) {
			if (player.offeringAmount() > highestPlayer.offeringAmount()) {
				highestPlayer = player;
			}
		}
		Player player = highestPlayer.bukkitPlayer();
		
		return player;
	}
	
	
	public God getGod() {
		return god;
	}
	
	public void setGodNull() {
		god = null;
	}
	
	public List<ItemStack> offeringReqList() {
		return offeringReq;
	}
	
	public int timer() {
		return timer;
	}
	
	public void setTimer(int seconds) {
		timer = seconds;
	}
	
	
}
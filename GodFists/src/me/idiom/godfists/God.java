package me.idiom.godfists;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.Utils;
import me.idiom.godfists.utils.MessageManager.MessageType;
import net.md_5.bungee.api.ChatColor;

public class God extends GodFistsPlayer {
	
	private boolean doneFalling = false;
	private long gfistCooldown;
	private long gflyCooldown;
	private long gteleCooldown;
	private long godStopTime;
	
	private Scoreboard scoreboard;
	private Objective objective;
	private Team tfistcooldown;
	
	public God(Player p) {
		super(p);
		
		gfistCooldown = 0;
		gflyCooldown = 0;
		godStopTime = System.currentTimeMillis() + (Utils.godEndTimer * 1000);
		System.out.println("god stop time " + godStopTime);
		setChosen(true);
	}

	public void updatecoolDownAndScoreBoard() {
		int gfistCooldownCountdown =  Utils.gfistTimer - ((int) System.currentTimeMillis() / 1000 - (int) gfistCooldown / 1000);
		int gflyCoolDownCountdown = Utils.gflyTimer - ((int) System.currentTimeMillis() / 1000 - (int) gfistCooldown / 1000);
		int gteleCoolDownCountdown = Utils.gteleTimer - ((int) System.currentTimeMillis() / 100 - (int) gteleCooldown / 1000);
		if (gfistCooldownCountdown <= 0 || gfistCooldownCountdown > Utils.gfistTimer) {gfistCooldownCountdown = 0;}
		if (gflyCoolDownCountdown <= 0 || gflyCoolDownCountdown > Utils.gflyTimer) {gflyCoolDownCountdown = 0;}
		
		flyCoolDownUpdater(gflyCoolDownCountdown, gfistCooldownCountdown);
		objective.getScore(ChatColor.GOLD + "God Fist: ").setScore(gfistCooldownCountdown);
		objective.getScore(ChatColor.GOLD + "Fly length: ").setScore(gflyCoolDownCountdown);
	}
	
	
	
	public void flyCoolDownUpdater(int gflyCoolDownCountdown, int gfistCooldownCountdown) {
		if (gflyCoolDownCountdown <= 0 && bukkitPlayer().getAllowFlight() && gfistCooldownCountdown > 0) {
			MessageManager.getInstance().msg(bukkitPlayer(), MessageType.BAD, "You feel a powerful force fleeting from your feet...");
			bukkitPlayer().setAllowFlight(false);
		}
		else if (gfistCooldownCountdown <= 0 && !bukkitPlayer().getAllowFlight()) {
			MessageManager.getInstance().msg(bukkitPlayer(), MessageType.GOOD, "You feel the familiar force return to your feet!");
			bukkitPlayer().setAllowFlight(true);
		}
	}
	
	public void setupScoreboard() {
		scoreboard = GodFists.getPlugin().getServer().getScoreboardManager().getNewScoreboard();
		objective = scoreboard.registerNewObjective("GodFists", "", ChatColor.AQUA + "YOU'RE THE GOD HOLDER");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		tfistcooldown = scoreboard.registerNewTeam("Fist Cool Down");
		tfistcooldown.addEntry(ChatColor.GOLD + "God Fist: ");
		tfistcooldown.setSuffix("");
		tfistcooldown.setPrefix("");
		
		bukkitPlayer().setScoreboard(scoreboard);
	}
	
	public boolean canUseFist() {
		return gfistCooldown() + (Utils.gfistTimer * 1000) <= System.currentTimeMillis() || gfistCooldown() <= 0;
	}
	
	public void reset() {
		System.out.println("reset god stats");
		System.out.println(bukkitPlayer().getName());
		bukkitPlayer().setAllowFlight(false);
	}
	
	public void setgodStopTime(Long l) {
		godStopTime = l;
	}
	
	public long godStopTime() {
		return godStopTime;
	}
	
	public void setgflyCooldown(Long l) {
		gflyCooldown = l;
	}
	
	public Long gflyCooldown() {
		return gflyCooldown;
	}
	 
	public void setgfistCooldown(Long l) {
		gfistCooldown = l;
	}
	
	public Long gfistCooldown() {
		return gfistCooldown;
	}
	
	public boolean doneFalling() {
		return doneFalling;
	}

	public void setDoneFalling(boolean doneFalling) {
		this.doneFalling = doneFalling;
	}
	
	public Scoreboard getScoreboard() {
		return scoreboard;
	}
	
	public void setScoreboardNew() {
		scoreboard.clearSlot(DisplaySlot.SIDEBAR);
	}
	
}

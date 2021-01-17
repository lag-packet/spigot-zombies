package me.idiom.godfists;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import me.idiom.godfists.listeners.OfferingsListener;
import me.idiom.godfists.utils.CommandManager;
import me.idiom.godfists.utils.PlayerManager;
import me.idiom.godfists.utils.RitualManager;
import me.idiom.godfists.utils.SettingsManager;
import me.idiom.godfists.listeners.GodFistsPlayerListener;
import me.idiom.godfists.listeners.GodListener;


public class GodFists extends JavaPlugin {
	
	public static GodGame godGame;
	public static RitualManager ritualManager;
	public static PlayerManager playerManager;
	public static SettingsManager godSettings;
	public static SettingsManager playerSettings;
	public static SettingsManager offeringCircleSettings;

	@Override
	public void onEnable() {
		godSettings = new SettingsManager("god");
		playerSettings = new SettingsManager("players");
		offeringCircleSettings = new SettingsManager("offering-circle");
		godGame = new GodGame();
		ritualManager = new RitualManager();
		playerManager = new PlayerManager();

		CommandManager cm = new CommandManager();
    	cm.setup();
    	getCommand("godfists").setExecutor(cm);
    	
		PluginManager pm = Bukkit.getServer().getPluginManager();	
    	pm.registerEvents(new GodFistsPlayerListener(), this);
    	pm.registerEvents(new OfferingsListener(), this);
    	pm.registerEvents(new GodListener(), this);
	}

	@Override
	public void onDisable() { 
		
	}
	
	public static Plugin getPlugin() {
		return Bukkit.getServer().getPluginManager().getPlugin("GodFists");
	}
}

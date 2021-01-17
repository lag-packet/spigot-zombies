package me.idiom.godfists.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import me.idiom.godfists.GodFists;

public class SettingsManager {
	
	private File file;
	public FileConfiguration config;
	
	
	public SettingsManager(String fileName) {
		if (!GodFists.getPlugin().getDataFolder().exists()) GodFists.getPlugin().getDataFolder().mkdir();
		
		file = new File(GodFists.getPlugin().getDataFolder(), fileName + ".yml");
		
		if (!file.exists()) {
			try { file.createNewFile(); }
			catch (IOException e) { e.printStackTrace(); }
		}
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public ConfigurationSection createConfigSec(String path) {
		ConfigurationSection c = config.createSection(path);
		try { config.save(file); }
		catch (Exception e) { e.printStackTrace(); }
		return c;
	}
	
	public void set(String path, Object value) {
		config.set(path, value);
		try { config.save(file); }
		catch (Exception e) { e.printStackTrace(); }
	}
	@SuppressWarnings("unchecked")
	public <T> T get(String path) {
		return (T) config.get(path);
	}public SettingsManager() {
		// TODO Auto-generated constructor stub
	}
	public boolean contains(String path) {
		return config.contains(path);
	}
}

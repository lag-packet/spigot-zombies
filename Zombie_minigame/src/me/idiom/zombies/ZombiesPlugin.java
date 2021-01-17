package me.idiom.zombies;

import me.idiom.zombies.listeners.ArenaListener;
import me.idiom.zombies.listeners.ArenaSetupListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ZombiesPlugin extends JavaPlugin {
    //make it naruto theme

    @Override
    public void onEnable() {
        DevCommands devCommands = new DevCommands();
        getCommand("zombies").setExecutor(devCommands);

        PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new ArenaSetupListener(), this);
        pm.registerEvents(new ArenaListener(), this);
    }

    @Override
    public void onDisable() {

    }

    public static Plugin getPlugin() {
        return Bukkit.getServer().getPluginManager().getPlugin("Zombies");
    }
}

package me.idiom.godfists.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.idiom.godfists.GodFists;
import me.idiom.godfists.GodFistsPlayer;
import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class GodFistsPlayerListener implements Listener{
	
	public GodFistsPlayerListener() {
		
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		System.out.println("player joined!");
		if (!GodFists.playerSettings.contains(e.getPlayer().getUniqueId().toString() + "(" + e.getPlayer().getDisplayName() + ")")) {
			System.out.println("doing thsi!");
			GodFists.playerSettings.createConfigSec(e.getPlayer().getUniqueId().toString() + "(" + e.getPlayer().getDisplayName() + ")");
		}
		
		GodFistsPlayer gfp = new GodFistsPlayer(e.getPlayer());
		System.out.println(GodFists.playerManager.getPlayer(e.getPlayer()));
		GodFists.playerManager.players().add(gfp);
		gfp.updateSettings();
		System.out.println("the items player has in offering: " + gfp.currentOfferings());
		 
	}
	
	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		System.out.println("player left");
		if (GodFists.godGame.getGod() != null && e.getPlayer().getUniqueId() == GodFists.godGame.getGod().bukkitPlayer().getUniqueId()) {
			GodFists.godGame.setGodNull();
			System.out.println("set god to null ");
			for (GodFistsPlayer gp : GodFists.playerManager.players()) {
				MessageManager.getInstance().msg(gp.bukkitPlayer(), MessageType.INFO, "The god fists holder has vanished...");
			}
		}
		GodFists.playerManager.removePlayer(e.getPlayer());
	}
	
}

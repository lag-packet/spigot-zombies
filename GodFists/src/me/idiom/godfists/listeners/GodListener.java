package me.idiom.godfists.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import me.idiom.godfists.God;
import me.idiom.godfists.GodFists;
import me.idiom.godfists.GodFistsPlayer;
import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.Utils;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class GodListener implements Listener {
	
	@EventHandler
	public void cancelChosenFallDmg(EntityDamageEvent e) {
		if (GodFists.godGame.getGod() == null) {return;}
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		
		God god = GodFists.godGame.getGod();
		
		if (!god.doneFalling() && god.bukkitPlayer().isOnGround()) {
			System.out.println("Cancleing fall dmg");
			god.setDoneFalling(true);
			e.setCancelled(true);
		}
		
	}
	
	@EventHandler
	public void modifyChosenDamage(EntityDamageByEntityEvent e) {
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (GodFists.godGame.getGod() == null) {return;}
		if (GodFists.godGame.getGod().bukkitPlayer().getUniqueId() != e.getDamager().getUniqueId()) { return;}
		System.out.println(GodFists.godGame.getGod().bukkitPlayer().getName());
		God god = GodFists.godGame.getGod();
		
		if (god.canUseFist()) {
			e.setDamage(100);
			god.setgfistCooldown(System.currentTimeMillis());
			god.setgflyCooldown(System.currentTimeMillis());
			System.out.println("set this " + god.gfistCooldown() + "     " + System.currentTimeMillis());
		} else {
			MessageManager.getInstance().msg(god.bukkitPlayer(), MessageType.INFO, "You're on cooldown for god fists!");
		}
	}
	
	@EventHandler
	public void godFly(PlayerToggleFlightEvent e) {
		if (GodFists.godGame.getGod() == null) {return;}
		God god = GodFists.godGame.getGod();
		
		//if they have gf up they can fly 
		//TODO: Refactor this condition.
		if ((god.gfistCooldown() + (Utils.gfistTimer * 1000) <= System.currentTimeMillis() || god.gfistCooldown() <= 0) 
				&& god.bukkitPlayer().getAllowFlight()) {
			if (god.doneFalling()) {System.out.println("set done falling"); god.setDoneFalling(false);}
			return;
		}
		
		if (god.bukkitPlayer().getAllowFlight()
				&& god.gflyCooldown() + (Utils.gfistTimer * 1000) <= System.currentTimeMillis()) {
			god.setgflyCooldown(System.currentTimeMillis());
			System.out.println("setting false");
			god.bukkitPlayer().setAllowFlight(false);
		}
		 
		
		
	}

}

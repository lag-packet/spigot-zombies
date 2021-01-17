package me.idiom.godfists.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import me.idiom.godfists.GodFistsPlayer;


public class PlayerManager {
	
	private List<GodFistsPlayer> players = new ArrayList<>();
	
	public PlayerManager() {
		
	}
	
	public List<GodFistsPlayer> players() {
		return players;
	}
	
	public GodFistsPlayer getPlayer(Player player) {
		for(GodFistsPlayer p : players) {
			if (p.bukkitPlayer().getUniqueId() == player.getUniqueId()) {
				return p;
			}
		}
		return null;
	}
	
	public void removePlayer(Player player) {
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).bukkitPlayer().getUniqueId() == player.getUniqueId()) {
				players.remove(i);
				System.out.println("removed " + player);
			}
		}
	}

}

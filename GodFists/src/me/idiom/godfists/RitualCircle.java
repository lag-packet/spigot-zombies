package me.idiom.godfists;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;

public class RitualCircle {
	
	private List<Location> blocks = new ArrayList<>();
	private int ID;
	
	public void setup() {
		blocks = GodFists.offeringCircleSettings.get("circles." + ID + ".blocks");
	}
	
	public RitualCircle(int ID, List<Location>blocks) {
		this.ID = ID;
		blocks = GodFists.offeringCircleSettings.get("circles." + ID + ".blocks");
		this.blocks = blocks;
	}
	
	public int getID() {
		return ID;
	}
	
	public List<Location> getBlocks() {
		return blocks;
	}

}

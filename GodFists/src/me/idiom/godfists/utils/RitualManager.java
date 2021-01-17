package me.idiom.godfists.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

import me.idiom.godfists.GodFists;
import me.idiom.godfists.RitualCircle;

public class RitualManager {

	private List<RitualCircle> ritualCircles = new ArrayList<>(); 
	
	public RitualManager() {
		setupRituals();
	}
	
	private void setupRituals() {
		if (!GodFists.offeringCircleSettings.contains("circles")) {
			//GodFists.offeringCircleSettings.createConfigSec("circles");
			return;
		}
		
		
		for (String key : GodFists.offeringCircleSettings.<ConfigurationSection>get("circles").getKeys(false)) {
			List<Location> blocks = GodFists.offeringCircleSettings.get("circles." + key + ".blocks");
			//if (blocks != null) {
				//System.out.println("added");
				ritualCircles.add(new RitualCircle(Integer.parseInt(key), blocks));
				System.out.println("added cirlce " + ritualCircles.get(Integer.parseInt(key)).getID());
			//}
		}
	}
	
	public List<RitualCircle> getCircles() {
		return ritualCircles;
	}
	
	public void refresh() {
		setupRituals();
	}
	
	public RitualCircle getCircleById(int ID) {
		for (RitualCircle c : ritualCircles) {
			if (c.getID() == ID) {
				return c;
			}
		}
		return null;
	}
}

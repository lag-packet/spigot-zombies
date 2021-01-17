package me.idiom.godfists.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.inventory.ItemStack;

import me.idiom.godfists.GodFists;
import me.idiom.godfists.RitualCircle;
import me.idiom.godfists.utils.MessageManager;
import me.idiom.godfists.utils.MessageManager.MessageType;

public class OfferingsListener implements Listener {

	// make it so when players break a ritual circle they get a unlikeness level
	// from the gods lowering their chances of getting
	// god fists? or something more maybe as a penalty.

	// god fist gamemo

	@EventHandler
	public void playerThrowItemInRitualCircle(PlayerDropItemEvent e) {

		Item itemDropped = e.getItemDrop();
		Location playerLocation = e.getPlayer().getLocation();

		for (RitualCircle rc : GodFists.ritualManager.getCircles()) {
			for (Location b : rc.getBlocks()) {
				if (b.distance(playerLocation) >= 3.5) {
					return;
				}
			}
		}
		List<ItemStack> playerOfferingList = GodFists.playerManager.getPlayer(e.getPlayer()).currentOfferings();
		for (ItemStack godItem : GodFists.godGame.offeringReqList()) {
			if (itemDropped.getItemStack().getType() == godItem.getType()) {
				if (GodFists.playerManager.getPlayer(e.getPlayer())
						.getItemCount(itemDropped.getItemStack()) >= GodFists.godGame.getItemAmount(godItem)
						&& !GodFists.playerManager.getPlayer(e.getPlayer()).hasOfferingReq()) {
					MessageManager.getInstance().msg(e.getPlayer(), MessageType.BAD,
							"You already fufilled the offering of this item type.");
					e.setCancelled(true);
					return;
				}
				playerOfferingList.add(itemDropped.getItemStack());
				itemDropped.remove();
				GodFists.playerSettings.set(
						e.getPlayer().getUniqueId() + "(" + e.getPlayer().getDisplayName() + ").currentOffering",
						playerOfferingList);
				System.out.println(
						GodFists.playerManager.getPlayer(e.getPlayer()).getItemCount(itemDropped.getItemStack()));
			}
			if (GodFists.playerManager.getPlayer(e.getPlayer()).hasOfferingReq()) {
				int amount = GodFists.playerManager.getPlayer(e.getPlayer()).offeringAmount();
				playerOfferingList.clear();
				GodFists.playerSettings.set(
						e.getPlayer().getUniqueId() + "(" + e.getPlayer().getDisplayName() + ").offeringAmount",
						amount + 1);
				// GodFists.playerSettings.set(e.getPlayer().getUniqueId() + "(" +
				// e.getPlayer().getDisplayName() + ").currentOffering", playerOfferingList);
				System.out.println("that would be, " + (amount + 1) + " sir.");
				GodFists.playerManager.getPlayer(e.getPlayer()).updateSettings();
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void playerMakeCircle(PlayerInteractEvent e) {

		ItemStack flintNSteel = new ItemStack(Material.FLINT_AND_STEEL);
		Block topLeft = null;
		Block topRight = null;
		Block bottomLeft = null;
		Block bottomRight = null;

		boolean validCircle = false;

		if (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand().equals(flintNSteel)) {

			Block interactedBlock = e.getClickedBlock();

			int interBlX = interactedBlock.getX();
			int interBlY = interactedBlock.getY();
			int interBlZ = interactedBlock.getZ();

			if (e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ - 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (e.getPlayer().getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					topLeft = interactedBlock;
					topRight = e.getPlayer().getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ); // topright
					if (blockTorchCheck("topright", topRight, e.getPlayer())) {
						bottomLeft = e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ + 1);
						if (blockTorchCheck("bottomleft", bottomLeft, e.getPlayer())) {
							bottomRight = e.getPlayer().getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ + 1);
							if (blockTorchCheck("bottomright", bottomRight, e.getPlayer())) {
								validCircle = true;
							}
						}
					}
				}
			}
			if (e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ - 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (e.getPlayer().getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					topRight = interactedBlock;
					topLeft = e.getPlayer().getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ);
					if (blockTorchCheck("topleft", topLeft, e.getPlayer())) {
						bottomLeft = e.getPlayer().getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ + 1);
						if (blockTorchCheck("bottomleft", bottomLeft, e.getPlayer())) {
							bottomRight = e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ + 1);
							if (blockTorchCheck("bottomright", bottomRight, e.getPlayer())) {
								validCircle = true;
							}
						}
					}
				}
			}
			if (e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ + 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (e.getPlayer().getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ - 1).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					bottomRight = interactedBlock;
					bottomLeft = e.getPlayer().getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ);
					if (blockTorchCheck("bottomleft", bottomLeft, e.getPlayer())) {
						topLeft = e.getPlayer().getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ - 1);
						if (blockTorchCheck("topleft", topLeft, e.getPlayer())) {
							topRight = e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ - 1);
							if (blockTorchCheck("topright", topRight, e.getPlayer())) {
								validCircle = true;
							}
						}
					}
				}
			}
			if (e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ + 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (e.getPlayer().getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					bottomLeft = interactedBlock;
					topLeft = e.getPlayer().getWorld().getBlockAt(interBlX, interBlY, interBlZ - 1);
					if (blockTorchCheck("topleft", topLeft, e.getPlayer())) {
						topRight = e.getPlayer().getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ - 1);
						if (blockTorchCheck("topright", topRight, e.getPlayer())) {
							bottomRight = e.getPlayer().getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ);
							if (blockTorchCheck("bottomright", bottomRight, e.getPlayer())) {
								validCircle = true;
							}
						}
					}
				}
			}
		}

		if (validCircle) {
			// check if the valid circle exists.
			// if it doesnt then ...
			List<Location> blocks = new ArrayList<>();
			blocks.add(topLeft.getLocation());
			blocks.add(topRight.getLocation());
			blocks.add(bottomLeft.getLocation());
			blocks.add(bottomRight.getLocation());

			if (GodFists.offeringCircleSettings
					.get("circles." + (GodFists.ritualManager.getCircles().size())) == null) {
				GodFists.offeringCircleSettings
						.createConfigSec("circles." + GodFists.ritualManager.getCircles().size());
				GodFists.offeringCircleSettings.set("circles." + GodFists.ritualManager.getCircles().size() + ".blocks",
						blocks); // remove .blocks to make it work but fix this for adding blocks
				GodFists.ritualManager.refresh();
			}

			System.out.println("you got a valid circle.");

			topLeft.setType(Material.END_PORTAL);
			topRight.setType(Material.END_PORTAL);
			bottomLeft.setType(Material.END_PORTAL);
			bottomRight.setType(Material.END_PORTAL);

		}

	}

	@EventHandler
	public void playerBreakCircle(BlockBreakEvent e) {

	}

	@EventHandler
	public void playerEnterOfferingPortal(PlayerPortalEvent e) {
		System.out.println(e.getPlayer().getLocation());
		for (RitualCircle rc : GodFists.ritualManager.getCircles()) {
			for (Location b : rc.getBlocks()) {
				if (b.distance(e.getPlayer().getLocation()) <= 3.5) {
					System.out.println("THIS IS TRUE");
					e.setCancelled(true);
					return;
				}
			}
		}
	}

	private boolean blockTorchCheck(String blockmode, Block testBlock, Player p) {
		int interBlX = testBlock.getX();
		int interBlY = testBlock.getY();
		int interBlZ = testBlock.getZ();
		if (blockmode.equalsIgnoreCase("topleft")) {
			if (p.getWorld().getBlockAt(interBlX, interBlY, interBlZ - 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (p.getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					return true;
				}
			}
		} else if (blockmode.equalsIgnoreCase("topright")) {
			if (p.getWorld().getBlockAt(interBlX, interBlY, interBlZ - 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (p.getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					return true;
				}
			}
		} else if (blockmode.equalsIgnoreCase("bottomleft")) {
			if (p.getWorld().getBlockAt(interBlX, interBlY, interBlZ + 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (p.getWorld().getBlockAt(interBlX - 1, interBlY, interBlZ).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					return true;
				}
			}
		} else if (blockmode.equalsIgnoreCase("bottomright")) {
			if (p.getWorld().getBlockAt(interBlX, interBlY, interBlZ + 1).getRelative(BlockFace.UP)
					.getType() == Material.TORCH) {
				if (p.getWorld().getBlockAt(interBlX + 1, interBlY, interBlZ - 1).getRelative(BlockFace.UP)
						.getType() == Material.TORCH) {
					return true;
				}
			}
		}
		return false;
	}
}

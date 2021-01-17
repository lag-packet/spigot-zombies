package me.idiom.zombies.listeners;

import me.idiom.zombies.Arena;
import me.idiom.zombies.ArenaManager;
import me.idiom.zombies.ArenasFile;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class ArenaSetupListener implements Listener {

    @EventHandler
    public void addZombieSpawnBlock(BlockBreakEvent e) {
        if (!e.getPlayer().getInventory().getItemInMainHand().hasItemMeta()) {
            return;
        }

        if (e.getPlayer().getInventory().getItemInMainHand().getType() != Material.WOODEN_SHOVEL) {
            return;
        }

        System.out.println(e.getPlayer());
        Player p = e.getPlayer();
        Location bLoc = e.getBlock().getLocation();
        String arenaID = p.getInventory().getItemInMainHand().getItemMeta().getDisplayName();
        Arena arena = ArenaManager.getInstance().getArena(Integer.parseInt(arenaID));
        e.setCancelled(true);

        for (Location l : arena.getSpawnBlocks()) {
            Location spawnLoc = new Location(bLoc.getWorld(), bLoc.getX(), bLoc.getY() + 1, bLoc.getZ());
            if (l.getX() == spawnLoc.getX() && l.getY() == spawnLoc.getY() && l.getZ() == spawnLoc.getZ()) {
            //if (l.equals(test)) {
                p.sendMessage("not setting that");
                return;
            }
        }

        e.getBlock().setType(Material.DARK_OAK_WOOD);
        Location spawnBlock = new Location(bLoc.getWorld(), bLoc.getX(), bLoc.getY() + 1, bLoc.getZ());
        arena.getSpawnBlocks().add(spawnBlock);
        ArenasFile.getInstance().setData(arenaID + ".zombie-blocks", arena.getSpawnBlocks());
        System.out.println("Set dat data!");

        //if (e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName())
    }
}

package me.idiom.zombies.listeners;

import me.idiom.zombies.Arena;
import me.idiom.zombies.ArenaManager;
import me.idiom.zombies.custommobs.WhiteZetsu;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;


public class ArenaListener implements Listener {
    int counter = 0;

    @EventHandler
    public void addPlayerArenaEvent(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof ArmorStand)){
            return;
        }
        if(!e.getRightClicked().isCustomNameVisible()) {
            return;
        }

        Arena a = ArenaManager.getInstance().getArena(Integer.parseInt(e.getRightClicked().getCustomName()));
        a.addPlayer(e.getPlayer());
        System.out.println("armor added " + a.playerList());
        if (a.state == Arena.ArenaState.WAITING && a.playerList().size() > 0) {
            a.state = Arena.ArenaState.STARTED;
            a.runGame();
        }
    }

    @EventHandler
    public void removeZombie(EntityDeathEvent e) {
        if (!e.getEntity().isCustomNameVisible()) {
            return;
        }
        if (!e.getEntity().getCustomName().contains("White Zetsu")) {
            return;
        }

        if (isZetsu(e.getEntity())) {
            WhiteZetsu z = (WhiteZetsu) ((CraftEntity) e.getEntity()).getHandle();
            ArenaManager.getInstance().getArena(z.getArena().getID()).aliveZetsuList().remove(z);
            counter++;
            System.out.println("Removed a zetsu counter : " + counter);
        }

        //for arena
        //for arena blocks
            //if zetsuLoc near block arenaid == id
            //areanaAliveZetsu--;
    }

    @EventHandler
    public void removePlayer(PlayerDeathEvent e){
        for (Arena a : ArenaManager.getInstance().getArenas()) {
            if (a.playerList().contains(e.getEntity())) {
                e.getEntity().sendMessage("YOU DIED AND LOST!");
                a.removePlayer(e.getEntity());
            }
        }
    }

    private boolean isZetsu(Entity entity) {
        return ((CraftEntity) entity).getHandle() instanceof WhiteZetsu;
    }
}

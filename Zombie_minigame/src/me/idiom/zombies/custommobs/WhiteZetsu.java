package me.idiom.zombies.custommobs;

import me.idiom.zombies.Arena;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

public class WhiteZetsu extends EntityZombie {

    private Arena arena;

    public WhiteZetsu(Location loc, Arena a) {
        super(EntityTypes.ZOMBIE, ((CraftWorld) loc.getWorld()).getHandle());
        this.setPosition(loc.getX(), loc.getY(),loc.getZ());
        this.setCustomName(new ChatComponentText(ChatColor.GRAY + "White Zetsu"));
        this.setCustomNameVisible(true);
        this.arena = a;
    }

    @Override
    public void initPathfinder() {
        this.goalSelector.a(0, new PathfinderGoalFloat(this));
        this.goalSelector.a(1, new PathfinderGoalNearestAttackableTarget(this,EntityHuman.class,false));
        this.goalSelector.a(2, new PathfinderGoalZombieAttack(this,1.0,false));
        this.goalSelector.a(3, new PathfinderGoalRandomStroll(this,1.0));
        //this.goalSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, this, false));
    }

    public Arena getArena() {
        return arena;
    }
}

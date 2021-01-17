package me.idiom.zombies;

import me.idiom.zombies.custommobs.WhiteZetsu;
import net.minecraft.server.v1_16_R3.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

public class Arena {

    public enum ArenaState {DISABLED, WAITING, COUNTING_DOWN, STARTED};

    public enum ArenaType {SOLO, PARTY};

    private int ID;
    private int arenaRunID;
    private int roundNumber, spawnEnemyAmount, spawnedEnemies;

    private Location location;
    private List<Player> players = new ArrayList<>();
    private List<WhiteZetsu> aliveZetsu = new ArrayList<>();
    private List<Location> spawnBlocks;
    private List<Location> arenaBlocks;
    private Arena instance = this;

    public ArenaState state = ArenaState.DISABLED;
    public ArenaType partyType;

    public Arena(int ID) {
        this.ID = ID;
        loadData();
        state = ArenaState.WAITING;
        partyType = ArenaType.SOLO;
    }

    public void runGame() {
        roundNumber = 1;
        spawnEnemyAmount = 5;
        spawnedEnemies = 0;

        System.out.println("Started game");
        System.out.println("Location is " + location);


        for (Player p : playerList()) {
            p.teleport(location);
        }

        arenaRunID = ZombiesPlugin.getPlugin().getServer().getScheduler().scheduleSyncRepeatingTask(ZombiesPlugin.
                getPlugin(), new Runnable() {

            int lastSpawnEnemyAmount = spawnEnemyAmount;
            int totalSpawned = 0;

            @Override
            public void run() {

                if (playerList().size() == 0 ) {
                    System.out.println("Ending gamemode");
                    end();
                }

                if (spawnEnemyAmount != 0) {
                    for (Location l : spawnBlocks) {
                        if (spawnEnemyAmount == 0) {
                            break;
                        }
                        WhiteZetsu zetsu = new WhiteZetsu(l,instance);
                        WorldServer world = ((CraftWorld) l.getWorld()).getHandle();
                        world.addEntity(zetsu);
                        //world.getEntity(zetsu.getUniqueID());
                        aliveZetsu.add(zetsu);
                        spawnEnemyAmount--;
                        totalSpawned++;
                    }
                }

                if (spawnEnemyAmount == 0 && aliveZetsu.size() == 0) {
                    roundNumber++;
                    for (Player p : playerList()) {
                        p.sendMessage("Round " + roundNumber);
                        System.out.println("Total spawned In arena " + ID + ":" + totalSpawned);
                    }
                    lastSpawnEnemyAmount = lastSpawnEnemyAmount * 2;
                    spawnEnemyAmount = lastSpawnEnemyAmount;
                }
            }
        },0, 20);
    }

    public void end() {
        Bukkit.getScheduler().cancelTask(arenaRunID);
        if (aliveZetsu.size() != 0) {
            for (WhiteZetsu z : aliveZetsu) {
                if (z.isAlive()) {
                    z.getBukkitEntity().remove();
                }
            }
            aliveZetsu = new ArrayList<>();
        }
    }

    public void loadData() {
        if (ArenasFile.getInstance().getYaml().contains(ID + ".location")) {
            location = (Location) ArenasFile.getInstance().getYaml().get(ID + ".location");
        }

        if (!ArenasFile.getInstance().getYaml().contains(ID + ".arena-blocks")) {
            arenaBlocks = new ArrayList<>();
            ArenasFile.getInstance().setData(ID + ".arena-blocks", arenaBlocks);
        } else {
            arenaBlocks = (List<Location>) ArenasFile.getInstance().getYaml().getList(ID + ".arena-blocks");
        }

        if (!ArenasFile.getInstance().getYaml().contains(ID + ".zombie-blocks")) {
            spawnBlocks = new ArrayList<>();
            ArenasFile.getInstance().setData(ID + ".zombie-blocks", spawnBlocks);
        } else {
            System.out.println("Loaded from else");
            spawnBlocks = (List<Location>) ArenasFile.getInstance().getYaml().getList(ID + ".zombie-blocks");
        }
    }

    public void addPlayer(Player p) {
        if (!players.contains(p)) {
            players.add(p);
        } else {
            p.sendMessage("You're already in a arena!");
        }
    }

    public void removePlayer(Player p) {
        /*for (Player iteratedP : playerList()) {
            System.out.println("checking " + iteratedP.getUniqueId() + " against " + p.getUniqueId());
            if (p.getUniqueId().equals(iteratedP)) {
                players.remove(p);
            }
        }*/
        System.out.println("Calling remove player");
        if (players.contains(p)) {
            players.remove(p);
            System.out.println("Removed " + p.getName() + " " + ID );
        }
    }

    public int getID() {
        return ID;
    }

    public List<Location> getSpawnBlocks() {
        if (spawnBlocks == null) {
            System.out.println("Arena was never loaded");
            loadData();
        }
        return spawnBlocks;
    }

    public List<Player> playerList() {
        return players;
    }

    public List<WhiteZetsu> aliveZetsuList(){
        return aliveZetsu;
    }

}

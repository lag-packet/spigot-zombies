package me.idiom.zombies;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DevCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Not a player");
            return true;
        }

        Player p = (Player) commandSender;

        if (command.getName().equalsIgnoreCase("zombies")) {

            if (args.length == 0) {
                p.sendMessage("not enough args.");
                return true;
            }

            if (args[0].equalsIgnoreCase("set")) {
                if (args[1].equalsIgnoreCase("arena")) {
                    int arenaID = Integer.parseInt(args[2]);
                    double pX = p.getLocation().getX();
                    double pY = p.getLocation().getY();
                    double pZ = p.getLocation().getZ();

                    ArenasFile.getInstance().setData(args[2] + ".location", new Location(p.getWorld(), pX, pY, pZ));

                    p.sendMessage("saved");
                } else {
                    p.sendMessage("invalid arena.");
                }
            } else if (args[0].equals("teleport")) {
                if (args.length != 2) {
                    p.sendMessage("Where is the arena number");
                    return true;
                }
                Location l = (Location) ArenasFile.getInstance().getYaml().get("ID." + args[1]);
                p.teleport(l);
            } else if (args[0].equalsIgnoreCase("spawn")) {
                if (args[1].equalsIgnoreCase("arena")) {
                    if (args.length != 3) {
                        p.sendMessage("Where is the arena number");
                        return true;
                    }
                    ItemStack spawnWand = new ItemStack(Material.WOODEN_SHOVEL);
                    ItemMeta im = spawnWand.getItemMeta();
                    im.setDisplayName(args[2]);
                    spawnWand.setItemMeta(im);
                    p.getInventory().addItem(spawnWand);
                    p.sendMessage("set zombies");
                }
            } else if (args[0].equalsIgnoreCase("joiner")) {
                if (args.length != 2) {
                    p.sendMessage("Where is the arena number");
                    return true;
                }
                ArmorStand joinerStand = (ArmorStand) p.getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
                joinerStand.setCustomName(args[1]);
                joinerStand.setCustomNameVisible(true);
                p.sendMessage("dropped the joiner");

            } else if (args[0].equalsIgnoreCase("start")) {
                if (args[1].equalsIgnoreCase("arena")) {
                    if (args.length != 3) {
                        p.sendMessage("Where is the arena number");
                        return true;
                    }
                    if (args[2].equalsIgnoreCase("all")) {

                    } else {
                        Arena arenaToStart = ArenaManager.getInstance().getArena(Integer.parseInt(args[2]));
                        arenaToStart.state = Arena.ArenaState.COUNTING_DOWN;
                    }
                    p.sendMessage("to execute all arenas type all");
                }
            }
        }
        return false;
    }
}

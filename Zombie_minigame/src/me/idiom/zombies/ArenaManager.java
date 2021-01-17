package me.idiom.zombies;

import java.util.ArrayList;
import java.util.List;

public class ArenaManager {

    public static ArenaManager instance;

    public static ArenaManager getInstance() {
        if (instance == null) {
            instance = new ArenaManager();
        }
        return instance;
    }

    private List<Arena> arenas = new ArrayList<>();

    public ArenaManager() {
        loadArenas();
    }

    public void loadArenas() {
        for (String s : ArenasFile.getInstance().getYaml().getKeys(false)) {
            Arena a = new Arena(Integer.parseInt(s));
            System.out.println("Loaded arena" + a.getID());
            arenas.add(a);
        }
    }

    public Arena getArena(int ID) {
        for (Arena a : arenas) {
            if (a.getID() == ID) {
                return a;
            }
        }
        System.out.println("invalid arena");
        return null;
    }

    public List<Arena> getArenas() {
        return arenas;
    }
}

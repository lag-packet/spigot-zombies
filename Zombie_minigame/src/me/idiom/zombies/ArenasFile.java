package me.idiom.zombies;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ArenasFile {

    private File file;
    private YamlConfiguration yamlFile;

    public static ArenasFile instance;

    public static ArenasFile getInstance() {
        if (instance == null) {
            instance = new ArenasFile();
        }
        return instance;
    }

    public ArenasFile() {
        if (!ZombiesPlugin.getPlugin().getDataFolder().exists()) ZombiesPlugin.getPlugin().getDataFolder().mkdir();
        file = new File(ZombiesPlugin.getPlugin().getDataFolder(),"Arenas.yml");

        if (!file.exists()) {
            try {
                System.out.println("Creating an arena file");
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        yamlFile = YamlConfiguration.loadConfiguration(file);
    }

    public void setData(String path, Object o) {
        yamlFile.set(path, o);
        try {
            yamlFile.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public YamlConfiguration getYaml() {
        return yamlFile;
    }

    public File getFile() {
        return file;
    }
}

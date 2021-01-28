package me.idiom.zombies;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class SettingsManager {
    private File file;
    private YamlConfiguration yamlFile;

    private static SettingsManager arenas = new SettingsManager("Arenas");

    public SettingsManager(String fileName) {
        if (!ZombiesPlugin.getPlugin().getDataFolder().exists()) ZombiesPlugin.getPlugin().getDataFolder().mkdir();
        file = new File(ZombiesPlugin.getPlugin().getDataFolder(),fileName + ".yml");

        if (!file.exists()) {
            try {
                System.out.println("Creating an " + fileName + " file");
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

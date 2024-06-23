package me.psikuvit.friends;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    public static FileConfiguration config = null;

    public static void reload() {
        File file = new File("plugins/Friends/config.yml");
        config = YamlConfiguration.loadConfiguration(file);
    }

    public static String getString(String path) {
        return config.getString(path);
    }

    public static String getColoredString(String path) {
        return getString(path).replaceAll("%prefix%", getString("messages.prefix")).replaceAll("&", "");
    }

    public static Boolean getBoolean(String path) {
        return config.getBoolean(path);
    }
}

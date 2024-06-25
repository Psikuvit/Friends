package me.psikuvit.friends;

import me.psikuvit.friends.database.DatabaseType;
import org.bukkit.ChatColor;

public class Utils {

    private static final FriendsPlugin plugin = FriendsPlugin.getInstance();

    public static void log(String toLog) {
       plugin.getLogger().info(toLog);
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public static DatabaseType getDatabaseType() {
        return DatabaseType.valueOf(plugin.getConfig().getString("database-type"));
    }
}

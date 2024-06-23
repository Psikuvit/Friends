package me.psikuvit.friends;

import org.bukkit.ChatColor;

public class Utils {

    public static void log(String s) {
        Main.getInstance().getLogger().info(s);
    }

    public static String color(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}

package me.psikuvit.friends.listeners;

import me.psikuvit.friends.database.MySQLData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private final MySQLData mySQL = MySQLData.getINSTANCE();

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {

    }
}

package me.psikuvit.friends;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Friends {

    private final HashMap<UUID, List<UUID>> playerFriends;
    private final HashMap<UUID, List<UUID>> ignoredPlayers;

    private static Friends INSTANCE;

    public Friends() {
        playerFriends = new HashMap<>();
        ignoredPlayers = new HashMap<>();
    }

    public static Friends getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Friends();
        }
        return INSTANCE;
    }

    public HashMap<UUID, List<UUID>> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public HashMap<UUID, List<UUID>> getPlayerFriends() {
        return playerFriends;
    }

    public void addFriend(Player player, UUID friendToAdd) {
        UUID playerUUID = player.getUniqueId();
        List<UUID> friends = playerFriends.get(playerUUID);
        if (friends.contains(friendToAdd)) {
            player.sendMessage(Utils.color("&cThis player is already your friends!"));
            return;
        }
        friends.add(friendToAdd);

        if (playerFriends.containsKey(playerUUID)) {
            playerFriends.put(playerUUID, friends);
        } else {
            playerFriends.replace(playerUUID, playerFriends.get(playerUUID), friends);
        }
    }
}

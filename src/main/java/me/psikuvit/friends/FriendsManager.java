package me.psikuvit.friends;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class FriendsManager {

    private final HashMap<UUID, List<UUID>> playerFriends;
    private final HashMap<UUID, List<UUID>> ignoredPlayers;

    private static FriendsManager INSTANCE;

    public FriendsManager() {
        playerFriends = new HashMap<>();
        ignoredPlayers = new HashMap<>();
    }

    public static FriendsManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FriendsManager();
        }
        return INSTANCE;
    }

    public HashMap<UUID, List<UUID>> getIgnoredPlayers() {
        return ignoredPlayers;
    }

    public HashMap<UUID, List<UUID>> getPlayerFriends() {
        return playerFriends;
    }

    public void addFriend(Player player, Player friendToAdd) {
        UUID playerUUID = player.getUniqueId();
        UUID friendToAddUUID = friendToAdd.getUniqueId();
        List<UUID> friends = playerFriends.get(playerUUID);

        if (friends.contains(friendToAddUUID)) {
            player.sendMessage(Utils.color("&cThis player is already your friends!"));
            return;
        }

        friends.add(friendToAddUUID);

        playerFriends.merge(playerUUID, friends, (oldValue, newValue) -> newValue);
    }

    public void removeFriend(Player player, Player friendToRemove) {
        UUID playerUUID = player.getUniqueId();
        UUID friendToRemoveUUID = friendToRemove.getUniqueId();
        List<UUID> friends = playerFriends.get(playerUUID);

        if (!friends.contains(friendToRemoveUUID)) {
            player.sendMessage(Utils.color("&cYou don't have a friend named '" + friendToRemove.getName() + "'!"));
            return;
        }

        friends.remove(friendToRemoveUUID);

        if (playerFriends.containsKey(playerUUID)) {
            playerFriends.put(playerUUID, friends);
        } else {
            playerFriends.replace(playerUUID, playerFriends.get(playerUUID), friends);
        }
    }
}
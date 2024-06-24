package me.psikuvit.friends.database.local;

import me.psikuvit.friends.FriendsManager;
import me.psikuvit.friends.Utils;
import me.psikuvit.friends.database.Database;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class SQLite implements Database {

    private final File file = new File("plugins/Friends/friends.yml");
    private final FriendsManager friendsMethods = FriendsManager.getInstance();
    private FileConfiguration friendsYML = null;

    public void reload() {

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                Utils.log("Couldn't create file");
            }
        }
        friendsYML = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public void loadData() {
        for (String player : friendsYML.getKeys(false)) {

            UUID playerUUID = UUID.fromString(player);
            List<UUID> friends = new ArrayList<>();

            for (String friend : friendsYML.getStringList(player)) {
                UUID friendUUID = UUID.fromString(friend);
                friends.add(friendUUID);
            }
            friendsMethods.getPlayerFriends().put(playerUUID, friends);

            Utils.log("Data loaded successfully!");
        }
    }

    @Override
    public void saveData() {
        Map<UUID, List<UUID>> friendsMap = friendsMethods.getPlayerFriends();

        for (Map.Entry<UUID, List<UUID>> entry : friendsMap.entrySet()) {
            UUID playerUUID = entry.getKey();
            List<UUID> friends = entry.getValue();
            friendsYML.set(String.valueOf(playerUUID), friends);
        }

        try {
            friendsYML.save(file);
        } catch (IOException e) {
            Utils.log("Saved Data Successfully!");
        }
    }
}

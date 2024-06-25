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

    private final File directory = new File("plugins/Friends/players");
    private final FriendsManager friendsMethods = FriendsManager.getInstance();
    private FileConfiguration friendsYML = null;

    @Override
    public void connectIntoDB() {
        if (!directory.exists()) {
            if (!directory.mkdirs()) Utils.log("Couldn't create file");
        }
    }

    @Override
    public void loadData() {
        File[] playerFiles = directory.listFiles();

        if (playerFiles == null) return;

        for (File playerFile : playerFiles) {
            if (playerFile.isFile()) {
                FileConfiguration playerYML = YamlConfiguration.loadConfiguration(playerFile);

                String player = playerFile.getName().replace(".yml", "");

                UUID playerUUID = UUID.fromString(player);
                List<UUID> friends = new ArrayList<>();

                for (String friend : playerYML.getStringList("friends")) {
                    UUID friendUUID = UUID.fromString(friend);
                    friends.add(friendUUID);
                }
                friendsMethods.getPlayerFriends().put(playerUUID, friends);
            }
        }
    }

    @Override
    public void saveData() {
        Map<UUID, List<UUID>> friendsMap = friendsMethods.getPlayerFriends();

        for (Map.Entry<UUID, List<UUID>> entry : friendsMap.entrySet()) {

            UUID playerUUID = entry.getKey();
            List<UUID> friends = entry.getValue();
            File playerFile = new File(directory, playerUUID.toString() + ".yml");
            FileConfiguration playerYML = YamlConfiguration.loadConfiguration(playerFile);

            List<String> friendsList = new ArrayList<>();
            for (UUID friend : friends) {
                friendsList.add(friend.toString());
            }
            playerYML.set("friends", friendsList);

            try {
                playerYML.save(playerFile);
            } catch (IOException e) {
                Utils.log("Couldn't save data for player " + playerUUID);
            }
        }

        Utils.log("Saved Data Successfully!");
    }
}

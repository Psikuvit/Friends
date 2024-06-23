package me.psikuvit.friends.database;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import me.psikuvit.friends.Friends;
import me.psikuvit.friends.Main;
import me.psikuvit.friends.Utils;
import org.bukkit.entity.Player;

import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class MySQLData {

    private final MySQL mySQL = Main.getInstance().getMySQL();
    private final Friends friendsMethods = Friends.getInstance();

    public static MySQLData INSTANCE;

    public static MySQLData getINSTANCE() {
        if (INSTANCE == null) {
            INSTANCE = new MySQLData();
        }
        return INSTANCE;
    }

    public void saveHashMaps() {
        try (Connection connection = mySQL.getConnection()) {
            PreparedStatement playerStatement = connection.prepareStatement("INSERT INTO players (player_uuid) VALUES(?) ON DUPLICATE KEY UPDATE player_uuid=player_uuid");
            for (UUID playerUUID : friendsMethods.getPlayerFriends().keySet()) {
                playerStatement.setString(1, playerUUID.toString());
                playerStatement.executeUpdate();
            }

            PreparedStatement friendsStatement = connection.prepareStatement("INSERT INTO friends (player_uuid, friends_uuids) VALUES(?, ?) ON DUPLICATE KEY UPDATE player_uuid=player_uuid");
            for (UUID playerUUID : friendsMethods.getPlayerFriends().keySet()) {
                for (UUID friendUUID : friendsMethods.getPlayerFriends().get(playerUUID)) {
                    friendsStatement.setString(1, playerUUID.toString());
                    friendsStatement.setString(1, friendUUID.toString());
                    friendsStatement.executeUpdate();
                }
            }

            Utils.log("Saved data into the database");

        } catch (SQLException e) {
            Utils.log("Can't find connection");
        }
    }

    public void loadHashMaps() {

        try (Connection connection = mySQL.getConnection()) {

            PreparedStatement playerStatement = connection.prepareStatement("SELECT player_uuid FROM players");
            ResultSet playerRS = playerStatement.executeQuery();

            if (playerRS.next()) {
                UUID playerUUID = UUID.fromString(playerRS.getString("player_uuid"));
                friendsMethods.getPlayerFriends().put(playerUUID, new ArrayList<>());
            }

            PreparedStatement friendStatement = connection.prepareStatement("SELECT player_uuid, friends_uuids FROM friends");
            ResultSet friendsRS = friendStatement.executeQuery();

            if (friendsRS.next()) {
                UUID playerUUID = UUID.fromString(friendsRS.getString("player_uuid"));
                UUID friendsUUID = UUID.fromString(friendsRS.getString("friends_uuids"));
                friendsMethods.getPlayerFriends().get(playerUUID).add(friendsUUID);

            }

            Utils.log("Loaded data from the database");
        } catch (SQLException exception) {
            Utils.log("Couldn't generate player data");
        }
    }
}

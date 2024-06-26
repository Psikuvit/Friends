package me.psikuvit.friends.database.mysql;

import me.psikuvit.friends.FriendsManager;
import me.psikuvit.friends.FriendsPlugin;
import me.psikuvit.friends.Utils;
import me.psikuvit.friends.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class MySQLData implements Database {

    private final MySQL mySQL = FriendsPlugin.getInstance().getMySQL();
    private final FriendsManager friendsMethods = FriendsManager.getInstance();

    @Override
    public void connectIntoDB() {
        mySQL.connectMySQL();
        mySQL.registerMySQL();
    }

    @Override
    public void loadData() {

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

    @Override
    public void saveData() {
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
}

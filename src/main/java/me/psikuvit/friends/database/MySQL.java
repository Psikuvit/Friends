package me.psikuvit.friends.database;

import me.psikuvit.friends.ConfigManager;
import me.psikuvit.friends.Main;
import me.psikuvit.friends.Utils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySQL {


    private final String host = ConfigManager.getString("database.host");
    private final String port = ConfigManager.getString("database.port");
    private final String database = ConfigManager.getString("database.database");
    private final String username = ConfigManager.getString("database.username");
    private final String password = ConfigManager.getString("database.password");
    private Connection connection;

    // Connect to Database
    public void connectMySQL() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
            Utils.log("Connected to MySQL");
        } catch (SQLException ex) {
            Utils.log("No valid MySQL Credentials is set in Config!\n Disabling Plugin!");
            Bukkit.getPluginManager().disablePlugin(Main.getInstance());
        }
    }

    // Disconnect from Database
    public void disconnectMySQL() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                Utils.log("Disconnected from MySQL");
            }
        } catch (SQLException exception) {
            Utils.log("Couldn't disconnect from database");
        }
    }

    // Setting up the Database
    public void registerMySQL() {
        try {
            if (isConnected()) {
                PreparedStatement playerStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS players (player_uuid VARCHAR(100), PRIMARY KEY (player_uuid))");
                playerStatement.executeUpdate();
                PreparedStatement friendsStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS friends (player_uuid VARCHAR(100), friends_uuids VARCHAR(100), PRIMARY KEY (player_uuid))");
                friendsStatement.executeUpdate();
            } else {
                Utils.log("Failed to register database: No connection");
            }
        } catch (SQLException e) {
            Utils.log("Failed to register database");
        }
    }

    // If the Server is connected to the MySQL
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException exception) {
            Utils.log("Couldn't check if database is connected");
            return false;
        }
    }

    // Get the Connection
    public Connection getConnection() {
        if (!isConnected()) {
            connectMySQL();
        }
        return connection;
    }


}

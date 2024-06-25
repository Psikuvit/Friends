package me.psikuvit.friends;

import me.psikuvit.friends.commands.FriendsCommandsRegisterer;
import me.psikuvit.friends.database.Database;
import me.psikuvit.friends.database.DatabaseType;
import me.psikuvit.friends.database.local.SQLite;
import me.psikuvit.friends.database.mysql.MySQL;
import me.psikuvit.friends.database.mysql.MySQLData;
import me.psikuvit.friends.listeners.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class FriendsPlugin extends JavaPlugin {

    private static FriendsPlugin instance;
    private MySQL mySQL;
    private Database database;

    public static FriendsPlugin getInstance() {
        return instance;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.saveData();

        if (Utils.getDatabaseType() == DatabaseType.MySQL) {
            mySQL.disconnectMySQL();
        }
    }

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();

        if (Utils.getDatabaseType() == DatabaseType.MYSQL) {
            database = new MySQLData();
        } else if (Utils.getDatabaseType() == DatabaseType.SQLITE) {
            database = new SQLite();
        }

        database.connectIntoDB();
        database.loadData();


        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        getCommand("friends").setExecutor(new FriendsCommandsRegisterer(this));
        getCommand("friends").setTabCompleter(new FriendsCommandsRegisterer(this));
    }

    public MySQL getMySQL() {
        return mySQL;
    }

}

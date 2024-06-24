package me.psikuvit.friends;

import me.psikuvit.friends.commands.FriendsCommandsRegisterer;
import me.psikuvit.friends.database.Database;
import me.psikuvit.friends.database.DatabaseType;
import me.psikuvit.friends.database.mysql.MySQL;
import me.psikuvit.friends.database.mysql.MySQLData;
import me.psikuvit.friends.listeners.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    private MySQL mySQL;
    private Database database;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();
        ConfigManager.reload();


        if (ConfigManager.getDatabaseType() == DatabaseType.MySQL) {
            mySQL = new MySQL();
            mySQL.connectMySQL();
            mySQL.registerMySQL();

            database = new MySQLData();
        } else if (ConfigManager.getDatabaseType() == DatabaseType.LOCAL) {
            database = new MySQLData();

        }

        database.loadHashMaps();


        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        getCommand("friends").setExecutor(new FriendsCommandsRegisterer(this));
        getCommand("friends").setTabCompleter(new FriendsCommandsRegisterer(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        database.saveHashMaps();

        if (ConfigManager.getDatabaseType() == DatabaseType.MySQL) {
            mySQL.disconnectMySQL();
        }
    }

    public static Main getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}

package me.psikuvit.friends;

import me.psikuvit.friends.commands.FriendsCommandsRegisterer;
import me.psikuvit.friends.database.MySQL;
import me.psikuvit.friends.database.MySQLData;
import me.psikuvit.friends.listeners.JoinListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private static Main instance;

    private MySQL mySQL;

    private MySQLData mySQLData;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();
        ConfigManager.reload();

        mySQL = new MySQL();
        mySQLData = new MySQLData();

        mySQL.connectMySQL();
        mySQL.registerMySQL();
        mySQLData.loadHashMaps();


        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        getCommand("friends").setExecutor(new FriendsCommandsRegisterer(this));
        getCommand("friends").setTabCompleter(new FriendsCommandsRegisterer(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        mySQLData.saveHashMaps();
        mySQL.disconnectMySQL();
    }

    public static Main getInstance() {
        return instance;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}

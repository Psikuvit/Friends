package me.psikuvit.friends.commands;

import me.psikuvit.friends.FriendsManager;
import me.psikuvit.friends.FriendsPlugin;
import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class CommandAbstract {

    protected FriendsPlugin plugin;
    protected FriendsManager friendsMethods = FriendsManager.getInstance();

    public CommandAbstract(final FriendsPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void executeCommand(final String[] args, final CommandSender sender);
    public abstract String correctArg();
    public abstract boolean onlyPlayer();
    public abstract int requiredArg();
    public abstract List<String> tabComplete(final String[] args);


}

package me.psikuvit.friends.commands;

import me.psikuvit.friends.Friends;
import me.psikuvit.friends.Main;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.List;

public abstract class CommandAbstract {

    protected Main plugin;
    protected Friends friendsMethods = Friends.getInstance();

    public CommandAbstract(final Main plugin) {
        this.plugin = plugin;
    }

    public abstract void executeCommand(final String[] args, final CommandSender sender);
    public abstract String correctArg();
    public abstract boolean onlyPlayer();
    public abstract int requiredArg();
    public abstract List<String> tabComplete(final String[] args);


}

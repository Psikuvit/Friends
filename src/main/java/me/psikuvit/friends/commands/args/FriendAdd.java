package me.psikuvit.friends.commands.args;

import me.psikuvit.friends.Main;
import me.psikuvit.friends.Utils;
import me.psikuvit.friends.commands.CommandAbstract;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class FriendAdd extends CommandAbstract {

    public FriendAdd(Main plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player player = (Player) sender;

        UUID friendToAdd = Bukkit.getPlayer(args[1]).getUniqueId();

        if (friendToAdd == null) {
            player.sendMessage(Utils.color("&cPlayer not found!"));
            return;
        }

        if (player.getUniqueId() == friendToAdd) {
            player.sendMessage(Utils.color("&cYou can't add yourself as a friend!"));
            return;
        }

        friendsMethods.addFriend(player, friendToAdd);

    }

    @Override
    public String correctArg() {
        return "/friends add <player_name>";
    }

    @Override
    public boolean onlyPlayer() {
        return true;
    }

    @Override
    public int requiredArg() {
        return 1;
    }

    @Override
    public List<String> tabComplete(String[] args) {
        List<String> completions = new ArrayList<>();

        if (args.length == 0) {
            completions.add("add");
        }

        return completions;
    }
}

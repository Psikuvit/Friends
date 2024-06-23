package me.psikuvit.friends.commands.args;

import me.psikuvit.friends.Main;
import me.psikuvit.friends.Utils;
import me.psikuvit.friends.commands.CommandAbstract;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FriendRemove extends CommandAbstract {

    public FriendRemove(Main plugin) {
        super(plugin);
    }

    @Override
    public void executeCommand(String[] args, CommandSender sender) {
        Player player = (Player) sender;

        Player friendToAdd = Bukkit.getPlayer(args[1]);

        if (friendToAdd == null) {
            player.sendMessage(Utils.color("&cPlayer not found!"));
            return;
        }

        if (player == friendToAdd) {
            player.sendMessage(Utils.color("&cYou can't remove yourself as a friend!"));
            return;
        }

        friendsMethods.removeFriend(player, friendToAdd);

    }

    @Override
    public String correctArg() {
        return "/friends remove <player_name>";
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
        return null;
    }
}

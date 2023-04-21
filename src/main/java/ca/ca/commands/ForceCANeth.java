package ca.ca.commands;

import java.util.List;

import ca.ca.CA;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import static org.bukkit.Bukkit.getServer;

public class ForceCANeth implements CommandExecutor {
    private Plugin plugin = CA.getPlugin(CA.class);
    String caWorld = (String) plugin.getConfig().get("CoolArenaN_World");
    List<Double> caLoc = (List<Double>) plugin.getConfig().getList("CoolArenaN_Location");
    String prefix = (String) plugin.getConfig().get("Prefix");
    String noPermissionMsg = (String) plugin.getConfig().get("NoPermission");
    String sendingMsg = (String) plugin.getConfig().get("Sending");
    String noUserMsg = (String) plugin.getConfig().get("NoUser");

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("cannot run this command from console!");
        } else {
            // Define Player
            Player player = (Player)sender;

            // Permission Check
            if (!player.hasPermission("core.forceCA")) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermissionMsg));
                return false;
            }

            // Run if there is no arguments [ TP SENDER]
            if (args == null || args.length == 0) {
                player.teleport(new Location(player.getServer().getWorld(caWorld), caLoc.get(0), caLoc.get(1), caLoc.get(2), -180, 0));
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:clear " + player.getName());
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect clear " + player.getName());
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "getkit neth " + player.getName());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + sendingMsg));
                return true;
            }

            // Define Target Player
            Player targetPlayer = player.getServer().getPlayer(args[0]);

            // Run if there is arguments [ TP TARGET]
            if (targetPlayer != null) {
                targetPlayer.teleport(new Location(targetPlayer.getServer().getWorld(caWorld), caLoc.get(0), caLoc.get(1), caLoc.get(2), -180, 0));
                targetPlayer.getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:clear " + targetPlayer.getName());
                targetPlayer.getServer().dispatchCommand(getServer().getConsoleSender(), "effect clear " + targetPlayer.getName());
                targetPlayer.getServer().dispatchCommand(getServer().getConsoleSender(), "getkit neth " + targetPlayer.getName());
                targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + sendingMsg));
                return true;
            } else { // If the target isn't real (rob)
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noUserMsg));
            }
        }
        return true;
    }
}
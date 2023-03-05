package ca.ca.commands;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import ca.ca.CA;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static org.bukkit.Bukkit.getServer;

public class CASpeed implements CommandExecutor {
    private Plugin plugin = CA.getPlugin(CA.class);
    String caWorld = (String) plugin.getConfig().get("CoolArena_World");
    List<Double> caLoc = (List<Double>) plugin.getConfig().getList("CoolArena_Location");
    Integer ExpiryTime = (Integer) plugin.getConfig().get("ExpiryTime");
    String prefix = (String) plugin.getConfig().get("Prefix");
    String noPermissionMsg = (String) plugin.getConfig().get("NoPermission");
    String sendingMsg = (String) plugin.getConfig().get("Sending");
    String noPendingMsg = (String) plugin.getConfig().get("NoPending");
    String expiredMsg = (String) plugin.getConfig().get("Expired");
    String reqSentMsg = (String) plugin.getConfig().get("ReqSentSpeed");
    String clickReqMsg = (String) plugin.getConfig().get("ClickReqSpeed");
    String clickReqHoverMsg = (String) plugin.getConfig().get("ClickReqHover");
    String noUserMsg = (String) plugin.getConfig().get("NoUser");
    String requestExistsMsg = (String) plugin.getConfig().get("RequestExists");
    HashMap<UUID, UUID> Requests = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("cannot run this command from console!");
        } else {
            Player player = (Player)sender;
            if (!player.hasPermission("core.coolArena")) {
                if (args == null || args.length == 0) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermissionMsg));
                    return false;
                }
                if (args[0].equalsIgnoreCase("accept")) {
                    if (this.Requests.get(player.getUniqueId()) != null) {
                        player.teleport(new Location(player.getServer().getWorld(caWorld), caLoc.get(0), caLoc.get(1), caLoc.get(2), 0, 0));
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect clear " + player.getName());
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:clear " + player.getName());
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "kit give Speed " + player.getName());
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect give " + player.getName() + " minecraft:speed 100000 1 false");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + sendingMsg));
                        this.Requests.remove(player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPendingMsg));
                    }
                } else {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPermissionMsg));
                }
                return false;
            }
            if (args == null || args.length == 0) {
                player.teleport(new Location(player.getServer().getWorld(caWorld), caLoc.get(0), caLoc.get(1), caLoc.get(2), 0, 0));
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect clear " + player.getName());
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:clear " + player.getName());
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "kit give Speed " + player.getName());
                player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect give " + player.getName() + " minecraft:speed 100000 1 false");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + sendingMsg));
                return true;
            }
            Player targetPlayer = player.getServer().getPlayer(args[0]);
            if (targetPlayer != null) {
                if(this.Requests.get(targetPlayer.getUniqueId()) != null) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + requestExistsMsg));
                    return true;
                }
                this.Requests.put(targetPlayer.getUniqueId(), player.getUniqueId());
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + reqSentMsg));
                TextComponent message = new TextComponent(ChatColor.translateAlternateColorCodes('&', prefix + clickReqMsg.replace("PLAYER", player.getPlayerListName())));
                message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new Content[] { (Content)new Text(clickReqHoverMsg) }));
                message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/caspeed accept"));
                targetPlayer.spigot().sendMessage((BaseComponent)message);
                // THIS PROBABLY WILL NOT WORK BUT I'M FUCKIN TRYING IT ANYWAY
                new BukkitRunnable() {
                    int countdownLeft = ExpiryTime;
                    @Override
                    public void run() {
                        if (countdownLeft > 0) {
                            countdownLeft--;
                        } else {
                            if (Requests.get(targetPlayer.getUniqueId()) != null) {
                                targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', expiredMsg));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', expiredMsg));
                                Requests.remove(targetPlayer.getUniqueId());
                            }
                            cancel();
                        }
                    }
                }.runTaskTimer(Bukkit.getPluginManager().getPlugin(plugin.getName()), 20, 20);
                // fr if this shit runs imma be tweakin
            } else {
                if (args[0].equalsIgnoreCase("accept")) {
                    if (this.Requests.get(player.getUniqueId()) != null) {
                        player.teleport(new Location(player.getServer().getWorld(caWorld), caLoc.get(0), caLoc.get(1), caLoc.get(2), 0, 0));
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect clear " + player.getName());
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:clear " + player.getName());
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "kit give Speed " + player.getName());
                        player.getServer().dispatchCommand(getServer().getConsoleSender(), "effect give " + player.getName() + " minecraft:speed 100000 1 false");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + sendingMsg));
                        this.Requests.remove(player.getUniqueId());
                    } else {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noPendingMsg));
                    }
                    return true;
                } if (args[0].equalsIgnoreCase("clear") && player.isOp()) {
                    Requests.clear();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCLEARED ALL CASPEED REQUESTS"));
                    return true;
                }
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', prefix + noUserMsg));
            }
        }
        return true;
    }
}
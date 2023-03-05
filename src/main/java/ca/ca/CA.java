package ca.ca;

import ca.ca.commands.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class CA extends JavaPlugin {

    @Override
    public void onEnable() {

        Bukkit.getLogger().info("[StrayCA] Successfully loaded Stray utils.");
        saveDefaultConfig();

        // Commands
        getCommand("coolarena").setExecutor((CommandExecutor)new CoolArena());
        getCommand("cabeast").setExecutor((CommandExecutor)new CABeast());
        getCommand("caspeed").setExecutor((CommandExecutor)new CASpeed());
        getCommand("caaxe").setExecutor((CommandExecutor)new CAAxe());
        getCommand("canone").setExecutor((CommandExecutor)new CANone());
        getCommand("mediaarena").setExecutor((CommandExecutor)new MediaArena());
        getCommand("forceca").setExecutor((CommandExecutor)new ForceCA());
        getCommand("forcecabeast").setExecutor((CommandExecutor)new ForceCABeast());
        getCommand("forcecaspeed").setExecutor((CommandExecutor)new ForceCASpeed());
        getCommand("forcecaaxe").setExecutor((CommandExecutor)new ForceCAAxe());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

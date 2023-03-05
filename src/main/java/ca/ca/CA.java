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
        getCommand("cadiamond").setExecutor((CommandExecutor)new CADiamond());
        getCommand("caneth").setExecutor((CommandExecutor)new CANeth());
        getCommand("canone").setExecutor((CommandExecutor)new CANone());
        getCommand("mediaarena").setExecutor((CommandExecutor)new MediaArena());
        getCommand("forceca").setExecutor((CommandExecutor)new ForceCA());
        getCommand("forcecadiamond").setExecutor((CommandExecutor)new ForceCADiamond());
        getCommand("forcecaneth").setExecutor((CommandExecutor)new ForceCANeth());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

package org.pedrinho.magicantylogout;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.pedrinho.magicantylogout.commands.AntylogoutCommand;
import org.pedrinho.magicantylogout.configuration.Config;
import org.pedrinho.magicantylogout.listeners.CombatListeners;
import org.pedrinho.magicantylogout.listeners.InventoryListener;
import org.pedrinho.magicantylogout.managers.CombatManager;
import org.pedrinho.magicantylogout.managers.ComponentsManager;
import org.pedrinho.magicantylogout.managers.InventoryManager;
import org.pedrinho.magicantylogout.managers.VersionManager;
import org.pedrinho.magicantylogout.tasks.AntylogoutTask;

public final class Main extends JavaPlugin {

    public static String version = "1.0.0";

    public WorldGuardPlugin worldGuard;


    @Override
    public void onEnable() {

        InventoryManager inventoryManager = new InventoryManager();

        worldGuard = (WorldGuardPlugin) getServer().getPluginManager().getPlugin("WorldGuard");
        if (worldGuard == null) {
            getLogger().warning("WorldGuard not found! This plugin requires WorldGuard to work.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        Config config = new Config(this);
        ComponentsManager componentsManager = new ComponentsManager(config);
        CombatManager combatManager = new CombatManager();
        new AntylogoutCommand(this, config, inventoryManager, combatManager, new VersionManager(), componentsManager);

        new InventoryListener(this, new VersionManager(), inventoryManager, config, combatManager, componentsManager);

        new AntylogoutTask(this, combatManager, componentsManager);

        new CombatListeners(this, config, componentsManager, combatManager, WorldGuard.getInstance());
    }

    @Override
    public void onDisable() {
    }

    @Override
    public void reloadConfig() {
        super.reloadConfig();
        saveDefaultConfig();
        FileConfiguration config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }
}

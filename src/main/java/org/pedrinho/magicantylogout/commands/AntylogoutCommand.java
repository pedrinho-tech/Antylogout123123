package org.pedrinho.magicantylogout.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.pedrinho.magicantylogout.configuration.Config;
import org.pedrinho.magicantylogout.managers.CombatManager;
import org.pedrinho.magicantylogout.managers.ComponentsManager;
import org.pedrinho.magicantylogout.managers.InventoryManager;
import org.pedrinho.magicantylogout.managers.VersionManager;
import org.pedrinho.magicantylogout.utils.Chat;

public class AntylogoutCommand implements CommandExecutor {

    private Config config;
    private final InventoryManager inventoryManager;
    private final CombatManager combatManager;
    private final VersionManager versionManager;
    private final ComponentsManager componentsManager;

    public AntylogoutCommand(JavaPlugin plugin, Config config, InventoryManager inventoryManager, CombatManager combatManager, VersionManager versionManager, ComponentsManager componentsManager) {
        plugin.getCommand("antylogout").setExecutor(this);
        this.config = config;
        this.inventoryManager = inventoryManager;
        this.combatManager = combatManager;
        this.versionManager = versionManager;
        this.componentsManager = componentsManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("magicantylogout.manage")) {
                inventoryManager.openMainInventory(player);
            } else {
                player.sendMessage(Chat.color(config.getCommandNopermission()));
            }
        } else {
            if (args.length > 0) {
                switch (args[0]) {
                    case "reload":
                        config.reloadConfig();
                        Bukkit.getLogger().info("Przeladowano pomyslnie plik konfiguracyjny!");
                        break;
                    case "clear":
                        int removedCombats = 0;
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if (combatManager.hasCombat(player1)) {
                                combatManager.removeCombat(player1);
                                removedCombats++;
                            }
                        }
                        Bukkit.getOnlinePlayers().forEach(componentsManager::removeBossBar);
                        Bukkit.getLogger().info("Usunieto antylogout z " + removedCombats);
                        break;
                    case "version":
                        if (versionManager.checkVersion()) {
                            Bukkit.getLogger().info("Posiadasz najnowsza wersje pluginu!");
                        } else {
                            Bukkit.getLogger().warning("Twoja wersja pluginu jest przestarzala!");
                            Bukkit.getLogger().warning("Twoja wersja: " + versionManager.getActualVersion());
                            Bukkit.getLogger().warning("Najnowsza wersja: " + versionManager.getLatestVersion());
                        }
                        break;
                    default:
                        Bukkit.getLogger().severe("Niepoprawna składnia: /antylogout [clear/reload/version]");
                        break;
                }
            } else {
                Bukkit.getLogger().severe("Niepoprawna składnia: /antylogout [clear/reload/version]");
            }
        }
        return false;
    }
}

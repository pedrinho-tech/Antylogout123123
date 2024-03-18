package org.pedrinho.magicantylogout.listeners;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.pedrinho.magicantylogout.configuration.Config;
import org.pedrinho.magicantylogout.managers.CombatManager;
import org.pedrinho.magicantylogout.managers.ComponentsManager;
import org.pedrinho.magicantylogout.managers.InventoryManager;
import org.pedrinho.magicantylogout.managers.VersionManager;
import org.pedrinho.magicantylogout.utils.Chat;

public class InventoryListener implements Listener {

    private final JavaPlugin plugin;
    private final VersionManager versionManager;
    private final InventoryManager inventoryManager;
    private final Config config;
    private final CombatManager combatManager;
    private final ComponentsManager componentsManager;

    public InventoryListener(JavaPlugin plugin, VersionManager versionManager, InventoryManager inventoryManager, Config config, CombatManager combatManager, ComponentsManager componentsManager) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;
        this.versionManager = versionManager;
        this.inventoryManager = inventoryManager;
        this.config = config;
        this.combatManager = combatManager;
        this.componentsManager = componentsManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory().equals(inventoryManager.mainInventory)) {
            Player player = (Player) e.getWhoClicked();

            e.setCancelled(true);
            if (player.hasPermission("magicantylogout.manage")) {
                switch (e.getSlot()) {
                    case 20:
                        if (versionManager.checkVersion()) {
                            player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &aPosiadasz najnowszą wersję pluginu!"));
                        } else {
                            player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &cTwoja wersja &8(&4" + versionManager.getActualVersion() + "&8) jest przestarzała!"));
                            player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &dNajnowsza wersja: &a" + versionManager.getLatestVersion()));
                            player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &fNowszą wersję pluginu pobierzesz na &ddc.magicsolutions.pl&f!"));
                        }
                        player.closeInventory();
                        break;
                    case 22:
                        int removedCombats = 0;
                        for (Player player1 : Bukkit.getOnlinePlayers()) {
                            if (combatManager.hasCombat(player1)) {
                                combatManager.removeCombat(player1);
                                removedCombats++;
                            }
                        }
                        player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &aUsunięto antylogout z &2" + removedCombats + " graczy."));
                        Bukkit.getOnlinePlayers().forEach(componentsManager::removeBossBar);
                        break;
                    case 24:
                        player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &7Przeładowywanie..."));
                        long time = System.currentTimeMillis();
                        config.reloadConfig();
                        player.sendMessage(Chat.color(" &d&lMAGIC&f&lANTYLOGOUT &8» &aPrzeładowano! &a" + (System.currentTimeMillis() - time) + "&ams" ));
                        player.closeInventory();
                        break;
                    /*case 24:
                        player.closeInventory();
                        inventoryManager.openInventory(player);*/
                }
            }
        } else if (e.getClickedInventory().equals(inventoryManager.configurationInventory)) {
            Player player = (Player) e.getWhoClicked();
            e.setCancelled(true);

            if (player.hasPermission("magicantylogout.manage")) {

                FileConfiguration config = plugin.getConfig();
            }


        }
    }
}

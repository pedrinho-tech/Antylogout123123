package org.pedrinho.magicantylogout.tasks;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.pedrinho.magicantylogout.managers.CombatManager;
import org.pedrinho.magicantylogout.managers.ComponentsManager;

public class AntylogoutTask implements Runnable {

    private final CombatManager combatManager;
    private final ComponentsManager componentsManager;

    public AntylogoutTask(JavaPlugin plugin, CombatManager combatManager, ComponentsManager componentsManager) {
        this.combatManager = combatManager;
        this.componentsManager = componentsManager;
        plugin.getServer().getScheduler().runTaskTimerAsynchronously(plugin, this, 0L, 20L);
    }

    public void run() {

        long now = System.currentTimeMillis();

        Bukkit.getOnlinePlayers().forEach(player -> combatManager.getCombat(player).ifPresent(combat -> {
            if (combat > now) {
                long remainingTime = combat - now;

                componentsManager.duringCombat(player, (int) remainingTime);
            } else {
                combatManager.removeCombat(player);

                componentsManager.endCombat(player);
            }
        }));
    }
}

package org.pedrinho.magicantylogout.listeners;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.pedrinho.magicantylogout.configuration.Config;
import org.pedrinho.magicantylogout.managers.CombatManager;
import org.pedrinho.magicantylogout.managers.ComponentsManager;
import org.pedrinho.magicantylogout.utils.Chat;

import java.time.Instant;

public class CombatListeners implements Listener {

    private final Config config;
    private final ComponentsManager componentsManager;
    private final CombatManager combatManager;
    private final WorldGuard worldGuard;

    public CombatListeners(JavaPlugin plugin, Config config, ComponentsManager componentsManager, CombatManager combatManager, WorldGuard worldGuard) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);

        this.config = config;
        this.componentsManager = componentsManager;
        this.combatManager = combatManager;
        this.worldGuard = worldGuard;
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {

        long time = Instant.now().plusSeconds(config.getAntylogoutDuration()).toEpochMilli();

        if (!event.isCancelled()) {
            if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
                Player damager = (Player) event.getDamager();
                Player victim = (Player) event.getEntity();
                if (!damager.hasPermission("magicsolutions.combat")) {
                    combatManager.putCombat(damager, time);
                    componentsManager.startCombat(damager, victim);
                }
                if (!victim.hasPermission("magicsolutions.combat")) {
                    combatManager.putCombat(victim, time);
                    componentsManager.startCombat(victim, damager);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK) && event.getClickedBlock() != null) {

            Material material = event.getClickedBlock().getType();
            Player player = event.getPlayer();

            if (this.combatManager.hasCombat(player)
                    && config.getBlocksBlacklist().contains(material)
                    && !player.hasPermission("magicantylogout.combat")) {
                event.setCancelled(true);
                player.sendMessage(Chat.color(config.getBlocksMessage()));
            }
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        String string = event.getMessage().toLowerCase();
        if (combatManager.hasCombat(player)
                && !config.getWhitelistCommands().contains(string)
                && !player.hasPermission("magicantylogout.combat")) {
            event.setCancelled(true);
            player.sendMessage(Chat.color(config.getCommandsMessage()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (combatManager.hasCombat(player)) {
            combatManager.removeCombat(player);
            componentsManager.endCombat(player);
            if (!player.hasPermission("magicsolutions.combat")) {
                player.setHealth(0.0D);
                if (config.isBroadcastEnabled()) {
                    Bukkit.broadcastMessage(Chat.color(config.getBroadcastLogout().replaceAll("%player%", player.getName())));
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (combatManager.hasCombat(player)) {
            componentsManager.endCombat(player);
            combatManager.removeCombat(player);
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event.getFrom().getBlockX() != event.getTo().getBlockX() || event.getFrom().getBlockZ() != event.getTo().getBlockZ() || event.getFrom().getBlockY() != event.getTo().getBlockY()) {
            Player player = event.getPlayer();
            Location to = event.getTo();
            if (this.worldGuard != null && this.combatManager.hasCombat(player) && !player.hasPermission("magicantylogout.combat")) {
                RegionContainer regionContainer = this.worldGuard.getPlatform().getRegionContainer();
                RegionQuery query = regionContainer.createQuery();

                for (ProtectedRegion protectedRegion : query.getApplicableRegions(BukkitAdapter.adapt(to))) {
                    if (config.getRegionsList().stream().anyMatch((region) -> region.equalsIgnoreCase(protectedRegion.getId()))) {
                        Vector vector = event.getFrom().toVector().subtract(event.getTo().toVector());
                        vector.setY(0).normalize().multiply(1.6D).setY(0.5D);
                        player.setVelocity(vector);
                        break;
                    }
                }

            }
        }
    }
}
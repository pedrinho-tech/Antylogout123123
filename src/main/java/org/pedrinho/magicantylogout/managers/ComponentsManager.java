package org.pedrinho.magicantylogout.managers;

import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.pedrinho.magicantylogout.configuration.Config;
import org.pedrinho.magicantylogout.utils.Chat;
import org.pedrinho.magicantylogout.utils.Time;

import java.util.HashMap;
import java.util.Map;

public class ComponentsManager {

    public Map<Player, BossBar> bossBars = new HashMap<>();
    private final Config config;

    public ComponentsManager(Config config) {
        this.config = config;
    }

    public void sendActionBarDuringCombat(Player player, int time) {
        player.sendActionBar(Chat.color(config.getActionbarDuringCombat().replaceAll("%time%", Time.formatTime(time))));
    }

    public void sendActionBarEndCombat(Player player) {
        player.sendActionBar(Chat.color(config.getActionbarEndCombat()));
    }

    public void sendMessageStartCombat(Player victim, Player damager) {
        victim.sendMessage(Chat.color(config.getMessageStartCombat().replaceAll("%player%", damager.getName())));
    }

    public void sendMessageEndCombat(Player player) {
        player.sendMessage(Chat.color(config.getMessageEndCombat()));
    }

    public void updateBossBar(Player player, int time) {

        String message = Chat.color(config.getBossbarDuringCombat().replaceAll("%time%", Time.formatTime(time)));

        double progress;

        if (config.isBossbarProgressSolid()) {
            double combatSec = (double) time / 1000;
            progress = (combatSec / (double) config.getAntylogoutDuration());
            progress = Math.max(0.0, Math.min(1.0, progress));
        } else {
            progress = 1.0;
        }


        BossBar bossBar = bossBars.get(player);
        if (bossBar == null) {
            bossBar = Bukkit.createBossBar(message, config.getBossbarColor(), config.getBossbarStyle());
            bossBars.put(player, bossBar);
            bossBar.addPlayer(player);
        }

        bossBar.setProgress(progress);
        bossBar.setTitle(message);
        bossBar.setVisible(true);
    }

    public void removeBossBar(Player player) {
        BossBar bossBar = bossBars.remove(player);
        if (bossBar != null) {
            bossBar.setVisible(false);
            bossBar.removeAll();
        }
    }

    public void duringCombat(Player player, int time) {
        if (config.isActionbarEnabled()) {
            sendActionBarDuringCombat(player, time);
        }

        if (config.isBossbarEnabled()) {
            updateBossBar(player, time);
        }
    }

    public void startCombat(Player victim, Player damager) {
        if (config.isMessageEnabled()) {
            sendMessageStartCombat(victim, damager);
        }
    }

    public void endCombat(Player player) {
        if (config.isMessageEnabled()) {
            sendMessageEndCombat(player);
        }

        if (config.isActionbarEnabled()) {
            sendActionBarEndCombat(player);
        }

        if (config.isBossbarEnabled()) {
            removeBossBar(player);
        }
    }
}

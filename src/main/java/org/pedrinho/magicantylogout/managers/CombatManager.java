package org.pedrinho.magicantylogout.managers;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class CombatManager {

    private final Map<UUID, Long> combatList = new HashMap<>();

    public void putCombat(Player player, long time) {
        if (!(player.hasPermission("magicantylogout.combat"))) {
            combatList.put(player.getUniqueId(), time);
        }
    }

    public void removeCombat(Player player) {
        combatList.remove(player.getUniqueId());
    }

    public Optional<Long> getCombat(Player player) {
        return Optional.ofNullable(combatList.get(player.getUniqueId()));
    }

    public boolean hasCombat(Player player) {
        return getCombat(player).isPresent();
    }
}

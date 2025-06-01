package com.mythictokens.cooldown;

import com.mythictokens.MythicTokensPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final MythicTokensPlugin plugin;
    private final Map<UUID, Map<String, Long>> cooldowns;

    public CooldownManager(MythicTokensPlugin plugin) {
        this.plugin = plugin;
        this.cooldowns = new HashMap<>();
    }

    public void setCooldown(Player player, String ability, int seconds) {
        UUID uuid = player.getUniqueId();
        cooldowns.computeIfAbsent(uuid, k -> new HashMap<>()).put(ability, System.currentTimeMillis() + (seconds * 1000L));
    }

    public boolean isOnCooldown(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid) || !cooldowns.get(uuid).containsKey(ability)) {
            return false;
        }
        long expiry = cooldowns.get(uuid).get(ability);
        if (System.currentTimeMillis() > expiry) {
            cooldowns.get(uuid).remove(ability);
            return false;
        }
        return true;
    }

    public long getRemainingCooldown(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid) || !cooldowns.get(uuid).containsKey(ability)) {
            return 0;
        }
        long expiry = cooldowns.get(uuid).get(ability);
        long remaining = expiry - System.currentTimeMillis();
        return remaining > 0 ? remaining / 1000 : 0;
    }
}
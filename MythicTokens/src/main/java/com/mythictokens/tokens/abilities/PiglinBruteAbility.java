package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PiglinBruteAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public PiglinBruteAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "piglinbrute")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "piglinbrute");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Brute Fury on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(4, 4, 4)) {
                if (entity instanceof Player target && entity != player) {
                    target.damage(6);
                }
            }
            ParticleUtils.spawnBruteParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_PIGLIN_BRUTE_ANGRY", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "piglinbrute", 15);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Brute Fury activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Piglin Brute ability: " + e.getMessage());
        }
    }
}
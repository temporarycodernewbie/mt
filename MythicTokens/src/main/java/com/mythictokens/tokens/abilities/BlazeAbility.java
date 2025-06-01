package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class BlazeAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public BlazeAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "blaze")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "blaze");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Flame Burst on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player target && entity != player) {
                    target.setFireTicks(60);
                    target.damage(4);
                }
            }
            ParticleUtils.spawnFlameParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_BLAZE_SHOOT", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "blaze", 20);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Flame Burst activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Blaze ability: " + e.getMessage());
        }
    }
}
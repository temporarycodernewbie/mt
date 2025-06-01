package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class DrownedAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public DrownedAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "drowned")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "drowned");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Trident Strike on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(10, 5, 10)) {
                if (entity instanceof Player target && entity != player) {
                    target.damage(6);
                    target.setVelocity(new Vector(0, 0.5, 0));
                }
            }
            ParticleUtils.spawnWaterParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_DROWNED_SHOOT", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "drowned", 25);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Trident Strike activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Drowned ability: " + e.getMessage());
        }
    }
}
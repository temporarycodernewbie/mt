package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SpiderAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public SpiderAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "spider")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "spider");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Web Snare on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player target && entity != player) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 100, 2));
                }
            }
            ParticleUtils.spawnWebParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_SPIDER_AMBIENT", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "spider", 15);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Web Snare activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Spider ability: " + e.getMessage());
        }
    }
}
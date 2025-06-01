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

public class SnowGolemAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public SnowGolemAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "snowgolem")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "snowgolem");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Snow Barrage on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(6, 6, 6)) {
                if (entity instanceof Player target && entity != player) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 80, 1));
                    target.damage(2);
                }
            }
            ParticleUtils.spawnSnowParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_SNOWBALL_THROW", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "snowgolem", 15);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Snow Barrage activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Snow Golem ability: " + e.getMessage());
        }
    }
}
package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class StriderAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public StriderAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "strider")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "strider");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Lava Glide on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 200, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 200, 1));
            ParticleUtils.spawnLavaParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_STRIDER_AMBIENT", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "strider", 20);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Lava Glide activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Strider ability: " + e.getMessage());
        }
    }
}
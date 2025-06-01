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

public class WardenAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public WardenAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "warden")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "warden");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Sonic Despair on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(10, 5, 10)) {
                if (entity instanceof Player target && entity != player) {
                    target.addPotionEffect(new PotionEffect(PotionEffectType.DARKNESS, 100, 1));
                    target.damage(5);
                }
            }
            ParticleUtils.spawnSonicBoom(player.getWorld(), player.getLocation());
            SoundUtils.playSound(player, "ENTITY_WARDEN_SONIC_BOOM", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "warden", 30);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Sonic Despair activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Warden ability: " + e.getMessage());
        }
    }
}

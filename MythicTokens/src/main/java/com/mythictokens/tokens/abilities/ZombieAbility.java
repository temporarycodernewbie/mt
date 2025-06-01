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

public class ZombieAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public ZombieAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "zombie")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "zombie");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Undead Vitality on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
            player.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 100, 1));
            ParticleUtils.spawnHealingParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_ZOMBIE_AMBIENT", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "zombie", 30);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Undead Vitality activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Zombie ability: " + e.getMessage());
        }
    }
}
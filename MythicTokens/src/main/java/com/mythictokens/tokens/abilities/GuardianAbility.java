package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class GuardianAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public GuardianAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "guardian")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "guardian");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Prismarine Ray on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(10, 5, 10)) {
                if (entity instanceof Player target && entity != player) {
                    target.damage(4);
                    target.addPotionEffect(new org.bukkit.potion.PotionEffect(org.bukkit.potion.PotionEffectType.SLOWNESS, 100, 1));
                }
            }
            ParticleUtils.spawnPrismarineParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_GUARDIAN_ATTACK", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "guardian", 20);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Prismarine Ray activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Guardian ability: " + e.getMessage());
        }
    }
}
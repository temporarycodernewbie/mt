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
import org.bukkit.util.Vector;

public class IronGolemAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public IronGolemAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "irongolem") || !sneaking) {
            long remaining = cooldownManager.getRemainingCooldown(player, "irongolem");
            if (cooldownManager.isOnCooldown(player, "irongolem")) {
                plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Titan Slam on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            }
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player target && entity != player) {
                    target.setVelocity(new Vector(0, 1, 0));
                    target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 60, 1));
                    target.damage(3);
                }
            }
            ParticleUtils.spawnIronParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_IRON_GOLEM_ATTACK", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "irongolem", 25);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Titan Slam activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Iron Golem ability: " + e.getMessage());
        }
    }
}
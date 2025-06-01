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

public class RavagerAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public RavagerAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "ravager")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "ravager");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Ground Slam on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
                if (entity instanceof Player target && entity != player) {
                    target.damage(5);
                    target.setVelocity(new Vector(0, 0.7, 0));
                }
            }
            ParticleUtils.spawnGroundSlamParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_RAVAGER_ROAR", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "ravager", 20);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Ground Slam activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Ravager ability: " + e.getMessage());
        }
    }
}
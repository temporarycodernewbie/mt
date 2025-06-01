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

public class HoglinAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public HoglinAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "hoglin")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "hoglin");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Hog Rush on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            player.setVelocity(player.getLocation().getDirection().multiply(2));
            for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
                if (entity instanceof Player target && entity != player) {
                    target.damage(5);
                    target.setVelocity(new Vector(0, 0.5, 0));
                }
            }
            ParticleUtils.spawnChargeParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_HOGLIN_ATTACK", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "hoglin", 15);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Hog Rush activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Hoglin ability: " + e.getMessage());
        }
    }
}
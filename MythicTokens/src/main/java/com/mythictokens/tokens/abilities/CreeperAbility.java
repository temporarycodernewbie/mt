package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CreeperAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public CreeperAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "creeper")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "creeper");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Explosive Core on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            Location location = player.getLocation();
            location.getWorld().createExplosion(location, 3.0f, false, false);
            ParticleUtils.spawnExplosionParticles(location);
            SoundUtils.playSound(player, "ENTITY_CREEPER_PRIMED", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "creeper", 30);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Explosive Core activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Creeper ability: " + e.getMessage());
        }
    }
}
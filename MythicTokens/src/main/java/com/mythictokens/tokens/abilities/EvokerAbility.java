package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class EvokerAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public EvokerAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "evoker")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "evoker");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Fang Ambush on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            for (Entity entity : player.getNearbyEntities(8, 4, 8)) {
                if (entity instanceof Player target && entity != player) {
                    target.damage(5);
                }
            }
            ParticleUtils.spawnFangParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_EVOKER_FANGS", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "evoker", 25);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Fang Ambush activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Evoker ability: " + e.getMessage());
        }
    }
}
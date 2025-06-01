package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

public class SlimeAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public SlimeAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "slime")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "slime");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Bounce Boost on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            player.setVelocity(new Vector(0, 2, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (player.isOnGround()) {
                        for (Entity entity : player.getNearbyEntities(3, 3, 3)) {
                            if (entity instanceof Player target && entity != player) {
                                target.setVelocity(new Vector(0, 0.5, 0));
                            }
                        }
                        ParticleUtils.spawnSlimeParticles(player.getLocation());
                        SoundUtils.playSound(player, "ENTITY_SLIME_JUMP", 1.0f, 1.0f);
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 20, 1);
            cooldownManager.setCooldown(player, "slime", 15);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Bounce Boost activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Slime ability: " + e.getMessage());
        }
    }
}
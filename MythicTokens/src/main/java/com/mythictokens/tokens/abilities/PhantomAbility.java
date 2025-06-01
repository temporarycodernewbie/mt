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

public class PhantomAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public PhantomAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "phantom")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "phantom");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Sky Assault on cooldown: <aqua>" + remaining + "s</aqua></red>"));
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
                                target.setVelocity(new Vector(0, 1, 0));
                                target.damage(4);
                            }
                        }
                        ParticleUtils.spawnDiveParticles(player.getLocation());
                        SoundUtils.playSound(player, "ENTITY_PHANTOM_FLAP", 1.0f, 1.0f);
                        cancel();
                    }
                }
            }.runTaskTimer(plugin, 20, 1);
            cooldownManager.setCooldown(player, "phantom", 20);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Sky Assault activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Phantom ability: " + e.getMessage());
        }
    }
}
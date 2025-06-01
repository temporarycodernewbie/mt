package com.mythictokens.tokens.abilities;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.cooldown.CooldownManager;
import com.mythictokens.tokens.TokenAbility;
import com.mythictokens.utils.GuiUtils;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class EndermanAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public EndermanAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "enderman")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "enderman");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Shadow Slip on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            if (sneaking) {
                GuiUtils.openTeleportGui(player, plugin.getTokenManager());
            } else {
                Location target = player.getTargetBlock(null, 10).getLocation();
                target.setYaw(player.getLocation().getYaw());
                target.setPitch(player.getLocation().getPitch());
                player.teleport(target);
                ParticleUtils.spawnEnderParticles(player.getLocation());
                SoundUtils.playSound(player, "ENTITY_ENDERMAN_TELEPORT", 1.0f, 1.0f);
                cooldownManager.setCooldown(player, "enderman", 10);
                plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Shadow Slip activated!</green>"));
            }
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Enderman ability: " + e.getMessage());
        }
    }
}
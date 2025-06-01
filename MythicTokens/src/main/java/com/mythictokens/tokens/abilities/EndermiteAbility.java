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

public class EndermiteAbility extends TokenAbility {
    private final CooldownManager cooldownManager;

    public EndermiteAbility(MythicTokensPlugin plugin) {
        super(plugin);
        this.cooldownManager = new CooldownManager(plugin);
    }

    @Override
    public void activate(Player player, boolean sneaking) {
        if (cooldownManager.isOnCooldown(player, "endermite")) {
            long remaining = cooldownManager.getRemainingCooldown(player, "endermite");
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<red>Burrow Blitz on cooldown: <aqua>" + remaining + "s</aqua></red>"));
            return;
        }
        try {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, 2));
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 100, 1));
            ParticleUtils.spawnEnderParticles(player.getLocation());
            SoundUtils.playSound(player, "ENTITY_ENDERMITE_AMBIENT", 1.0f, 1.0f);
            cooldownManager.setCooldown(player, "endermite", 20);
            plugin.getAdventure().player(player).sendActionBar(MiniMessage.miniMessage().deserialize("<green>Burrow Blitz activated!</green>"));
        } catch (Exception e) {
            plugin.getLogger().severe("Error activating Endermite ability: " + e.getMessage());
        }
    }
}
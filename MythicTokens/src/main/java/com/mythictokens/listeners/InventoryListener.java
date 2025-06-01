package com.mythictokens.listeners;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.tokens.TokenManager;
import com.mythictokens.utils.ParticleUtils;
import com.mythictokens.utils.SoundUtils;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {
    private final MythicTokensPlugin plugin;
    private final TokenManager tokenManager;

    public InventoryListener(MythicTokensPlugin plugin, TokenManager tokenManager) {
        this.plugin = plugin;
        this.tokenManager = tokenManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        InventoryView view = event.getView();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null) return;

        String title = view.getTitle();
        if (title.equals("Mythic Tokens")) {
            event.setCancelled(true);
            String tokenType = tokenManager.getTokenType(clickedItem);
            if (tokenType != null) {
                tokenManager.giveToken(player.getName(), tokenType);
                plugin.getAdventure().player(player).sendMessage(MiniMessage.miniMessage().deserialize("<green>Equipped <aqua>" + tokenManager.getAbilityName(tokenType) + "</aqua> token!</green>"));
                player.closeInventory();
            }
        } else if (title.equals("Teleport Menu")) {
            event.setCancelled(true);
            if (clickedItem.getType() == org.bukkit.Material.PLAYER_HEAD) {
                String targetName = clickedItem.getItemMeta().getDisplayName();
                Player target = player.getServer().getPlayer(targetName);
                if (target != null) {
                    player.teleport(target.getLocation());
                    ParticleUtils.spawnEnderParticles(player.getLocation());
                    SoundUtils.playSound(player, "ENTITY_ENDERMAN_TELEPORT", 1.0f, 1.0f);
                    plugin.getAdventure().player(player).sendMessage(MiniMessage.miniMessage().deserialize("<green>Teleported to <aqua>" + targetName + "</aqua>!</green>"));
                    player.closeInventory();
                } else {
                    plugin.getAdventure().player(player).sendMessage(MiniMessage.miniMessage().deserialize("<red>Player <aqua>" + targetName + "</aqua> not found!</red>"));
                }
            }
        }
    }
}
package com.mythictokens.listeners;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.tokens.TokenManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {
    private final MythicTokensPlugin plugin;
    private final TokenManager tokenManager;

    public PlayerListener(MythicTokensPlugin plugin, TokenManager tokenManager) {
        this.plugin = plugin;
        this.tokenManager = tokenManager;
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            tokenManager.assignRandomToken(player);
        } else {
            // Ensure they still have it after relog
            tokenManager.ensurePlayerHasToken(player);
        }
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent event) {
        if (tokenManager.isTokenItem(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getCurrentItem() != null && tokenManager.isTokenItem(event.getCurrentItem())) {
            if (event.getRawSlot() >= event.getView().getTopInventory().getSize()) return; // Allow hotbar use
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        event.getDrops().removeIf(tokenManager::isTokenItem);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> {
            tokenManager.ensurePlayerHasToken(event.getPlayer());
        }, 20L); // Delay 1 sec after respawn
    }
}

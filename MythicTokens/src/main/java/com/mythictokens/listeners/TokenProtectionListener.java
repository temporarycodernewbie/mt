package com.mythictokens.listeners;

import com.mythictokens.tokens.TokenManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class TokenProtectionListener implements Listener {

    private final TokenManager tokenManager;

    public TokenProtectionListener(TokenManager tokenManager) {
        this.tokenManager = tokenManager;
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (tokenManager.isTokenItem(event.getItemDrop().getItemStack())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        ItemStack item = event.getCurrentItem();
        if (tokenManager.isTokenItem(item)) {
            // Prevent placing in containers
            if (!event.getClickedInventory().equals(player.getInventory())) {
                event.setCancelled(true);
            }
            // Prevent placing in special blocks like anvils or crafting
            if (event.getView().getTopInventory().getType() != InventoryType.CRAFTING &&
                    event.getView().getTopInventory().getType() != InventoryType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        for (int slot : event.getRawSlots()) {
            if (slot < event.getView().getTopInventory().getSize()) {
                ItemStack dragged = event.getOldCursor();
                if (tokenManager.isTokenItem(dragged)) {
                    event.setCancelled(true);
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // Remove token from dropped items so it stays with player
        event.getDrops().removeIf(tokenManager::isTokenItem);
    }
}

package com.mythictokens.utils;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.tokens.TokenManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiUtils {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();

    public static void openTokenGui(Player player, TokenManager tokenManager) {
        Component title = Component.text("Mythic Tokens");
        Inventory inventory = Bukkit.createInventory(null, 27, miniMessage.serialize(title));
        for (String token : tokenManager.getAvailableTokens()) {
            ItemStack item = new ItemStack(Material.NETHER_STAR);
            ItemMeta meta = item.getItemMeta();
            if (meta != null) {
                Component displayName = Component.text(tokenManager.getAbilityName(token));
                meta.setDisplayName(miniMessage.serialize(displayName));
                List<String> lore = new ArrayList<>();
                lore.add(miniMessage.serialize(Component.text("Token: " + token)));
                lore.add(miniMessage.serialize(Component.text("Click to equip!")));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
            inventory.addItem(item);
        }
        player.openInventory(inventory);
    }

    public static void openTeleportGui(Player player, TokenManager tokenManager) {
        Component title = Component.text("Teleport Menu");
        Inventory inventory = Bukkit.createInventory(null, 9, miniMessage.serialize(title));
        for (Player target : player.getServer().getOnlinePlayers()) {
            if (target != player) {
                ItemStack item = new ItemStack(Material.PLAYER_HEAD);
                ItemMeta meta = item.getItemMeta();
                if (meta != null) {
                    Component displayName = Component.text(target.getName());
                    meta.setDisplayName(miniMessage.serialize(displayName));
                    List<String> lore = new ArrayList<>();
                    lore.add(miniMessage.serialize(Component.text("Click to teleport!")));
                    meta.setLore(lore);
                    item.setItemMeta(meta);
                }
                inventory.addItem(item);
            }
        }
        player.openInventory(inventory);
    }
}
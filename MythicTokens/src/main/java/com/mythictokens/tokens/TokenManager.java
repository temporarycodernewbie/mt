package com.mythictokens.tokens;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.data.PlayerData;
import com.mythictokens.tokens.abilities.*;
import com.mythictokens.utils.ConfigManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class TokenManager {
    private final MythicTokensPlugin plugin;
    private final ConfigManager configManager;
    private final PlayerData playerData;
    private final Map<String, TokenAbility> abilities;
    private final Map<UUID, String> playerTokens;
    private final List<String> availableTokens;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public TokenManager(MythicTokensPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.playerData = new PlayerData(plugin);
        this.abilities = new HashMap<>();
        this.playerTokens = new HashMap<>();
        this.availableTokens = new ArrayList<>();
        initializeAbilities();
        loadTokens();
    }

    private void initializeAbilities() {
        abilities.put("warden", new WardenAbility(plugin));
        abilities.put("enderman", new EndermanAbility(plugin));
        abilities.put("zombie", new ZombieAbility(plugin));
        abilities.put("creeper", new CreeperAbility(plugin));
        abilities.put("phantom", new PhantomAbility(plugin));
        abilities.put("blaze", new BlazeAbility(plugin));
        abilities.put("evoker", new EvokerAbility(plugin));
        abilities.put("guardian", new GuardianAbility(plugin));
        abilities.put("piglinbrute", new PiglinBruteAbility(plugin));
        abilities.put("shulker", new ShulkerAbility(plugin));
        abilities.put("witherskeleton", new WitherSkeletonAbility(plugin));
        abilities.put("ravager", new RavagerAbility(plugin));
        abilities.put("spider", new SpiderAbility(plugin));
        abilities.put("endermite", new EndermiteAbility(plugin));
        abilities.put("drowned", new DrownedAbility(plugin));
        abilities.put("hoglin", new HoglinAbility(plugin));
        abilities.put("strider", new StriderAbility(plugin));
        abilities.put("irongolem", new IronGolemAbility(plugin));
        abilities.put("snowgolem", new SnowGolemAbility(plugin));
        abilities.put("slime", new SlimeAbility(plugin));
    }

    private void loadTokens() {
        List<String> enabledTokens = configManager.getTokensConfig().getStringList("enabled-tokens");
        for (String token : enabledTokens) {
            if (abilities.containsKey(token.toLowerCase())) {
                availableTokens.add(token.toLowerCase());
            }
        }
        if (availableTokens.isEmpty()) {
            availableTokens.addAll(abilities.keySet());
            configManager.getTokensConfig().set("enabled-tokens", new ArrayList<>(abilities.keySet()));
            configManager.saveTokensConfig();
        }
    }

    public void assignRandomToken(Player player) {
        if (availableTokens.isEmpty()) {
            plugin.getAdventure().player(player).sendMessage(miniMessage.deserialize("<red>No tokens available!</red>"));
            return;
        }
        String token = availableTokens.get(new Random().nextInt(availableTokens.size()));
        playerTokens.put(player.getUniqueId(), token);
        playerData.savePlayerToken(player, token);
        player.getInventory().addItem(getTokenItem(token));
        plugin.getAdventure().player(player).sendMessage(miniMessage.deserialize("<green>Received <aqua>" + getAbilityName(token) + "</aqua> token!</green>"));
    }

    public void ensurePlayerHasToken(Player player) {
        UUID uuid = player.getUniqueId();
        if (!playerTokens.containsKey(uuid)) {
            assignRandomToken(player);
            return;
        }
        String tokenName = playerTokens.get(uuid);
        boolean hasToken = player.getInventory().containsAtLeast(getTokenItem(tokenName), 1);
        if (!hasToken) {
            player.getInventory().addItem(getTokenItem(tokenName));
        }
    }

    public boolean hasToken(Player player) {
        return playerTokens.containsKey(player.getUniqueId());
    }

    public String getTokenType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        ItemMeta meta = item.getItemMeta();
        if (meta.hasLore()) {
            List<String> lore = meta.getLore();
            if (lore != null && !lore.isEmpty()) {
                String loreLine = miniMessage.stripTags(lore.get(0));
                if (loreLine.startsWith("Token: ")) {
                    return loreLine.replace("Token: ", "").toLowerCase();
                }
            }
        }
        return null;
    }

    public boolean isTokenItem(ItemStack item) {
        return getTokenType(item) != null;
    }

    public ItemStack getTokenItem(String tokenType) {
        ItemStack item = new ItemStack(Material.NETHER_STAR);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            Component displayName = Component.text(getAbilityName(tokenType));
            meta.setDisplayName(miniMessage.serialize(displayName));
            List<String> lore = new ArrayList<>();
            lore.add(miniMessage.serialize(Component.text("Token: " + tokenType)));
            lore.add(miniMessage.serialize(Component.text("Right-click to activate!")));
            meta.setLore(lore);
            item.setItemMeta(meta);
        }
        return item;
    }

    public void activateTokenAbility(Player player, String tokenType, boolean sneaking) {
        TokenAbility ability = abilities.get(tokenType.toLowerCase());
        if (ability != null) {
            ability.activate(player, sneaking);
        } else {
            plugin.getAdventure().player(player).sendMessage(miniMessage.deserialize("<red>Invalid token type!</red>"));
        }
    }

    public List<String> getAvailableTokens() {
        return new ArrayList<>(availableTokens);
    }

    public String getAbilityName(String tokenType) {
        return switch (tokenType.toLowerCase()) {
            case "warden" -> "Sonic Despair";
            case "enderman" -> "Shadow Slip";
            case "zombie" -> "Undead Vitality";
            case "creeper" -> "Explosive Core";
            case "phantom" -> "Sky Assault";
            case "blaze" -> "Flame Burst";
            case "evoker" -> "Fang Ambush";
            case "guardian" -> "Prismarine Ray";
            case "piglinbrute" -> "Brute Fury";
            case "shulker" -> "Levitation Strike";
            case "witherskeleton" -> "Dark Plague";
            case "ravager" -> "Ground Slam";
            case "spider" -> "Web Snare";
            case "endermite" -> "Burrow Blitz";
            case "drowned" -> "Trident Strike";
            case "hoglin" -> "Hog Rush";
            case "strider" -> "Lava Glide";
            case "irongolem" -> "Titan Slam";
            case "snowgolem" -> "Snow Barrage";
            case "slime" -> "Bounce Boost";
            default -> "Unknown Ability";
        };
    }

    public void giveToken(String playerName, String tokenType) {
        Player player = plugin.getServer().getPlayer(playerName);
        if (player == null) {
            plugin.getLogger().warning("Player " + playerName + " not found for token assignment!");
            return;
        }
        if (!abilities.containsKey(tokenType.toLowerCase())) {
            plugin.getLogger().warning("Invalid token type: " + tokenType);
            return;
        }
        playerTokens.put(player.getUniqueId(), tokenType.toLowerCase());
        playerData.savePlayerToken(player, tokenType.toLowerCase());
        player.getInventory().addItem(getTokenItem(tokenType.toLowerCase()));
        plugin.getAdventure().player(player).sendMessage(miniMessage.deserialize("<green>Received <aqua>" + getAbilityName(tokenType) + "</aqua> token!</green>"));
    }

    public void removeToken(String playerName) {
        Player player = plugin.getServer().getPlayer(playerName);
        if (player == null) {
            plugin.getLogger().warning("Player " + playerName + " not found for token removal!");
            return;
        }
        playerTokens.remove(player.getUniqueId());
        playerData.savePlayerToken(player, null);
        player.getInventory().clear();
        plugin.getAdventure().player(player).sendMessage(miniMessage.deserialize("<green>Token removed!</green>"));
    }

    public void toggleToken(String tokenType) {
        tokenType = tokenType.toLowerCase();
        List<String> enabledTokens = configManager.getTokensConfig().getStringList("enabled-tokens");
        if (enabledTokens.contains(tokenType)) {
            enabledTokens.remove(tokenType);
            availableTokens.remove(tokenType);
            plugin.getLogger().info("Disabled token: " + tokenType);
        } else if (abilities.containsKey(tokenType)) {
            enabledTokens.add(tokenType);
            availableTokens.add(tokenType);
            plugin.getLogger().info("Enabled token: " + tokenType);
        } else {
            plugin.getLogger().warning("Invalid token type: " + tokenType);
            return;
        }
        configManager.getTokensConfig().set("enabled-tokens", enabledTokens);
        configManager.saveTokensConfig();
    }
}

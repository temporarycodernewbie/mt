package com.mythictokens;

import com.mythictokens.commands.TokenCommand;
import com.mythictokens.listeners.InventoryListener;
import com.mythictokens.listeners.PlayerListener;
import com.mythictokens.listeners.TokenProtectionListener;
import com.mythictokens.tokens.TokenManager;
import com.mythictokens.utils.ConfigManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;

public final class MythicTokensPlugin extends JavaPlugin {
    private ConfigManager configManager;
    private TokenManager tokenManager;
    private BukkitAudiences adventure;

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        saveDefaultConfig();
        configManager = new ConfigManager(this);
        tokenManager = new TokenManager(this, configManager);

        getCommand("token").setExecutor(new TokenCommand(this, tokenManager));

        getServer().getPluginManager().registerEvents(new PlayerListener(this, tokenManager), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this, tokenManager), this);
        getServer().getPluginManager().registerEvents(new TokenProtectionListener(tokenManager), this); // ✅ added here

        new Metrics(this, 12345);
        getLogger().info("MythicTokens v1.0 has been enabled!");
    }

    @Override
    public void onDisable() {
        if (this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
        getLogger().info("MythicTokens v1.0 has been disabled!");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public TokenManager getTokenManager() {
        return tokenManager;
    }

    public BukkitAudiences getAdventure() {
        if (this.adventure == null) {
            throw new IllegalStateException("Adventure not initialized!");
        }
        return this.adventure;
    }
}

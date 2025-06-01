package com.mythictokens.utils;

import com.mythictokens.MythicTokensPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    private final MythicTokensPlugin plugin;
    private FileConfiguration config;
    private FileConfiguration tokensConfig;
    private FileConfiguration langConfig;
    private FileConfiguration guiConfig;
    private File configFile;
    private File tokensFile;
    private File langFile;
    private File guiFile;

    public ConfigManager(MythicTokensPlugin plugin) {
        this.plugin = plugin;
        loadConfigs();
    }

    public void loadConfigs() {
        // Main config
        configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);

        // Tokens config
        tokensFile = new File(plugin.getDataFolder(), "tokens.yml");
        if (!tokensFile.exists()) {
            plugin.saveResource("tokens.yml", false);
        }
        tokensConfig = YamlConfiguration.loadConfiguration(tokensFile);

        // Lang config
        langFile = new File(plugin.getDataFolder(), "lang.yml");
        if (!langFile.exists()) {
            plugin.saveResource("lang.yml", false);
        }
        langConfig = YamlConfiguration.loadConfiguration(langFile);

        // GUI config
        guiFile = new File(plugin.getDataFolder(), "gui.yml");
        if (!guiFile.exists()) {
            plugin.saveResource("gui.yml", false);
        }
        guiConfig = YamlConfiguration.loadConfiguration(guiFile);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getTokensConfig() {
        return tokensConfig;
    }

    public FileConfiguration getLangConfig() {
        return langConfig;
    }

    public FileConfiguration getGuiConfig() {
        return guiConfig;
    }

    public void saveTokensConfig() {
        try {
            tokensConfig.save(tokensFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save tokens.yml: " + e.getMessage());
        }
    }
}
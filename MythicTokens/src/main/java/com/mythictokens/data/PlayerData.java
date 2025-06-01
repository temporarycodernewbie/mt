package com.mythictokens.data;

import com.mythictokens.MythicTokensPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class PlayerData {
    private final MythicTokensPlugin plugin;
    private static File dataFile;
    private static FileConfiguration dataConfig;

    public PlayerData(MythicTokensPlugin plugin) {
        this.plugin = plugin;
        init();
    }

    private void init() {
        dataFile = new File(plugin.getDataFolder(), "playerdata.yml");
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                plugin.getLogger().severe("Failed to create playerdata.yml: " + e.getMessage());
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }

    public void savePlayerToken(Player player, String token) {
        dataConfig.set(player.getUniqueId().toString() + ".token", token);
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Could not save playerdata.yml: " + e.getMessage());
        }
    }

    public String getPlayerToken(Player player) {
        return dataConfig.getString(player.getUniqueId().toString() + ".token");
    }
}
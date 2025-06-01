package com.mythictokens.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundUtils {
    public static void playSound(Location location, String sound, float volume, float pitch) {
        try {
            location.getWorld().playSound(location, Sound.valueOf(sound), volume, pitch);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to play sound " + sound + ": " + e.getMessage());
        }
    }

    public static void playSound(Player player, String sound, float volume, float pitch) {
        try {
            player.playSound(player.getLocation(), Sound.valueOf(sound), volume, pitch);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to play sound " + sound + " for " + player.getName() + ": " + e.getMessage());
        }
    }
}
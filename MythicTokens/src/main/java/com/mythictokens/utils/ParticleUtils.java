package com.mythictokens.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.inventory.ItemStack;

public class ParticleUtils {
    public static void spawnSonicBoom(World world, Location start) {
        try {
            world.spawnParticle(Particle.SONIC_BOOM, start, 5, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn SONIC_BOOM particles: " + e.getMessage());
        }
    }

    public static void spawnEnderParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.PORTAL, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn PORTAL particles: " + e.getMessage());
        }
    }

    public static void spawnHealingParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.HAPPY_VILLAGER, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn HAPPY_VILLAGER particles: " + e.getMessage());
        }
    }

    public static void spawnExplosionParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.POOF, location, 5, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn POOF particles: " + e.getMessage());
        }
    }

    public static void spawnDiveParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.CLOUD, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn CLOUD particles: " + e.getMessage());
        }
    }

    public static void spawnFlameParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.FLAME, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn FLAME particles: " + e.getMessage());
        }
    }

    public static void spawnFangParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.WITCH, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn WITCH particles: " + e.getMessage());
        }
    }

    public static void spawnPrismarineParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.BUBBLE, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn BUBBLE particles: " + e.getMessage());
        }
    }

    public static void spawnBruteParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.CRIT, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn CRIT particles: " + e.getMessage());
        }
    }

    public static void spawnLevitationParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.END_ROD, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn END_ROD particles: " + e.getMessage());
        }
    }

    public static void spawnWitherParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.LARGE_SMOKE, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn LARGE_SMOKE particles: " + e.getMessage());
        }
    }

    public static void spawnGroundSlamParticles(Location location) {
        try {
            BlockData dust = Material.DIRT.createBlockData();
            location.getWorld().spawnParticle(Particle.FALLING_DUST, location, 10, 0.3, 0.3, 0.3, 0, dust);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn FALLING_DUST particles: " + e.getMessage());
        }
    }

    public static void spawnWebParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.EFFECT, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn EFFECT particles: " + e.getMessage());
        }
    }

    public static void spawnWaterParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.SPLASH, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn SPLASH particles: " + e.getMessage());
        }
    }

    public static void spawnChargeParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.CLOUD, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn CLOUD particles: " + e.getMessage());
        }
    }

    public static void spawnLavaParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.LAVA, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn LAVA particles: " + e.getMessage());
        }
    }

    public static void spawnIronParticles(Location location) {
        try {
            BlockData iron = Material.IRON_BLOCK.createBlockData();
            location.getWorld().spawnParticle(Particle.FALLING_DUST, location, 10, 0.3, 0.3, 0.3, 0, iron);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn FALLING_DUST (iron) particles: " + e.getMessage());
        }
    }

    public static void spawnSnowParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.SNOWFLAKE, location, 10, 0.3, 0.3, 0.3, 0);
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn SNOWFLAKE particles: " + e.getMessage());
        }
    }

    public static void spawnSlimeParticles(Location location) {
        try {
            location.getWorld().spawnParticle(Particle.ITEM, location, 10, 0.3, 0.3, 0.3, 0,
                    new ItemStack(Material.SLIME_BALL));
        } catch (Exception e) {
            Bukkit.getLogger().warning("Failed to spawn ITEM (slime ball) particles: " + e.getMessage());
        }
    }
}

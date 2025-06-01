package com.mythictokens.tokens;

import com.mythictokens.MythicTokensPlugin;
import org.bukkit.entity.Player;

public abstract class TokenAbility {
    protected final MythicTokensPlugin plugin;

    public TokenAbility(MythicTokensPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void activate(Player player, boolean sneaking);
}
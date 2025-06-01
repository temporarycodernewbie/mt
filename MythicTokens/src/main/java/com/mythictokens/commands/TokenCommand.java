package com.mythictokens.commands;

import com.mythictokens.MythicTokensPlugin;
import com.mythictokens.tokens.TokenManager;
import com.mythictokens.utils.GuiUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokenCommand implements CommandExecutor {
    private final MythicTokensPlugin plugin;
    private final TokenManager tokenManager;
    private final MiniMessage miniMessage = MiniMessage.miniMessage();

    public TokenCommand(MythicTokensPlugin plugin, TokenManager tokenManager) {
        this.plugin = plugin;
        this.tokenManager = tokenManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) && args.length == 0) {
            plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<red>Only players can use this command without arguments!</red>"));
            return true;
        }

        if (args.length == 0) {
            Player player = (Player) sender;
            GuiUtils.openTokenGui(player, tokenManager);
            return true;
        }

        if (args[0].equalsIgnoreCase("give") && args.length == 3) {
            if (!sender.hasPermission("mythictokens.admin")) {
                plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<red>No permission!</red>"));
                return true;
            }
            tokenManager.giveToken(args[1], args[2]);
            plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<green>Gave token <aqua>" + args[2] + "</aqua> to <aqua>" + args[1] + "</aqua>!</green>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("remove") && args.length == 2) {
            if (!sender.hasPermission("mythictokens.admin")) {
                plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<red>No permission!</red>"));
                return true;
            }
            tokenManager.removeToken(args[1]);
            plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<green>Removed token from <aqua>" + args[1] + "</aqua>!</green>"));
            return true;
        }

        if (args[0].equalsIgnoreCase("toggle") && args.length == 2) {
            if (!sender.hasPermission("mythictokens.admin")) {
                plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<red>No permission!</red>"));
                return true;
            }
            tokenManager.toggleToken(args[1]);
            plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<green>Toggled token <aqua>" + args[1] + "</aqua>!</green>"));
            return true;
        }

        plugin.getAdventure().sender(sender).sendMessage(miniMessage.deserialize("<red>Usage: /token [give|remove|toggle] <player|token> [token]</red>"));
        return true;
    }
}
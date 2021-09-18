package com.wyvencraft.api.commands;

import com.wyvencraft.api.integration.IWyvenCore;
import com.wyvencraft.api.language.LanguageManager;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class SubCommand {
    private final IWyvenCore plugin;
    private final String name;
    private final String permission;
    private final int minArgs;
    private final boolean playerCommand;

    public SubCommand(IWyvenCore plugin, String name, String permission, int minArgs, boolean playerCommand) {
        this.plugin = plugin;
        this.name = name;
        this.permission = permission;
        this.minArgs = minArgs;
        this.playerCommand = playerCommand;
    }

    public void command(CommandSender s, String[] args) {
        final LanguageManager lang = plugin.getLanguageManager();

        if (playerCommand && !(s instanceof Player)) {
            lang.sendMessage(s, "ONLY_PLAYERS", (Replacer) null);
            return;
        }

        if (s.hasPermission(permission)) {
            handleCommand(s, args);
        } else {
            lang.sendMessage(s, "NO_PERMISSION", (Replacer) null);
        }
    }

    protected abstract void handleCommand(CommandSender s, String[] args);

    public String getName() {
        return name;
    }

    public int getMinArgs() {
        return minArgs;
    }

    public IWyvenCore getPlugin() {
        return this.plugin;
    }
}

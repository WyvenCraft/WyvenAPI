package com.wyvencraft.api.commands;

import com.wyvencraft.api.language.LanguageManager;
import com.wyvencraft.api.utils.Replacer;
import com.wyvencraft.api.integration.IWyvenCore;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    private final IWyvenCore plugin;
    private final String name;
    private final String permission;
    private final int minArgs;

    public SubCommand(IWyvenCore plugin, String name, String permission, int minArgs) {
        this.plugin = plugin;
        this.name = name;
        this.permission = permission;
        this.minArgs = minArgs;
    }

    public void command(CommandSender s, String[] args) {
        if (hasPermission(s)) {
            handleCommand(s, args);
        } else {
            LanguageManager lang = plugin.getLanguageManager();
            lang.sendMessage(s, "NO_PERMISSION", (Replacer) null);
        }
    }

    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission(permission) || sender.hasPermission("wyvencore.*");
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

package com.wyvencraft.api.commands;

import com.wyvencraft.api.integration.WyvenAPI;
import com.wyvencraft.api.managers.ILangManager;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
    private final WyvenAPI wyven;
    private final String name;
    private final String permission;
    private final int minArgs;

    public SubCommand(WyvenAPI wyven, String name, String permission, int minArgs) {
        this.wyven = wyven;
        this.name = name;
        this.permission = permission;
        this.minArgs = minArgs;
    }

    public void command(CommandSender s, String[] args) {
        if (hasPermission(s)) {
            handleCommand(s, args);
        } else {
            ILangManager lang = wyven.getLangManager();
            lang.sendMessage(s, lang.getMessageColored("NO_PERMISSION"));
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
}

package com.wyvencraft.api.addon;

import com.wyvencraft.api.integration.IWyvenCore;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

public final  class AddonLogger extends Logger {
    private final Addon addon;

    public AddonLogger(Addon addon) {
        super(addon.getName(), null);
        this.addon = addon;
    }

    public Addon getAddon() {
        return this.addon;
    }

    @Override
    public void log(LogRecord record) {
        Addon addon = getAddon();
        String addonPrefix = addon.getPrefix();

        IWyvenCore core = addon.getPlugin();
        JavaPlugin plugin = core.getPlugin();
        String pluginName = plugin.getName();

        String originalMessage = record.getMessage();
        String newMessage = ("[" + addonPrefix + "] " + originalMessage);
        record.setMessage(newMessage);
        record.setLoggerName(pluginName);

        Logger logger = plugin.getLogger();
        logger.log(record);
    }
}

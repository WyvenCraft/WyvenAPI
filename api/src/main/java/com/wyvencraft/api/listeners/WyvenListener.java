package com.wyvencraft.api.listeners;

import com.wyvencraft.api.integration.IWyvenCore;
import com.wyvencraft.api.configuration.ConfigurationManager;
import com.wyvencraft.api.language.LanguageManager;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public abstract class WyvenListener implements Listener {
    private final IWyvenCore plugin;

    public WyvenListener(IWyvenCore plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null!");
    }

    public void register() {
        IWyvenCore core = getCombatLogX();
        JavaPlugin plugin = core.getPlugin();

        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(this, plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }

    protected final IWyvenCore getCombatLogX() {
        return this.plugin;
    }

    protected final JavaPlugin getJavaPlugin() {
        IWyvenCore core = getCombatLogX();
        return core.getPlugin();
    }

    protected final Logger getPluginLogger() {
        IWyvenCore plugin = getCombatLogX();
        return plugin.getLogger();
    }

    protected final ConfigurationManager getPluginConfigurationManager() {
        IWyvenCore plugin = getCombatLogX();
        return plugin.getConfigurationManager();
    }

    protected final LanguageManager getLanguageManager() {
        IWyvenCore core = getCombatLogX();
        return core.getLanguageManager();
    }

    protected final String getMessageWithPrefix(@Nullable CommandSender sender, @NotNull String key,
                                                @Nullable Replacer replacer, boolean color) {
        IWyvenCore plugin = getCombatLogX();
        LanguageManager languageManager = plugin.getLanguageManager();

        String message = languageManager.getMessage(key, replacer);
        if (message.isEmpty()) return "";

        String prefix = languageManager.getMessage("prefix", (Replacer) null);
        return (prefix.isEmpty() ? message : String.format(Locale.US, "%s %s", prefix, message));
    }

    protected final void sendMessageWithPrefix(@NotNull CommandSender sender, @NotNull String key,
                                               @Nullable Replacer replacer, boolean color) {
        String message = getMessageWithPrefix(sender, key, replacer, color);
        if (!message.isEmpty()) sender.sendMessage(message);
    }

    protected void printDebug(String message) {
        ConfigurationManager configurationManager = getPluginConfigurationManager();
        YamlConfiguration configuration = configurationManager.get("config.yml");
        if (!configuration.getBoolean("debug-mode")) return;

        Logger logger = getPluginLogger();
        logger.info("[Debug] " + message);
    }
}

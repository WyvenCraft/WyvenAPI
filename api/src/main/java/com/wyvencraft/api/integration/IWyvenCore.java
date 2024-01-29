package com.wyvencraft.api.integration;

import com.wyvencraft.api.Database;
import com.wyvencraft.api.addon.AddonManager;
import com.wyvencraft.api.configuration.ConfigurationManager;
import com.wyvencraft.api.language.LanguageManager;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public interface IWyvenCore {
    JavaPlugin getPlugin();

    Logger getLogger();

    AddonManager getAddonManager();

    LanguageManager getLanguageManager();

    Database getDatabase();

    ConfigurationManager getConfigurationManager();

    YamlConfiguration getConfig(String fileName);

    void reloadConfig(String fileName);

    void saveConfig(String fileName);

    void saveDefaultConfig(String fileName);

    YamlConfiguration getData(OfflinePlayer player);

    void saveData(OfflinePlayer player);

    void registerCommand(final String commandName, final CommandExecutor executor, final TabCompleter tabCompleter, final String description, final String usage, final String... aliases);

    String getMessageWithPrefix(CommandSender sender, String key, Replacer replacer, boolean color);

    void sendMessageWithPrefix(CommandSender sender, String key, Replacer replacer, boolean color);

    void sendMessage(CommandSender sender, String... messageArray);

    void printDebug(String... messageArray);

    void printDebug(Throwable ex);
}

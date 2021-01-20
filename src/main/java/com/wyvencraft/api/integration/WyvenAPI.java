package com.wyvencraft.api.integration;

import com.wyvencraft.api.addon.AddonHandler;
import com.wyvencraft.api.hooks.Hook;
import com.wyvencraft.api.managers.ILangManager;
import com.wyvencraft.api.menus.IMenus;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.util.logging.Logger;

public interface WyvenAPI {
    Plugin getPlugin();

    Logger getLogger();

    AddonHandler getAddonHandler();

    IMenus getMenus();

    ILangManager getLangManager();

    void saveConfig(String configName);

    void saveDefaultConfig(String configName);

    void reloadConfig(String configName);

    FileConfiguration getConfig(String configName);

    void registerCommand(final String commandName, final CommandExecutor executor, final TabCompleter tabCompleter, final String description, final String usage, final String... aliases);
}

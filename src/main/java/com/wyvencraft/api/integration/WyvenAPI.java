package com.wyvencraft.api.integration;

import com.wyvencraft.api.addon.AddonHandler;
import com.wyvencraft.api.managers.ILangManager;
import com.wyvencraft.api.menus.IMenus;
import io.github.portlek.smartinventory.SmartInventory;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface WyvenAPI {
    Plugin getPlugin();

    AddonHandler getAddonHandler();

    SmartInventory getSmartInv();

    IMenus getMenus();

    ILangManager getLangManager();

    void saveConfig(String configName);

    void saveDefaultConfig(String configName);

    void reloadConfig(String configName);

    File getConfig(String configName);

    void registerCommand(final String commandName, final CommandExecutor executor, final TabCompleter tabCompleter, final String description, final String usage, final String... aliases);
}

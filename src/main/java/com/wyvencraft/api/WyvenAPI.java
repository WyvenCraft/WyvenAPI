package com.wyvencraft.api;

import com.wyvencraft.api.addon.AddonHandler;
import com.wyvencraft.api.configuration.ConfigSettings;
import com.wyvencraft.api.configuration.FileManager;
import com.wyvencraft.api.database.Database;
import com.wyvencraft.api.managers.LangManager;
import com.wyvencraft.api.menus.Menus;
import io.github.portlek.smartinventory.SmartInventory;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

public interface WyvenAPI {
    Database getDatabase();

    FileManager getFileManager();

    AddonHandler getAddonHandler();

    Plugin getPlugin();

    ConfigSettings getSettings();

    SmartInventory getSmartInv();

    Menus getMenus();

    LangManager getLangManager();

    void registerCommand(final String commandName, final CommandExecutor executor, final TabCompleter tabCompleter, final String description, final String usage, final String... aliases);
}

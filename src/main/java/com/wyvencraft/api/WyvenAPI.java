package com.wyvencraft.api;

import com.wyvencraft.api.addon.AddonHandler;
import com.wyvencraft.api.configuration.ConfigSettings;
import com.wyvencraft.api.configuration.FileManager;
import com.wyvencraft.api.database.Database;
import com.wyvencraft.api.managers.LangManager;
import com.wyvencraft.api.menus.Menus;
import com.wyvencraft.api.player.WyvenPlayer;
import io.github.portlek.smartinventory.SmartInventory;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public interface WyvenAPI {
    WyvenPlayer getPlayer(UUID uuid);

    Database getDatabase();

    FileManager getFileManager();

    AddonHandler getAddonHandler();

    Plugin getPlugin();

    ConfigSettings getSettings();

    SmartInventory getSmartInv();

    Menus getMenus();

    LangManager getLangManager();

    void disable();

    void registerCommand(final String commandName, final CommandExecutor executor, final TabCompleter tabCompleter, final String description, final String usage, final String... aliases);
}

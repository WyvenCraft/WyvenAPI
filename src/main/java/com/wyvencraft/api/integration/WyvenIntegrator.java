package com.wyvencraft.api.integration;

import com.wyvencraft.api.addon.AddonHandler;
import com.wyvencraft.api.configuration.ConfigSettings;
import com.wyvencraft.api.configuration.IFileManager;
import com.wyvencraft.api.database.IDatabase;
import com.wyvencraft.api.managers.ILangManager;
import com.wyvencraft.api.menus.IMenus;
import com.wyvencraft.api.player.WyvenPlayer;
import io.github.portlek.smartinventory.SmartInventory;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

public interface WyvenIntegrator {
    WyvenPlayer getPlayer(UUID uuid);

    AddonHandler getAddonHandler();

    Plugin getPlugin();

    SmartInventory getSmartInv();

    IMenus getMenus();

    ILangManager getLangManager();

    void disable();

    void registerCommand(final String commandName, final CommandExecutor executor, final TabCompleter tabCompleter, final String description, final String usage, final String... aliases);
}

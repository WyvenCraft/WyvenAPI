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

public class WyvenAPI implements WyvenIntegrator {

    public WyvenAPI(Plugin plugin) {
    }

    @Override
    public WyvenPlayer getPlayer(UUID uuid) {
        return null;
    }

    @Override
    public IDatabase getDatabase() {
        return null;
    }

    @Override
    public IFileManager getFileManager() {
        return null;
    }

    @Override
    public AddonHandler getAddonHandler() {
        return null;
    }

    @Override
    public Plugin getPlugin() {
        return null;
    }

    @Override
    public ConfigSettings getSettings() {
        return null;
    }

    @Override
    public SmartInventory getSmartInv() {
        return null;
    }

    @Override
    public IMenus getMenus() {
        return null;
    }

    @Override
    public ILangManager getLangManager() {
        return null;
    }

    @Override
    public void disable() {

    }

    @Override
    public void registerCommand(String commandName, CommandExecutor executor, TabCompleter tabCompleter, String description, String usage, String... aliases) {

    }
}

package com.wyvencraft.api.addon;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;
import java.util.jar.JarFile;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public interface AddonHandler {

    List<Addon> getAddons();

    Map<Addon, List<Listener>> getAddonListenerMap();

    void loadAddons();

    void enableAddons();

    void disableAddons();

    void reloadAddonConfigs();

    <E extends Addon> Object getAddonByName(final String name);

    List<Addon> getLoadedAddons();

    List<Addon> getEnabledAddons();

    List<Addon> getAllAddons();

    AddonClassLoader getClassLoader(final Addon addon);

    Class<?> getClassByName(final String name);

    void setClass(final String name, final Class<?> clazz);

    void registerListener(final Addon addon, final Listener listener);

    void loadAddon(final File file);

    void enableAddon(final Addon addon);

    void disableAddon(final Addon addon);

    YamlConfiguration getAddonDescription(final JarFile jarFile) throws IllegalStateException, IOException, InvalidConfigurationException;
}

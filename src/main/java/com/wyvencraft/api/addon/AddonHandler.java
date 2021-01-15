package com.wyvencraft.api.addon;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

public interface AddonHandler {

    List<Addon> getAddons();

    Map<Addon, List<Listener>> getAddonListenerMap();

    void loadAddons();

    void enableAddons();

    void disableAddons();

    void reloadAddonConfigs();

    Addon getAddonByName(final String name);

    Addon getLoadedAddons();

    Addon getEnabledAddons();

    Addon getAllAddons();

    <E extends AddonClassLoader> Object getClassLoader(final Addon IAddon);

    Class<?> getClassByName(final String name);

    void setClass(final String name, final Class<?> clazz);

    void registerListener(final Addon IAddon, final Listener listener);

    void loadAddon(final File file);

    void enableAddon(final Addon IAddon);

    void disableAddon(final Addon IAddon);

    YamlConfiguration getAddonDescription(final JarFile jarFile) throws IllegalStateException, IOException, InvalidConfigurationException;
}

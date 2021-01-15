package com.wyvencraft.api.addon;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.jar.JarFile;

public interface IAddonHandler {

    List<IAddon> getAddons();

    Map<IAddon, List<Listener>> getAddonListenerMap();

    void loadAddons();

    void enableAddons();

    void disableAddons();

    void reloadAddonConfigs();

    <E extends IAddon> Object getAddonByName(final String name);

    List<IAddon> getLoadedAddons();

    List<IAddon> getEnabledAddons();

    List<IAddon> getAllAddons();

    IAddonClassLoader getClassLoader(final IAddon IAddon);

    Class<?> getClassByName(final String name);

    void setClass(final String name, final Class<?> clazz);

    void registerListener(final IAddon IAddon, final Listener listener);

    void loadAddon(final File file);

    void enableAddon(final IAddon IAddon);

    void disableAddon(final IAddon IAddon);

    YamlConfiguration getAddonDescription(final JarFile jarFile) throws IllegalStateException, IOException, InvalidConfigurationException;
}

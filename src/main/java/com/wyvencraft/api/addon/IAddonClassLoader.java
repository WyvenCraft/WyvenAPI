package com.wyvencraft.api.addon;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public interface IAddonClassLoader {
    Class<?> findClass(final String name, final boolean checkGlobal);

    IAddon getAddon();

    Set<String> getClasses();

    void registerAddon(final YamlConfiguration description, final File path);

    IAddonDescription createDescription(final YamlConfiguration config) throws IllegalStateException;
}

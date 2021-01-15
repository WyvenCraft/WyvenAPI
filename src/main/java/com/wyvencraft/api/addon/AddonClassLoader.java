package com.wyvencraft.api.addon;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.Set;

public interface AddonClassLoader {
    Class<?> findClass(final String name, final boolean checkGlobal);

    Addon getAddon();

    Set<String> getClasses();

    void registerAddon(final YamlConfiguration description, final File path);

    AddonDescription createDescription(final YamlConfiguration config) throws IllegalStateException;
}

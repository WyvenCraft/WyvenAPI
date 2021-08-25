package com.wyvencraft.api.configuration;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.InputStream;
import java.util.Objects;
import java.util.logging.Logger;

public final class WrapperResourcePlugin implements IResource {
    private final Plugin plugin;

    public WrapperResourcePlugin(Plugin plugin) {
        this.plugin = Objects.requireNonNull(plugin, "plugin must not be null!");
    }

    @Override
    public File getDataFolder() {
        return this.plugin.getDataFolder();
    }

    @Override
    public InputStream getResource(String name) {
        return this.plugin.getResource(name);
    }

    @Override
    public Logger getLogger() {
        return this.plugin.getLogger();
    }
}

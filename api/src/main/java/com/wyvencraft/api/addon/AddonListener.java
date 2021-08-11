package com.wyvencraft.api.addon;

import com.wyvencraft.api.listeners.WyvenListener;
import com.wyvencraft.api.managers.ConfigurationManager;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.Objects;
import java.util.logging.Logger;

public abstract class AddonListener extends WyvenListener {
    private final Addon addon;

    public AddonListener(Addon addon) {
        super(addon.getPlugin());
        this.addon = Objects.requireNonNull(addon, "addon must not be null!");
    }

    @Override
    public final void register() {
        Addon addon = getAddon();
        addon.registerListener(this);
    }

    protected final void printDebug(String message) {
        ConfigurationManager pluginConfigurationManager = getPluginConfigurationManager();
        YamlConfiguration configuration = pluginConfigurationManager.get("config.yml");
        if (!configuration.getBoolean("debug-mode")) return;

        Logger logger = getAddonLogger();
        logger.info("[Debug] " + message);
    }

    protected final Addon getAddon() {
        return this.addon;
    }

    protected final Logger getAddonLogger() {
        Addon addon = getAddon();
        return addon.getLogger();
    }

    protected final ConfigurationManager getAddonConfigurationManager() {
        Addon addon = getAddon();
        return addon.getConfigurationManager();
    }
}

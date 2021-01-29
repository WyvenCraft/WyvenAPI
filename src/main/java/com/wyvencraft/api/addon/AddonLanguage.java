package com.wyvencraft.api.addon;

import com.wyvencraft.api.managers.ILangManager;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.command.CommandSender;

import java.util.Objects;

@Deprecated
public class AddonLanguage implements ILangManager {

    private final Addon addon;

    public AddonLanguage(Addon addon) {
        this.addon = Objects.requireNonNull(addon, addon.getDescription().getName() + " must not be null");
    }

    public String getLanguage() {
        return null;
    }

    public void sendMessage(CommandSender player, String key, Replacer... replacerArray) {
    }

    public void sendMessage(CommandSender sender, String... messageArray) {

    }

    public String getMessage(String key, Replacer... replacers) {
        return null;
    }

    public String getMessage(String key) {
        return null;
    }
}

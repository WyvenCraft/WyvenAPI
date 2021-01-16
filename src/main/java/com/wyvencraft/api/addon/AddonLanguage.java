package com.wyvencraft.api.addon;

import com.wyvencraft.api.managers.ILangManager;
import com.wyvencraft.api.utils.Replacer;
import org.bukkit.entity.Player;

public class AddonLanguage implements ILangManager {

    public AddonLanguage(Addon addon) {

    }

    @Override
    public String getLanguage() {
        return null;
    }

    @Override
    public String getMessage(String msg) {
        return null;
    }

    @Override
    public String getLocalizedMessage(Player player, String key) {
        return null;
    }

    @Override
    public void sendLocalizedMessage(Player player, String key, Replacer... replacerArray) {
    }
}

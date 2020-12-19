package com.wyvencraft.api.menus;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public interface Menus {
    void loadMenus();

    Menu getMenu(String menuName);

    List<Menu> getMenus();

    MenuItem getMenuItem(YamlConfiguration file, ConfigurationSection section);

}

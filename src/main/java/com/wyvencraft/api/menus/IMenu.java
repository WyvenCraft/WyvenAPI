package com.wyvencraft.api.menus;

import java.util.List;

public interface IMenu {

    public String getTitle();

    public int getRows();

    public String getId();

    public String getParent();

    public List<IMenuItem> getContents();

    public List<CustomItem> getCustomItems();
}

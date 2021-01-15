package com.wyvencraft.api.menus;

import java.util.List;

public class Menu {
    private final String id;
    private final String title;
    private final String parent;
    private final int rows;
    private final List<MenuItem> contents;
    private final List<CustomItem> customItems;

    public Menu(String id, String title, String parent, int rows, List<MenuItem> contents, List<CustomItem> customItems) {
        this.id = id;
        this.title = title;
        this.parent = parent;
        this.rows = rows;
        this.contents = contents;
        this.customItems = customItems;
    }

    public String getTitle() {
        return title;
    }

    public int getRows() {
        return rows;
    }

    public String getId() {
        return id;
    }

    public String getParent() {
        return parent;
    }

    public List<MenuItem> getContents() {
        return contents;
    }

    public List<CustomItem> getCustomItems() {
        return customItems;
    }
}

package com.wyvencraft.api.menus;

import io.github.portlek.bukkititembuilder.ItemStackBuilder;
import io.github.portlek.smartinventory.util.SlotPos;

import java.util.List;

public class CustomItem extends IMenuItem {
    public CustomItem(ItemStackBuilder stack, SlotPos slot, boolean fill, List<SlotPos> slots, List<String> actions, Type type) {
        super(stack, slot, fill, slots, actions, type);
    }
}

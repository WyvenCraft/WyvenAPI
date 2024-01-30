package com.wyvencraft.api.itemfactory;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack itemStack;

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemBuilder setDisplayName(String displayName) {
        this.itemStack.editMeta(meta -> meta.displayName(Component.text(displayName)));
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        itemStack.editMeta(meta -> {
            List<TextComponent> components = Arrays.stream(lore)
                    .map(Component::text)
                    .toList();
            meta.lore(components);
        });
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment, int level) {
        itemStack.editMeta(meta -> meta.addEnchant(enchantment, level, true));
        return this;
    }

    public ItemBuilder addEnchantment(Enchantment enchantment) {
        itemStack.editMeta(meta -> meta.addEnchant(enchantment, 1, true));
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemStack.editMeta(meta -> meta.setUnbreakable(unbreakable));
        return this;
    }

    public ItemBuilder setGlowing() {
        addEnchantment(Enchantment.LUCK, 1);
        return this;
    }

    public ItemBuilder setModelData(int modelData) {
        itemStack.editMeta(meta -> meta.setCustomModelData(modelData));
        return this;
    }

    public ItemBuilder setFlags(ItemFlag... flags) {
        itemStack.editMeta(meta -> meta.addItemFlags(flags));
        return this;
    }

    public ItemBuilder setSkullOwner(String owner) {
        itemStack.editMeta(meta -> {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(owner);
            ((SkullMeta) meta).setOwningPlayer(offlinePlayer);
        });
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}

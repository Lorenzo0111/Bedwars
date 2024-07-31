package me.lorenzo0111.bedwars.shop;

import lombok.Getter;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.items.SpecialItem;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpecialItemFactory {
    private final NamespacedKey specialItemKey = new NamespacedKey(BedwarsPlugin.getInstance(), "special_item");
    @Getter
    private final Map<String, SpecialItem> specialItems = new HashMap<>();


    @SuppressWarnings("ConstantConditions")
    public @NotNull ItemStack build(@NotNull SpecialItem item) {
        ItemStack stack = item.build();

        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer().set(specialItemKey, PersistentDataType.STRING, item.getId());

        stack.setItemMeta(meta);
        return stack;
    }


    public @Nullable SpecialItem getSpecialItem(@NotNull ItemStack stack) {
        ItemMeta meta = stack.getItemMeta();
        if (meta == null) return null;

        String id = meta.getPersistentDataContainer().get(specialItemKey, PersistentDataType.STRING);
        if (id == null) return null;

        return specialItems.get(id);
    }

    public @Nullable SpecialItem getSpecialItem(@NotNull String id) {
        return specialItems.get(id);
    }

    public void registerSpecialItem(@NotNull SpecialItem item) {
        if (specialItems.containsKey(item.getId()))
            throw new IllegalArgumentException("The item with the id " + item.getId() + " is already registered.");

        specialItems.put(item.getId(), item);
    }

}

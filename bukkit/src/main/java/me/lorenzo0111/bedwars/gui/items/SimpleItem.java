package me.lorenzo0111.bedwars.gui.items;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SimpleItem extends ConfiguredItem {
    private final Consumer<Player> consumer;

    public SimpleItem(String id, Consumer<Player> consumer) {
        super(id);
        this.consumer = consumer;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        this.consumer.accept(player);
    }
}

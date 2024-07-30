package me.lorenzo0111.bedwars.gui.items.setup;

import me.lorenzo0111.bedwars.gui.items.ConfiguredItem;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class LocationEditItem extends ConfiguredItem {
    private final Consumer<Location> consumer;

    public LocationEditItem(String id, Consumer<Location> consumer) {
        super(id);
        this.consumer = consumer;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        this.consumer.accept(player.getLocation());
    }

}

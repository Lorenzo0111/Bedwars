package me.lorenzo0111.bedwars.gui.items.setup;

import me.lorenzo0111.bedwars.gui.items.ConfiguredItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public class SaveItem extends ConfiguredItem {
    public SaveItem() {
        super("save");
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.closeInventory();
        plugin.getSetupManager().endSetup(player);
    }
}

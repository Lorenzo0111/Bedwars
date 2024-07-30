package me.lorenzo0111.bedwars.gui.items.setup;

import me.lorenzo0111.bedwars.conversations.ConversationUtil;
import me.lorenzo0111.bedwars.conversations.NumberInput;
import me.lorenzo0111.bedwars.gui.items.ConfiguredItem;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class IntegerEditItem extends ConfiguredItem {
    private final Consumer<Integer> consumer;

    public IntegerEditItem(String id, Consumer<Integer> consumer) {
        super(id);
        this.consumer = consumer;
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        player.closeInventory();
        ConversationUtil.start(player, new NumberInput(
                plugin.getPrefixed(this.id),
                this.consumer)
        );
    }

}

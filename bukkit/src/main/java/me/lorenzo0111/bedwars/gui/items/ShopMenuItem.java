package me.lorenzo0111.bedwars.gui.items;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import me.lorenzo0111.bedwars.shop.ShopItem;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class ShopMenuItem extends ConfiguredItem {
    private final ShopItem item;

    public ShopMenuItem(ShopItem item) {
        super("shop");

        this.item = item;
    }

    @Override
    public ItemBuilder overrideBase() {
        return new ItemBuilder(item.parseMaterial())
                .setAmount(item.amount());
    }

    @Override
    public void handleClick(@NotNull ClickType clickType, @NotNull Player player, @NotNull InventoryClickEvent inventoryClickEvent) {
        AbstractGame game = plugin.getGameManager().getGame(player);
        ChatColor team = null;
        if (game != null)
            team = game.getTeam(player);

        ItemStack result = item.buy(player, team);
        if (result == null) return;

        player.getInventory().addItem(result);
    }

    @Override
    public String replacePlaceholders(String origin) {
        return origin.replace("%name%", item.name())
                .replace("%price%", item.price() + " " + item.priceType())
                .replace("%amount%", String.valueOf(item.amount()));
    }
}

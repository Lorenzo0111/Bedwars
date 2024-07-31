package me.lorenzo0111.bedwars.gui.menus;

import me.lorenzo0111.bedwars.gui.items.ShopMenuItem;
import me.lorenzo0111.bedwars.shop.ShopItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ShopMenu extends BaseMenu {

    public ShopMenu(@NotNull List<ShopItem> items) {
        super("shop", true);

        items.forEach(item -> this.addItem(new ShopMenuItem(item)));
    }

}

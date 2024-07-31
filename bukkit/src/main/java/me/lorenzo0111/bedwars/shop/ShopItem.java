package me.lorenzo0111.bedwars.shop;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.items.SpecialItem;
import me.lorenzo0111.bedwars.utils.ShopUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public record ShopItem(String name,
                       String material,
                       int amount,
                       int price,
                       Material priceType) {

    public @Nullable ItemStack buy(Player player, @Nullable ChatColor color) {
        if (!ShopUtils.hasEnough(player, priceType, price)) return null;

        ShopUtils.remove(player, priceType, price);

        SpecialItemFactory factory = BedwarsPlugin.getInstance().getSpecialItemFactory();
        SpecialItem specialItem = factory.getSpecialItem(material);
        if (specialItem != null) return factory.build(specialItem);

        Material mat = material.equalsIgnoreCase("WOOL") ?
                Material.getMaterial(color + "_WOOL") :
                Material.getMaterial(material);
        if (mat == null) return null;

        return new ItemBuilder(mat)
                .setDisplayName(name)
                .setAmount(amount)
                .get();
    }

    public Material parseMaterial() {
        return material.equalsIgnoreCase("WOOL") ?
                Material.WHITE_WOOL :
                Material.getMaterial(material);
    }

}

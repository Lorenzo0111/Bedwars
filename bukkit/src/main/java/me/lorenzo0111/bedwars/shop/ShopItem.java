package me.lorenzo0111.bedwars.shop;

import me.lorenzo0111.bedwars.utils.ShopUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public record ShopItem(String name,
                       Material material,
                       int cost,
                       Material costType) {

    public @Nullable ItemStack buy(Player player) {
        if (!ShopUtils.hasEnough(player, costType, cost)) return null;

        ShopUtils.remove(player, costType, cost);

        return new ItemBuilder(material)
                .setDisplayName(name)
                .get();
    }

}

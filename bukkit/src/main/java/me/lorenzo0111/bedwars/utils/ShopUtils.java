package me.lorenzo0111.bedwars.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class ShopUtils {

    public static boolean hasEnough(Player player, Material material, int amount) {
        return player.getInventory().containsAtLeast(new ItemStack(material), amount);
    }

    public static void remove(Player player, Material material, int amount) {
        player.getInventory().removeItem(new ItemStack(material, amount));
    }
}

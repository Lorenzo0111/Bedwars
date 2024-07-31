package me.lorenzo0111.bedwars.shop.items;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.items.SpecialItem;
import org.bukkit.Material;
import org.bukkit.entity.Fireball;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

public class FireballItem extends SpecialItem {

    @Override
    public String getId() {
        return "FIREBALL";
    }

    @Override
    public @NotNull ItemStack build() {
        return new ItemBuilder(Material.FIRE_CHARGE)
                .setDisplayName(BedwarsPlugin.getInstance().getMessage("items.bridge-egg"))
                .get();
    }

    @Override
    public void handle(PlayerInteractEvent event) {
        event.getPlayer().launchProjectile(Fireball.class);
    }

}

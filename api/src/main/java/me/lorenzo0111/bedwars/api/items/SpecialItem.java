package me.lorenzo0111.bedwars.api.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class SpecialItem {

    public abstract String getId();
    public abstract ItemStack build();
    public abstract void handle(PlayerInteractEvent event);

}

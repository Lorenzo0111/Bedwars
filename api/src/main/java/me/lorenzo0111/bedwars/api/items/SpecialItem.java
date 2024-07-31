package me.lorenzo0111.bedwars.api.items;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class SpecialItem {

    public abstract String getId();
    public abstract @NotNull ItemStack build();
    public abstract void handle(PlayerInteractEvent event);

}

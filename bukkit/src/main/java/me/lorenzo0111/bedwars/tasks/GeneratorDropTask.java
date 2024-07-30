package me.lorenzo0111.bedwars.tasks;

import me.lorenzo0111.bedwars.BedwarsPlugin;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class GeneratorDropTask extends BukkitRunnable {
    private final Material material;
    private final Location location;

    public GeneratorDropTask(@NotNull Material material, @NotNull Location location) {
        this.material = material;
        this.location = location;

        this.runTaskTimerAsynchronously(BedwarsPlugin.getInstance(), 0,
                BedwarsPlugin.getInstance()
                        .getConfig().getInt("generators." + material.name()));
    }


    @Override
    public void run() {
        location.getWorld().dropItem(location, new ItemStack(material));
    }

}

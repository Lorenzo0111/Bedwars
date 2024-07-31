package me.lorenzo0111.bedwars.hooks.hologram.decent;

import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class DecentHologramHook extends HologramHook {

    @Override
    public String getId() {
        return "DecentHolograms";
    }

    @Override
    public WrappedHologram create(Location location, List<String> stringLines, List<Material> materialLines) {
        DecentHologram hologram = new DecentHologram(this, location, stringLines, materialLines);
        holograms.add(hologram);
        hologram.spawn();

        return hologram;
    }

}

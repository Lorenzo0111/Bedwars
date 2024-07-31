package me.lorenzo0111.bedwars.hooks.hologram.natives;

import me.lorenzo0111.bedwars.api.hologram.HologramHook;
import me.lorenzo0111.bedwars.api.hologram.WrappedHologram;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;

public class NativeHologramHook extends HologramHook {

    @Override
    public String getId() {
        return "Native";
    }

    @Override
    public WrappedHologram create(Location location, List<String> stringLines, List<Material> materialLines) {
        NativeHologram hologram = new NativeHologram(this, location, stringLines, materialLines);
        holograms.add(hologram);
        hologram.spawn();

        return hologram;
    }

}

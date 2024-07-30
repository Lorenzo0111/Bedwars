package me.lorenzo0111.bedwars.api.hologram;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.List;
import java.util.UUID;

@Data
public abstract class WrappedHologram {
    protected final UUID uuid = UUID.randomUUID();
    protected final HologramHook hook;
    protected final Location location;
    protected final List<String> stringLines;
    protected final List<Material> materialLines;

    public abstract void spawn();
    public abstract void remove();
    public abstract void update();
}

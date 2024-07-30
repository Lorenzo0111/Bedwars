package me.lorenzo0111.bedwars.hooks.hologram;

import lombok.Data;
import org.bukkit.Location;

import java.util.List;

@Data
public abstract class WrappedHologram {
    protected final Location location;
    protected final List<String> lines;

    public abstract void spawn();
    public abstract void remove();
    public abstract void update();
}

package me.lorenzo0111.bedwars.api.hologram;

import lombok.Data;
import org.bukkit.Location;

import java.util.List;
import java.util.UUID;

@Data
public abstract class WrappedHologram {
    protected final UUID uuid = UUID.randomUUID();
    protected final Location location;
    protected final List<String> lines;

    public abstract void spawn();
    public abstract void remove();
    public abstract void update();
}

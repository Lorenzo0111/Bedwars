package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Data
public class TeamConfig implements ConfigurationSerializable {
    private Location spawn;
    private Location bed;
    private Location shop;
    private Location upgrades;
    private Location generator;

    @SuppressWarnings("unused")
    public TeamConfig(Map<String, Object> data) {
        this.spawn = (Location) data.get("spawn");
        this.bed = (Location) data.get("bed");
        this.shop = (Location) data.get("shop");
        this.upgrades = (Location) data.get("upgrades");
        this.generator = (Location) data.get("generator");
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return Map.of(
                "spawn", this.spawn,
                "bed", this.bed,
                "shop", this.shop,
                "upgrades", this.upgrades,
                "generator", this.generator
        );
    }
}

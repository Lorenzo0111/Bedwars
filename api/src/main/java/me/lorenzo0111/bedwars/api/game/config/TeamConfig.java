package me.lorenzo0111.bedwars.api.game.config;

import lombok.Data;
import org.bukkit.Location;

@Data
public class TeamConfig {
    private Location spawn;
    private Location bed;
    private Location shop;
    private Location upgrades;
    private Location generator;
}

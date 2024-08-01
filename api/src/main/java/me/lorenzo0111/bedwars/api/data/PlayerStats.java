package me.lorenzo0111.bedwars.api.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import me.lorenzo0111.bedwars.api.BedwarsProvider;

import java.util.UUID;

@Data
@AllArgsConstructor
public class PlayerStats {
    private final UUID uuid;
    private final String name;
    private int kills;
    private int deaths;
    private int wins;
    private int losses;
    private int beds;
    private int games;

    public void update() {
        BedwarsProvider.getInstance().updateStats(uuid, this);
    }
}

package me.lorenzo0111.bedwars.api.game;

import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.api.game.config.GameConfiguration;

import java.util.UUID;

@RequiredArgsConstructor
public abstract class AbstractGame {
    protected final UUID uuid = UUID.randomUUID();
    protected final GameConfiguration config;
    protected GameState state = GameState.WAITING;

    public abstract void start();
    public abstract void stop();

    public UUID getUniqueId() {
        return this.uuid;
    }
}

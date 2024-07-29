package me.lorenzo0111.bedwars.api.game;

import java.util.UUID;

public abstract class AbstractGame {
    protected final UUID uuid = UUID.randomUUID();
    protected GameState state = GameState.WAITING;

    public abstract void start();
    public abstract void stop();

    public UUID getUniqueId() {
        return this.uuid;
    }
}

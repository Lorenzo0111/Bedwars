package me.lorenzo0111.bedwars.api.game;

public enum GameState {
    WAITING,
    STARTING,
    PLAYING,
    SHUTDOWN;

    public boolean canJoin() {
        return this == WAITING || this == STARTING;
    }
}

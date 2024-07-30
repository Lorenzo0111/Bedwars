package me.lorenzo0111.bedwars.api.game;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AbstractGameManager {
    void reload();
    void stop();
    @Nullable AbstractGame getGame(UUID id);
    @Nullable AbstractGame findGame();
}

package me.lorenzo0111.bedwars.api.game;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AbstractGameManager {
    void reload();
    void stop();
    @Nullable AbstractGame getGame(UUID id);
    @Nullable AbstractGame findGame();
    @Nullable AbstractGame getGame(Player player);
}

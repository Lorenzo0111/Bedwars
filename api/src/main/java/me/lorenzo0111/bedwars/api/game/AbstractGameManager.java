package me.lorenzo0111.bedwars.api.game;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface AbstractGameManager {
    void reload();
    void stop();
    @Nullable AbstractGame findGame();
    @Nullable AbstractGame getGame(UUID id);
    @Nullable AbstractGame getGame(Player player);
    @Nullable AbstractGame getGame(World world);
}

package me.lorenzo0111.bedwars.api.scoreboard;

import lombok.Data;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

@Data
public abstract class WrappedScoreboard {
    protected final UUID uuid = UUID.randomUUID();
    protected final ScoreboardHook hook;
    protected final String title;
    protected final Callable<List<String>> lines;

    public abstract void show(Player player);
    public abstract void hide(Player player);

    public abstract void destroy();
    public abstract void update();
}

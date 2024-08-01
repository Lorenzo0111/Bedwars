package me.lorenzo0111.bedwars.api.events;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

public abstract class GameEvent extends Event {
    protected AbstractGame game;

    public GameEvent(@NotNull final AbstractGame game) {
        this.game = game;
    }

    @NotNull
    public final AbstractGame getGame() {
        return game;
    }
}


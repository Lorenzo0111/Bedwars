package me.lorenzo0111.bedwars.api.events;

import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("unused")
public class BedwarsStartEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();

    public BedwarsStartEvent(@NotNull AbstractGame game) {
        super(game);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

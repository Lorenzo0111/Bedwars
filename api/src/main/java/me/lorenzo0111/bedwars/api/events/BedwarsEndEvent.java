package me.lorenzo0111.bedwars.api.events;

import lombok.Getter;
import me.lorenzo0111.bedwars.api.game.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@SuppressWarnings("unused")
public class BedwarsEndEvent extends GameEvent {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player winner;

    public BedwarsEndEvent(@NotNull AbstractGame game, Player winner) {
        super(game);
        this.winner = winner;
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

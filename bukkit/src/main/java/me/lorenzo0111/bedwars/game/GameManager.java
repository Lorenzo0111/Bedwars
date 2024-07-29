package me.lorenzo0111.bedwars.game;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.lorenzo0111.bedwars.BedwarsPlugin;
import me.lorenzo0111.bedwars.api.game.AbstractGameManager;
import me.lorenzo0111.bedwars.api.game.AbstractGame;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GameManager implements AbstractGameManager {
    private final BedwarsPlugin plugin;
    @Getter private final List<AbstractGame> games = new ArrayList<>();

    @Override
    public void reload() {
        this.stop();
    }

    @Override
    public void stop() {
        this.games.forEach(AbstractGame::stop);
        this.games.clear();
    }
}

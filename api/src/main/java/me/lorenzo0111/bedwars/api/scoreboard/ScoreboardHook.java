package me.lorenzo0111.bedwars.api.scoreboard;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public abstract class ScoreboardHook {
    protected final List<WrappedScoreboard> scoreboards = new ArrayList<>();

    public abstract String getId();

    public void unload() {
        scoreboards.forEach(WrappedScoreboard::destroy);
        scoreboards.clear();
    }

    public abstract WrappedScoreboard create(String title, Callable<List<String>> lines);

    public void remove(WrappedScoreboard scoreboard) {
        scoreboards.remove(scoreboard);
    }
}

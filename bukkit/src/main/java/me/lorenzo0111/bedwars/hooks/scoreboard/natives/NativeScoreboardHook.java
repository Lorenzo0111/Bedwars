package me.lorenzo0111.bedwars.hooks.scoreboard.natives;

import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;
import me.lorenzo0111.bedwars.api.scoreboard.WrappedScoreboard;

import java.util.List;
import java.util.concurrent.Callable;

public class NativeScoreboardHook extends ScoreboardHook {

    @Override
    public String getId() {
        return "Native";
    }

    @Override
    public WrappedScoreboard create(String title, Callable<List<String>> lines) {
        NativeScoreboard scoreboard = new NativeScoreboard(this, title, lines);
        scoreboards.add(scoreboard);

        return scoreboard;
    }
}

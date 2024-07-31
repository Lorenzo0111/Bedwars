package me.lorenzo0111.bedwars.hooks.scoreboard.natives;

import me.lorenzo0111.bedwars.api.scoreboard.ScoreboardHook;
import me.lorenzo0111.bedwars.api.scoreboard.WrappedScoreboard;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.concurrent.Callable;

public class NativeScoreboard extends WrappedScoreboard {
    private final Scoreboard scoreboard;
    private final Objective objective;
    private boolean destroyed = false;

    @SuppressWarnings("ConstantConditions")
    public NativeScoreboard(ScoreboardHook hook, String title, Callable<List<String>> lines) {
        super(hook, title, lines);

        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("bedwars", Criteria.DUMMY, "bedwars");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(title);

        this.update();
    }


    @Override
    public void show(Player player) {
        if (destroyed) return;

        player.setScoreboard(scoreboard);
    }

    @Override
    @SuppressWarnings("ConstantConditions")
    public void hide(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }

    @Override
    public void destroy() {
        objective.unregister();
        destroyed = true;
    }

    @Override
    public void update() {
        if (destroyed) return;

        try {
            List<String> currentLines = lines.call();
            for (int i = 0; i < currentLines.size(); i++) {
                objective.getScore(currentLines.get(i)).setScore(i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
